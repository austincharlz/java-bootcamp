# Lab 47 — Draft Incident Update Skeleton

## Step 1 — Sections

Summary: A synthetic Kafka/CRM issue is affecting customer profile updates for a small subset of test traffic during the
lab window.

Impact: Agents are unable to process customer updates for synthetic customer IDs CUS-1001 and CUS-1002, which delays
queue reconciliation and downstream agent review.

Current status: The team has isolated the issue to a limited customer-event path and is validating the recovery plan
before broader rollout.

What we know: The affected traffic is limited to lab fixtures with correlation ID `lab-request-001`; no production
credentials or real customer records are involved.

Next update time: 18:30 UTC.

Contacts: incident-response channel, on-call Kafka owner, and release coordinator for the CRM service.

## Step 2 — Check the reference

No invented root cause; no credentials; no customer PII.

## Step 3 — Impact line

Agents are unable to process customer updates for synthetic customer IDs CUS-1001 and CUS-1002, which delays queue
reconciliation and downstream agent review.

## Step 4 — Next update

Next update: 18:30 UTC.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.