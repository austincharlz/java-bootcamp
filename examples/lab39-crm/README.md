# Lab 39

## Runbook

Required environment variables:

```bash
export CRM_DB_URL=jdbc:postgresql://localhost:5432/crm
export CRM_DB_USERNAME=crm_app
export CRM_DB_PASSWORD=your-local-password
```

Copy `.env.example` to `.env`, fill in local values, and keep `.env` out of Git.

Typical commands:

```bash
docker compose up -d postgres
export CRM_DB_PASSWORD=change-me
./mvnw -q test -Dtest=CustomerRepositoryIT
./mvnw -q clean verify
./mvnw -q spring-boot:run
```

## Failure experiments

| # | Experiment                        | Observe                        | Restore                 |
|---|-----------------------------------|--------------------------------|-------------------------|
| 1 | Stop PostgreSQL and start app     | Fail fast / pool errors        | Start PostgreSQL; retry |
| 2 | Insert duplicate email            | 409 or IT assertion            | Use unique email        |
| 3 | Stale `@Version` update           | Optimistic failure → 409       | Reload entity; retry    |
| 4 | Request `size=1000`               | Capped to 100                  | Keep allow-list         |
| 5 | Temporarily set `ddl-auto=update` | Document risk; do not leave it | Restore `validate`      |

## Security and production review

1. Untrusted inputs include HTTP bodies, sort params, and page size.
2. Validation and business rules live in the service layer; JPA is persistence, not authz.
3. Secrets and local DB passwords stay in `.env` or shell exports and are never committed.

## Reflection Questions

1. Flyway plus `ddl-auto=validate` was the most important correctness decision because it prevents drift on a shared
   database.
2. PostgreSQL-backed repository tests and the Flyway startup logs prove the mappings and schema are aligned.
3. Diagnosing the stale `@Version` path and the local DB credential mismatch was the hardest part.
