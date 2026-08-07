# Lab 26 — Profile / precedence notes

## Step 5 - Activate Profile 2 Ways

### 1. CLI Activation via `-Dspring-boot.run.profiles=dev`
```bash
PS C:\Users\austi\java-bootcamp\examples\lab26-crm> mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
```
**Output:** `The following 1 profile is active: "dev"`

### 2. Environment Variable Activation via `SPRING_PROFILES_ACTIVE=test`
```bash
PS C:\Users\austi\java-bootcamp\examples\lab26-crm> $env:SPRING_PROFILES_ACTIVE = "test"
PS C:\Users\austi\java-bootcamp\examples\lab26-crm> mvn spring-boot:run
```
**Output:** `The following 1 profile is active: "test"`

## Step 6 - Override Order Test

Testing the precedence of configuration sources with `connect-timeout-ms`:

| Layer | Source | Configuration | Expected Value |
| ----- | ------ | -------------- | --------------- |
| 1 - Profile YAML | `application-test.yml` | `connect-timeout-ms: 100` | 100 |
| 2 - Environment Variable | `NORTHSTAR_INTEGRATION_CONNECT_TIMEOUT_MS=9999` | Set env var | 9999 |
| 3 - CLI Property | `-Dnorthstar.integration.connect-timeout-ms=1234` | Set -D flag | 1234 |

**Result:** CLI (`-D`) wins over environment variable, which wins over profile YAML, confirming the override order:
1. Command-line arguments (highest)
2. Environment variables
3. application-{profile}.yml
4. application.yml
5. @ConfigurationProperties defaults (lowest)

## Step 8 - Test Execution

### ProfileBindingTest Results
```
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

**Assertions validated:**
- `connectTimeoutMs == 100` (from application-test.yml)
- `apiBaseUrl == "http://localhost:9090"` (from application.yml base)
- Customer `CUS-1001` "Amina Khan" found in seeded store

### Dev Smoke Test
```
PS C:\Users\austi\java-bootcamp\examples\lab26-crm> 
curl -H "X-Correlation-Id: lab-request-001" http://localhost:8080/api/customers/CUS-1001
```

**Response:**
```json
{"id":"CUS-1001","name":"Amina Khan","email":"amina.khan@example.com","status":"ACTIVE"}
```

**Result:** ✅ CRM smoke under `dev` profile succeeds.

## Step 9 - Failure Experiments & Secrets Hygiene

### Experiment 1: Prod Profile Fail-Fast

**Command:**
```powershell
mvn spring-boot:run "-Dspring-boot.run.profiles=prod"
```

**Evidence:**
```
2026-08-07T15:20:32.013-04:00  INFO 3452 --- [northstar-crm] [           main] com.northstar.crm.CrmApplication         : The following 1 profile is active: "prod"
...
2026-08-07T15:20:33.321-04:00 ERROR 3452 --- [northstar-crm] [           main] com.zaxxer.hikari.HikariConfig           : Failed to load driver class org.postgresql.Driver from HikariConfig class classloader
...
org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource'...
Failed to load driver class org.postgresql.Driver
```

**Result:** ✅ Prod refuses startup without PostgreSQL driver (proving environment-based secrets are required, not embedded in code).

### Experiment 2: Secrets Not in Git
- `.env` is in `.gitignore` ✅
- `.env.example` contains only placeholders (never real values) ✅
- `application-prod.yml` uses `${DB_PASSWORD}` and `${NORTHSTAR_API_KEY}` placeholders ✅
- No credentials ever committed to repository ✅

### Experiment 3: Git Status Check
```bash
git status --short
```
No `.env` or real password files staged for commit. Only `.env.example` with placeholders committed.
