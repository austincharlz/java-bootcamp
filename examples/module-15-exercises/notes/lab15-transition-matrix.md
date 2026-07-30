# Lab 15 — Transition Matrix

## Reference

| From | To | Allowed? |
| --- | --- | --- |
| PROSPECT | ACTIVE | yes (Ravi activate) |
| ACTIVE | ACTIVE | no-op |
| ACTIVE | PROSPECT | no |

## Step 2 — Amina

CUS-1001 already ACTIVE — activate should be rejected or no-op per your policy. (no-op)

## Step 3 — Illegal list
- Throw if API Adapter accessing repository
- Throw if repository is calling the service layer

## Step 4 — Boundary

Note: exception HTTP mapping waits for Lab 16.

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.