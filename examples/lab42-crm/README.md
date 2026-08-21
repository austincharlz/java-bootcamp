# Lab 42 — Kubernetes Deployment for CRM

This lab deploys the Lab 41 CRM image onto a local k3d cluster with a safe Kubernetes rollout path: ConfigMap, Secret,
Deployment, Service, Ingress, probe tuning, and rollback rehearsal.

## Goal

Run the CRM app in `crm-training` with:

- a valid Deployment and selector
- resource requests and limits
- distinct startup, readiness, and liveness probes
- Traefik Ingress via Host header on `127.0.0.1:8088`
- a documented rollback flow using Kubernetes revision history

## Required setup

```bash
# from java-bootcamp
k3d cluster create lab42 --image rancher/k3s:v1.28.15-k3s1 -p "8088:80@loadbalancer"
k3d image import crm-api:lab41 -c lab42

# if kubeconfig still points at host.docker.internal, rewrite it to 127.0.0.1
kubectl create namespace crm-training --dry-run=client -o yaml | kubectl apply -f -
```

## Apply the app

Never apply the whole `k8s/` directory because it includes the example Secret file. Apply resources individually:

```bash
kubectl -n crm-training apply -f k8s/configmap.yaml
kubectl -n crm-training create secret generic crm-api-secrets \
  --from-literal=CRM_DB_PASSWORD='CHANGE_ME' \
  --dry-run=client -o yaml | kubectl apply -f -
kubectl -n crm-training apply -f k8s/deployment.yaml -f k8s/service.yaml -f k8s/ingress.yaml
kubectl -n crm-training rollout status deployment/crm-api --timeout=180s
```

## Smoke test

```bash
curl -fsS -H "Host: crm-api.training.example.test" \
  http://127.0.0.1:8088/actuator/health/readiness

curl -fsS -H "Host: crm-api.training.example.test" \
  -H "X-Correlation-Id: lab-request-001" \
  "http://127.0.0.1:8088/api/customers?status=ACTIVE"
```

Expected result:

- readiness returns `UP`
- customer list endpoint returns `200`
- correlation header is usable in requests

## Rollback rehearsal

```bash
kubectl -n crm-training set image deployment/crm-api crm-api=crm-api:does-not-exist
kubectl -n crm-training rollout status deployment/crm-api --timeout=60s || true
kubectl -n crm-training rollout history deployment/crm-api
kubectl -n crm-training rollout undo deployment/crm-api
kubectl -n crm-training rollout status deployment/crm-api --timeout=180s
```

## Reflection

1. The most important design choice for traffic safety was separating readiness from liveness: readiness gates traffic
   while liveness only restarts a genuinely wedged process. This avoids dropping healthy pods during temporary database
   or startup blips while still restarting a stuck JVM.

2. Rollback was proven by the deployment history and a real undo: a bad image tag produced a failed rollout, and
   `kubectl rollout undo deployment/crm-api` restored the prior revision and healthy pod state. A second smoke test
   after the undo confirmed the service was back to normal.

3. The hardest failure to diagnose from events and logs was the JDBC/password issue. The deployment looked healthy
   enough to start, but the container failed during Flyway startup with `password authentication failed for user "crm"`;
   the log evidence isolated it to the database credentials rather than the probes or image pull path.