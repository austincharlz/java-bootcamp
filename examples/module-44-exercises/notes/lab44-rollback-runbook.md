# Lab 44 — Outline Rollback Runbook

## Step 1 — Steps

Detect → decide → redeploy known-good digest → verify readiness → CRM smoke → comms update.

## Step 2 — Check the reference

Rollback names digest Y and a verification check—not “redeploy latest”.

## Step 3 — Timebox

Write a target recovery time placeholder (e.g. under 5 minutes) and who declares SEV.

## Step 4 — List-API verify

Re-run readiness + **`GET /api/customers?status=ACTIVE`** with `lab-request-001`. Kafka DLT is Lab 46 — not required
here.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.