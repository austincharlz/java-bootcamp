# Lab 44 Evidence Log

- Repo: `java-bootcamp`
- Lab 43 run id / jarSha256: recorded in `examples/lab44-crm/artifact-manifest.json` as
  `gitCommit=8a5a9684301f7c210f085d07c2038f9098ec36d8` and
  `jarSha256=314c1350fdb567954c816ce714c72640d18ddc495859de3314b91566dd095d2b`
- Workflow path: `.github/workflows/crm-cd.yml` at the git root
- Smoke check: `GET /api/customers?status=ACTIVE` with `X-Correlation-Id: lab-request-001` or a documented tabletop if
  the local cluster is not running
- Rollback rehearsal: `kubectl -n crm-training rollout undo deployment/crm-api` followed by readiness + list-API
  verification
- Secrets: no kubeconfig, registry token, or password values committed to Git

## Smoke command

```powershell
curl.exe -fsS -H "Host: crm-api.training.example.test" `
  -H "X-Correlation-Id: lab-request-001" `
  "http://127.0.0.1:8088/api/customers?status=ACTIVE"
```

## Rollback command

```bash
kubectl -n crm-training rollout undo deployment/crm-api
kubectl -n crm-training rollout status deployment/crm-api --timeout=180s
```
