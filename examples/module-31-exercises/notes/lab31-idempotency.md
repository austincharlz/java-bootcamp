# Lab 31 — Idempotency Plan

## Step 1 — Why duplicates

producer retry, consumer rebalance/reprocess.

## Step 2 — Business key

`eventId` or `customerId+eventType+occurredAt` for `CUS-1001`.

## Step 3 — Store idea

Check a processed-events table/set before side effects (email).

## Step 4 — Out of scope

Do not implement the table yet — paper design only.

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.