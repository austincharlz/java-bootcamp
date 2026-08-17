# Lab 32 CRM — Resilience4j for Outbound Account Calls

CRM lab that protects outbound reads to a separate Account Profile service with **Retry**, **CircuitBreaker**, and
**TimeLimiter** (Resilience4j). Degraded reads return a truthful `AccountSummary.unavailable` response; writes never use
a success-shaped fallback. Resilience behavior is proven with deterministic **WireMock** stubs and verified via Spring
Boot Actuator.

## What's implemented

| Concern                 | Detail                                                                                      |
|-------------------------|---------------------------------------------------------------------------------------------|
| `AccountClient`         | `GET /accounts/{id}/summary`; maps 5xx → `TemporaryAccountException`                        |
| `AccountProfileService` | `@CircuitBreaker` → `@Retry` → `@TimeLimiter` on `find(customerId)`                         |
| Fallback                | `AccountSummary.unavailable(customerId)` — `available=false`, never fakes success           |
| Retry                   | 3 attempts, 200ms exponential backoff, retries `TemporaryAccountException` / `IOException`  |
| CircuitBreaker          | COUNT_BASED window=10, threshold=50%, open-wait=10s, half-open probes=2                     |
| TimeLimiter             | 1500ms budget, cancels running future                                                       |
| Tests                   | `AccountProfileResilienceTest` — healthy call, OPEN fail-fast, timeout fallback             |
| Runbook                 | `docs/resilience-notes.md` — UX contract, failure experiments, production threshold warning |

## Quick commands

```bash
# Run resilience tests (no Kafka or Docker required)
mvn test -Dtest=AccountProfileResilienceTest

# Run all tests
mvn test

# Start the app (requires Kafka — see below)
mvn spring-boot:run
```

```bash
# Actuator observation while running
curl -s localhost:8080/actuator/health
curl -s localhost:8080/actuator/circuitbreakerevents
curl -s localhost:8080/actuator/metrics/resilience4j.circuitbreaker.calls
```

```bash
# Optional: start Lab 30 Kafka broker for manual API/event demos
docker compose -f ../lab30-crm/compose.yaml up -d
```

## Key files

| File                                                   | Purpose                                          |
|--------------------------------------------------------|--------------------------------------------------|
| `src/main/java/.../account/AccountClient.java`         | HTTP client; maps 5xx to typed exception         |
| `src/main/java/.../account/AccountProfileService.java` | Resilience4j-annotated service                   |
| `src/main/java/.../account/AccountSummary.java`        | Record with `unavailable()` factory              |
| `src/main/resources/application.yml`                   | Resilience4j instance config + Actuator exposure |
| `src/test/.../AccountProfileResilienceTest.java`       | WireMock-based resilience tests                  |
| `docs/resilience-notes.md`                             | Runbook, UX contract, failure experiments        |

## Notes

- Lab CB thresholds (window=10, open-wait=10s) are sized for classroom visibility. Production values must come from SLOs
  and load tests.
- Write endpoints do **not** use a success-shaped fallback — failed writes propagate as errors.
- No Docker required to run the resilience tests; WireMock runs in-process.
