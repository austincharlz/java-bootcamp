# Lab 46 — Classify Consumer Failures

## Reference

| Failure              | Typical action             |
|----------------------|----------------------------|
| Validation           | DLT + fix publisher        |
| Deserialization      | DLT + schema/version check |
| Transient DB         | Bounded retry then DLT     |
| Poison forever-retry | Forbidden pattern          |

## Step 1 — Categories

List: validation, deserialization, timeout, DB, authz—with one CRM example each.

## Step 2 — Check the reference

Poison messages must not block the partition forever while lag grows unnoticed.

## Step 3 — User impact

Map one failure to stale profile data for `CUS-1001` or stuck status for `CUS-1002`.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.