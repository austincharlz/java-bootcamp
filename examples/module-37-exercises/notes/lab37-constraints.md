# Lab 37 — Constraints Checklist

## Step 1 — PK/UK

PK on customer_id; UNIQUE on account_number.

## Step 2 — CHECK

status IN ('ACTIVE','SUSPENDED',...).

## Step 3 — NOT NULL

full_name and status NOT NULL.

## Step 4 — SQLSTATE awareness

Note unique violations → SQLSTATE 23505 (for later labs).

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.