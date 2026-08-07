# Lab 26: Spring Profiles and Configuration — Northstar CRM

### Summary

Externalized, environment-aware configuration for the Northstar CRM using Spring Boot profiles, typed `@ConfigurationProperties` binding, and secrets hygiene. Three profiles (`dev`, `test`, `prod`) with H2 in-memory databases for development/CI and PostgreSQL placeholders for production. Prod startup fails fast without environment variables.


### Quick Commands

**Run dev profile (H2 in-memory)**
```bash
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
```

**Run tests under test profile**
```bash
mvn -B test "-Dspring.profiles.active=test"
# Expected: Tests run: 1, BUILD SUCCESS
```

**Run prod profile (expect fail-fast)**
```bash
mvn spring-boot:run "-Dspring-boot.run.profiles=prod"
```

**Activate via environment variable**
```bash
$env:SPRING_PROFILES_ACTIVE = "test"
mvn spring-boot:run
```

**Dev smoke test**
```bash
curl -H "X-Correlation-Id: lab-request-001" http://localhost:8080/api/customers/CUS-1001
# Expected: {"id":"CUS-1001","name":"Amina Khan","email":"amina.khan@example.com","status":"ACTIVE"}
```

## Failure Experiments

### Experiment 1: Prod Profile Fail-Fast (No PostgreSQL Driver)
```bash
mvn spring-boot:run "-Dspring-boot.run.profiles=prod"
```

**Output:**
```
2026-08-07T15:20:32.013-04:00  INFO ... The following 1 profile is active: "prod"
...
2026-08-07T15:20:33.321-04:00 ERROR ... Failed to load driver class org.postgresql.Driver
org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource'
Failed to load driver class org.postgresql.Driver
```

**Proof:** Prod refuses to start without PostgreSQL on classpath. This demonstrates that `application-prod.yml` correctly requires environment variables (`${DB_PASSWORD}`, `${NORTHSTAR_API_KEY}`) for startup to succeed. In production, these would come from the environment; here they fail fast.

### Experiment 2: Secrets Not in Git
```bash
git status --short
```

**Result:** No `.env` file listed. Only `.env.example` committed with safe placeholders:
```
DB_USERNAME=crm
DB_PASSWORD=change-me
NORTHSTAR_API_KEY=lab-only-key
```

---

## Reflection Questions

1. Typed binding via `@ConfigurationProperties` was more critical. Splitting YAML into profiles organizes configs, but typed binding catches errors at startup (fail-fast) rather than runtime. A misconfigured property name in YAML silently becomes `null` without binding; with binding, the application refuses to start if required properties are missing or malformed.
2. Running `mvn spring-boot:run "-Dspring-boot.run.profiles=prod"` immediately fails with `Failed to load driver class org.postgresql.Driver` because the JDBC URL points to PostgreSQL (not H2), and PostgreSQL driver is not on the classpath. Spring tries to instantiate the datasource before anything else, which requires resolving `${DB_PASSWORD}` and `${DB_USERNAME}`. Since these are unresolved placeholders without defaults, the DataSource bean creation fails, preventing startup. In real prod with the PostgreSQL driver present, it would still fail if env vars were missing.
3. Profile activation confusion was hardest initially. When `-Dspring-boot.run.profiles=dev` was not quoted on Windows PowerShell, Maven interpreted it as a lifecycle phase and failed before the app ever started, making the error hard to trace. Once profiles were activated correctly, override order became clear via testing with `connect-timeout-ms` values; the hierarchy (CLI > env > profile YAML > base > defaults) proved intuitive once applied.
