# Lab 32 — Resilience Runbook & UX Contract

## Instance name

All three annotations (`@CircuitBreaker`, `@Retry`, `@TimeLimiter`) share the name `accountProfile` so they resolve to the same Resilience4j configuration blocks in `application.yml`. Using a consistent name also means Actuator events, health, and metrics all surface under the same instance key — making it easy to correlate retries, CB state transitions, and timeout counts in a single dashboard view.

## Truthful fallback

`available=false` must never look like a successfully funded account because:
- React reads `available` to decide whether to render the balance panel or the degraded banner.
- If fallback returns `available=true` with invented data, agents see fake balances and may act on them (payments, refunds, escalations) — a data-integrity violation.
- If a write later fails silently with a success-shaped fallback, the caller believes the mutation succeeded when it did not.

The only correct contract: degraded reads return `AccountSummary.unavailable(customerId)` (`available=false`, `note="account-profile-unavailable"`). Write endpoints propagate errors — they never return a success-shaped fallback.

---

## AccountSummary.available flag contract

| CRM response field | Meaning |
| ------------------ | ------- |
| `available: true`  | Account Profile succeeded; accounts list is trustworthy |
| `available: false` | Degraded read; show banner — **do not invent balances or imply writes succeeded** |

React/API banner text: **"Account information is temporarily unavailable."**

Correlation ID `lab-request-001` (or the `X-Correlation-Id` request header) must appear in both the CRM request log and the outbound Account Profile log.

---

## Resilience4j instance: `accountProfile`

| Pattern | Key config (lab values) | Purpose |
| ------- | ----------------------- | ------- |
| Retry | maxAttempts=3, backoff×2 from 200ms, retries `TemporaryAccountException`/`IOException` | Recover from transient 503s |
| CircuitBreaker | COUNT_BASED window=10, threshold=50%, open-wait=10s, half-open probes=2 | Stop hammering a degraded dependency |
| TimeLimiter | 1500ms, cancel-running-future=true | Enforce CRM latency budget; prevent thread-pool starvation |

**⚠ Production note:** The values above (window=10, minimum-calls=5, open-wait=10s) are tuned for classroom visibility. Production thresholds must be derived from SLOs and load tests — typical open-waits are 30–60s and windows cover hundreds of calls.

---

## Failure experiments

| # | Experiment | Expected observation |
| - | ---------- | -------------------- |
| 1 | Permanent 503 from WireMock | Retries exhaust → circuit trips OPEN → fallback `available=false` |
| 2 | 3 000ms delay stub | Response arrives in ≈1 500ms → fallback `available=false` |
| 3 | Calls while OPEN | Fail fast (<20ms); WireMock journal count unchanged |
| 4 | Retry on non-idempotent write | **Forbidden** — double-charge / duplicate record risk |
| 5 | Fallback returns `available=true` without real data | UX lie — React cannot distinguish real from fake accounts |

---

## Actuator observation

```bash
curl -s localhost:8080/actuator/health
curl -s localhost:8080/actuator/circuitbreakerevents
curl -s localhost:8080/actuator/metrics/resilience4j.circuitbreaker.calls
```

---

## Reflection

1. **Fallback honesty vs fail-hard**: Returning `available=false` with HTTP 200 lets React degrade gracefully without lying. Silently returning fake data would corrupt downstream decisions.
2. **OPEN fail-fast proof**: WireMock journal count does not increase while the circuit is OPEN — zero additional requests reach the stub.
3. **Hardest failure**: Composing `@TimeLimiter` with `CompletableFuture` — sync return types silently bypass the timeout budget.
