# Lab 38 — Index Tradeoffs

## Step 1 — Benefit

Faster status filters and account-by-customer joins.

## Step 2 — Cost

Slower INSERT/UPDATE for Amina/Ravi seeds at scale; more disk.

## Step 3 — Cleanup

Lab may include dropping experimental indexes — plan to document before/after.

## Step 4 — Rule

Add index only when EXPLAIN shows need.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.