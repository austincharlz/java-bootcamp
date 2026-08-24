# Lab 44 — Release plan

## Immutable artifact

Promote **one** identity from Lab 43: **`jarSha256`** + **`gitCommit`** from `SHA256SUMS`. Never rebuild with Maven on
the deploy agent. Image digest / GHCR is **optional** (Lab 41 `RepoDigests` is empty until you push).

| Field        | Value                                                                                       |
|--------------|---------------------------------------------------------------------------------------------|
| Version      | `1.4.0-lab44`                                                                               |
| Commit       | `8a5a9684301f7c210f085d07c2038f9098ec36d8` from Lab 43 `commit=` line                       |
| JAR SHA-256  | `314c1350fdb567954c816ce714c72640d18ddc495859de3314b91566dd095d2b` from Lab 43 `SHA256SUMS` |
| Image digest | `null` unless you pushed                                                                    |

## Promotion path

```text
Lab 43 CI package (crm-jar) → test → staging (list-API smoke) → [approval] → production
```

Approvers: release manager + production environment required reviewer (s) in GitHub Environments. Forbidden: any rebuild
during promotion (`mvn package` / `./mvnw`) because it produces new bits.

## Gates (objective)

| Env        | Gate                                                         | Evidence                                                      |
|------------|--------------------------------------------------------------|---------------------------------------------------------------|
| test       | Lab 43 verify green                                          | Lab 43 run URL                                                |
| staging    | SHA match + `GET /api/customers?status=ACTIVE` (or tabletop) | `artifact-manifest.json` jarSha256 and smoke log / screenshot |
| production | approval + `jarSha256` match                                 | GitHub Environment approval + `release-checklist.md` decision |

## Config vs artifact

Environment-specific configuration is mutable and injected at deploy time; the artifact is immutable.

| Category                  | Variable / setting                                         | test       | staging      | production      | Stored in Git                      |
|---------------------------|------------------------------------------------------------|------------|--------------|-----------------|------------------------------------|
| Spring datasource         | `SPRING_DATASOURCE_URL`                                    | env value  | env value    | env value       | Name/pattern only                  |
| Spring datasource         | `SPRING_DATASOURCE_USERNAME`                               | env value  | env value    | env value       | Name only                          |
| Spring datasource secret  | `SPRING_DATASOURCE_PASSWORD`                               | secret ref | secret ref   | secret ref      | **No** (secret value never in Git) |
| Lab 42 profile (`docker`) | `CRM_DB_HOST`, `CRM_DB_PORT`, `CRM_DB_NAME`                | env value  | env value    | env value       | Name/pattern only                  |
| Lab 42 profile secret     | `CRM_DB_USER`, `CRM_DB_PASSWORD`                           | secret ref | secret ref   | secret ref      | **No** (secret value never in Git) |
| Edge routing              | Ingress/host (for example `crm-api.training.example.test`) | test host  | staging host | production host | Hostnames only                     |

Secret **names** only in GitHub Environment docs/manifests, never secret values. User is **`crm`**, not `crm_app`. No
Kafka requirement for this lab’s smoke.

## DB compatibility

Schema changes follow **expand-before-contract**:

1. Add backward-compatible structures first (new nullable columns/tables, dual-write if needed).
2. Deploy application versions that can read both old and new schema.
3. Remove old structures only after all environments run compatible code.

Rollback scope notes:

- App artifact rollback is valid for code/config regressions.
- Artifact rollback is **not sufficient** after destructive DB operations (for example `DROP COLUMN`, irreversible type
  changes, destructive data migrations).
- Lab 43/44 use isolated DB **`crm_lab43`**; do not treat Lab 42 `crm_lab42` as production.