# Lab 42 — Rollout and Rollback Checklist

## Step 1 — Rollout watch

List: `kubectl rollout status`, pod Ready, Host-header Ingress check on `:8088`, `GET /api/customers`.

## Step 2 — Check the reference

Rollback rehearses a bad image tag then `rollout undo` to `crm-api:lab41`.

## Step 3 — Evidence

Name screenshot folders under `notes/screenshots/lab-42/` for before/after.

## Step 4 — Correlation

Include header `lab-request-001` on smoke calls in the checklist.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.