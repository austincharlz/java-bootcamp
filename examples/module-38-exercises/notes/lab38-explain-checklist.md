# Lab 38 — EXPLAIN Checklist

## Step 1 — Command

Write the command you will use: `EXPLAIN (ANALYZE, BUFFERS) <sql>;`.

## Step 2 — Look for

Seq Scan vs Index Scan, rows estimates, buffers.

## Step 3 — Success signal

Index Scan on customer_id for Amina lookup is a good sign.

## Step 4 — Analyze

Note `ANALYZE customer;` updates stats (PostgreSQL), not DBMS_STATS.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.