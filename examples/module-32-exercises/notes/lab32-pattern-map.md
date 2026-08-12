# Lab 32 — Pattern Map

## Reference

| Pattern | CRM use |
| --- | --- |
| Retry | Transient 503 from Account Profile |
| TimeLimiter | Fail fast if call exceeds N ms |
| CircuitBreaker | Stop calling when failure rate high |
| Fallback | Return cached/minimal profile for Amina |

## Step 2 — Add Ravi row

When `CUS-1002` Ravi's request arrives and the circuit is open, the call fails fast and the fallback returns a minimal cached profile without ever reaching Account Profile.

## Step 3 — Order idea

TimeLimiter → CircuitBreaker → Retry → call (outermost to innermost so the time limit caps total elapsed time, the breaker stops retries when the service is down, and retries handle transient errors).

## Step 4 — Boundary

Circuit breaker applies only to remote calls (Account Profile service); in-memory map lookups are local and never fail remotely, so wrapping them would add overhead with no benefit.

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.