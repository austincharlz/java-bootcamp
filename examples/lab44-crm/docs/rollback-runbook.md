# Lab 44 — Rollback runbook

## Known-good identity

| Field                       | Value                                                                                             |
|-----------------------------|---------------------------------------------------------------------------------------------------|
| Previous `jarSha256`        | `314c1350fdb567954c816ce714c72640d18ddc495859de3314b91566dd095d2b`                                |
| Previous version / Image Id | `1.4.0-lab44` / `null` (no pushed image digest; local k3d uses existing `crm-api:lab41` image id) |
| Verification check          | readiness + **`GET /api/customers?status=ACTIVE`** with `X-Correlation-Id: lab-request-001`       |

There is **no** `GET /api/customers/{id}`. Optional local cluster is **Lab 42 k3d** (`Host` header on `:8088`), not
instructor GHCR.

## Procedure (sketch)

1. Announce incident / change freeze as needed (Lab 47 templates).
2. Redeploy the **prior** identity — do **not** `mvn package`. For optional k3d:
   `kubectl -n crm-training rollout undo deployment/crm-api`
3. Exact commands for your environment (tabletop is valid on the timed path):
   - `gh run download <LAB43_RUN_ID> -n crm-jar -D dist/`
   - `Get-FileHash dist\*.jar -Algorithm SHA256` (Windows) or `sha256sum dist/*.jar` (macOS/Linux)
   - `kubectl -n crm-training rollout undo deployment/crm-api` and
     `kubectl -n crm-training rollout status deployment/crm-api --timeout=180s`
   -
   `curl.exe -fsS -H "Host: crm-api.training.example.test" -H "X-Correlation-Id: lab-request-001" "http://127.0.0.1:8088/api/customers?status=ACTIVE"`
4. Verify readiness + list-API smoke.
5. Record outcome in release notes (no secrets).

## Rehearsal evidence

Evidence is stored under `notes/screenshots/lab-44/` and should include the SHA comparison, curl response, and any
rollback commands executed. Redact tokens, kubeconfig, and any environment secrets before saving.
