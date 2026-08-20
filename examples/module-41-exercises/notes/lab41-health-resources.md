# Lab 41 — Health and Resource Checklist

## Step 1 — Health

Name the Actuator readiness path you expect (e.g. `/actuator/health/readiness`) and what “ready” means for agents.

## Step 2 — Check the reference

Readiness fails closed if DB is down—agents should not get half-ready CRM.

## Step 3 — Resources

Write placeholder memory/CPU limits for local docker run (numbers can be lab defaults).

## Step 4 — Graceful stop

One sentence on SIGTERM / graceful shutdown expectation for in-flight `lab-request-001` calls.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.