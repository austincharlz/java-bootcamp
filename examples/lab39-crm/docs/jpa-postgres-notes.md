# Lab 39 — JPA / PostgreSQL notes

## Flyway vs ddl-auto

Use Flyway as the schema source of truth and leave `ddl-auto=validate` in `application.yml`. This prevents Hibernate
from mutating a shared database and makes drift obvious at startup. If the schema changes, add a new migration such as
`V2__...sql` instead of editing a live migration.

## Required env

```bash
export CRM_DB_URL=jdbc:postgresql://localhost:5432/crm
export CRM_DB_USERNAME=crm_app
export CRM_DB_PASSWORD=change-me
```

Keep `.env` local-only; never commit it.

## Optimistic locking

`@Version` on `CustomerEntity` makes stale updates fail with Spring's optimistic-locking exception. The API layer should
translate that to a controlled `409 Conflict` response instead of leaking SQL or stack traces.

## Failure experiments

### 1. PostgreSQL down

```bash
docker stop crm-postgres
./mvnw -q spring-boot:run
```

Expected: startup fails fast with connection refused / pool errors.

### 2. Duplicate email

```bash
./mvnw -q test -Dtest=CustomerRepositoryIT#duplicateEmailFails
```

Expected: repository throws a data-integrity exception, and API maps it to HTTP 409.

### 3. Stale optimistic lock

```bash
./mvnw -q test -Dtest=CustomerRepositoryIT#optimisticLockRace
```

Expected: stale version update fails with optimistic locking and is translated to HTTP 409.

### 4. Bounded page size

```bash
curl "http://localhost:8080/api/customers?status=ACTIVE&page=0&size=1000"
```

Expected: size is capped to 100 and sort remains deterministic with customerId tie-breaker.
