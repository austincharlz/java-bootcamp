# Lab 46 — Draft DLT Policy

## Step 1 — Names

Primary topic example `crm.customer-events.v1`; DLT `crm.customer-events.v1.DLT`; group `crm-notifications` (Lab 31).
Lab 30 `.dlq` is a different name.

## Step 2 — Check the reference

Use `DefaultErrorHandler` + `DeadLetterPublishingRecoverer` **wired on** `ConcurrentKafkaListenerContainerFactory`
(Spring Kafka).

## Step 3 — Headers

List headers to preserve: original topic, exception message class, correlation `lab-request-001`.

## Step 4 — PII rule

Prefer customer IDs in logs/metrics—not emails/names.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.