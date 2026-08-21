# Lab 42 — Map k3s Manifests

## Reference

| Object     | Holds                     | Must not hold      |
|------------|---------------------------|--------------------|
| ConfigMap  | Non-secret URLs/flags     | DB passwords       |
| Secret     | Credentials (out-of-band) | Values in Git      |
| Deployment | Pod template, probes      | HostPath secrets   |
| Service    | ClusterIP ports           | TLS private keys   |
| Ingress    | Host/path/TLS redirect    | App business logic |

## Step 1 — Objects

List: Namespace (student), ConfigMap, Secret (ref only), Deployment, Service, Ingress (Traefik).

## Step 2 — Check the reference

Default student cluster is **local k3d** (`lab42`, Traefik Ingress). Use `kubectl` with the k3d kubeconfig (never commit
it). A shared instructor cluster is optional and only if the instructor publishes it.

## Step 3 — Labels

Propose app labels: `app=crm-api`, `lab=42`, `customer-fixture=synthetic`.

## Step 4 — Image pin

Note image is Lab 41 tag `crm-api:lab41` (record Image Id). Do not deploy `:latest` alone. A registry digest is optional
until you push.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.