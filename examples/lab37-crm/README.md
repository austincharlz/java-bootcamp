# Lab 37 CRM PostgreSQL local runbook

This project uses the local Docker fallback for the lab. Keep database credentials in `.env`; do not commit them.

## Local Docker setup

```powershell
cd C:\Users\austi\java-bootcamp\examples\lab37-crm
docker compose up -d --wait
```

Connect as the admin user:

```powershell
docker exec -i crm-postgres psql -U crm -d crm
```

## Script order

```sql
-- as crm / postgres admin
\i
database/01_create_user.sql
\i database/02_schema.sql
\i database/03_seed.sql
\i database/04_verify.sql
```

## Reflection questions

1. The strongest correctness decision was enforcing database-level constraints on status, email uniqueness, and
   customer/account relationships. Those rules protect the CRM even if app validation is missing or bypassed.

2. The local Docker validation showed the `crm_app` user connected successfully, the tables existed under `crm_app`,
   Amina and Ravi were seeded correctly, and the validation queries returned the expected ACTIVE/PROSPECT rows plus the
   history record for `lab-request-001`.

3. The hardest issue was the local Docker permission/setup mismatch: the app user had to be granted the right schema
   privileges and then verified that it could write only in its own schema instead of the public schema.

## Notes

- `.env` is local-only and intentionally ignored by Git.
- `.env.example` is the tracked template.
- The `05_drop.sql` cleanup script drops child tables before parent tables to avoid foreign-key dependency failures.
