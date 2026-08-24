# Lab 42 — Fill Deployment YAML TODOs

## Step 1 — Skeleton

In notes, draft:

```yaml
# deployment-skeleton.yaml
spec:
  replicas: 1
  template:
    spec:
      securityContext:
        runAsNonRoot: 10001
        runAsUser: 10001
      containers:
      - name: crm-api
        image: crm-api:lab41
        ports:
        - containerPort: 8080
        readinessProbe:
          httpGet:
            path: /actuator/health/readiness
            port: 8080
```

## Step 3

Add resources.requests/limits placeholders for CPU/memory.

## Step 4

Explicitly note: do not kubectl apply as completion of this exercise.