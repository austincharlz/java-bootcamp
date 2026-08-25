# Lab 46 — Sketch Idempotent Handling

## Step 1 — Side effects

List side effects your consumer might own (projection upsert, email, audit row).

## Step 2 — Check the reference

Idempotency keys / upserts / dedupe store—pick a strategy in notes. Lab 31 default is in-memory `eventId`.

## Step 3 — Scenario

Describe duplicate delivery for an event about `CUS-1002` status change.

## Step 4 — Test idea

Name one test: process same event twice → one projection row.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.