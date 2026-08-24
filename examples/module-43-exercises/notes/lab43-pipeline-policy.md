# Lab 43 — Define Pipeline Triggers

## Reference

| Event        | Verify | Package JAR+SHA |
|--------------|--------|-----------------|
| pull_request | Yes    | No (typical)    |
| push main    | Yes    | Yes             |
| tag v*       | Yes    | Yes             |

## Step 1 — Matrix

Fill a table: event → jobs (verify always; package on main/tags; deploy later/not yet).

## Step 2 — Check the reference

Leadership: PRs get fast feedback; main/tags get stronger gates; deploy creds never in Git.

## Step 3 — CRM identity

Note synthetic fixtures may appear only in test evidence (`CUS-1001`, `lab-request-001`).

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.