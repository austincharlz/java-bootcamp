# Lab 38 — Sargability

## Reference

| Predicate                            | Sargable?                 |
|--------------------------------------|---------------------------|
| customer_id = 'CUS-1001'             | Yes                       |
| status = 'ACTIVE'                    | Yes (with index)          |
| LOWER(full_name) = 'amina khan'      | Usually no on plain index |
| created_at >= TIMESTAMP '2026-01-01' | Yes (range)               |
| date_trunc('day', created_at) = ...  | Often weaker than range   |

## Step 1 — Study table

Copy the reference table into notes.

## Step 2 — Rewrite

Store lowercased column or use `ILIKE` carefully.

## Step 3 — Half-open range

Prefer `created_at >= d AND created_at < d+1` over wrapping columns in functions.

## Step 4 — Oracle note

If old materials say `TRUNC(created_at)`, map to PostgreSQL range/`date_trunc` contrast.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.