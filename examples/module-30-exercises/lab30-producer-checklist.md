# Lab 30 — Producer Checklist

## Step 1 — Settings list
`acks=all`, idempotent producer, key = customerId, value = JSON envelope.

## Step 2 — Why acks=all

Wait for ISR ack before considering the CRM event durable.

## Step 3 — Idempotence

Broker dedupes producer retries so Amina is not double-created in the log.

## Step 4 — Out of scope today

*Do not run `kafka-console-producer` in this pre-lab.*

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.