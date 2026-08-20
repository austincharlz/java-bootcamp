# Lab 41 — Plan Container Smoke

## Step 1 — Steps

Order: health ready → `GET /api/customers` (optional CUS-1001 row) → correlation header `lab-request-001` → stop
container.

## Step 2 — Check the reference

Evidence is screenshots/logs under lab-41 notes—not production dumps.

## Step 3 — Failure case

One planned negative: wrong DB URL should fail readiness.

## Step 4 — Scope line

State this is a plan; full docker build/run is Lab 41.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.