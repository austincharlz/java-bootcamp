# Lab 38: PostgreSQL CRM Query Performance

## Run

```powershell
cd C:\Users\austi\java-bootcamp\examples\lab38-crm
docker compose up -d
docker compose exec -T -e PGPASSWORD=CrmLab_Strong1 postgres psql -U crm_app -d crm -c "SET search_path TO crm_app; SELECT COUNT(*) FROM customer;"
```

## Reflection

1. Adding `customer_id` as tie-breaker in `ORDER BY (created_at DESC, customer_id DESC)` ensured deterministic page
   order and prevented duplicate rows across pages.

2. 55% execution time reduction (0.123 → 0.055 ms) and buffer improvement (4 → 1+3) prove the email index worth its
   write cost; email lookups are frequent in CRM auth.

3. Missing table privileges for `crm_app` role was hardest to diagnose—data loaded but queries failed with "permission
   denied." Fix: grant SELECT/INSERT/UPDATE/DELETE on all tables.