# Lab 39 — Paging and Locking Notes

## Step 1 — Page request

`PageRequest.of(0, 20, Sort.by("customerId"))`.

## Step 2 — Response

Return totalElements + content slice to the UI later.

## Step 3 — Optimistic lock

Second writer on Amina fails if version stale — user retries.

## Step 4 — Correlation

Log `lab-request-001` on lock failures for support.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.