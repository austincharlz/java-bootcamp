# Lab 42 — Design Three Probes

## Step 1 — Definitions

- startup (slow boot): Covers a slow PostgreSQL-dependent boot without triggering false restarts
- readiness (take traffic): Gates traffic. If it fails, the pod is pulled from Service's endpoints and is NOT restarted.
- liveness (restart if wedged)d): Reserved for truly wedged processes and restarts the container.

## Step 2 — Check the reference

Do not point all three at the same shallow endpoint without thinking—readiness should reflect DB dependency where
required.

## Step 3 — Paths

Propose Actuator paths: startup and readiness on `/actuator/health/readiness`; liveness on `/actuator/health/liveness`.

## Step 4 — Failure story

Describe what agents see if readiness fails while liveness stays up. If readiness fails and liveness stays up, the pod
is removed and not restarted. It would be running, but it would not be available.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.