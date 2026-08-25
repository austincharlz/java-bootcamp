# Lab 47 — PR Description Outline

## Step 1 — Sections

Why: A limited Kafka/customer-event path caused synthetic profile-open failures after the 1.4.0 deployment.

What changed: We narrowed the issue to the event path affecting `CUS-1001` and `CUS-1002`, validated the lag and DLT
behavior, and prepared a rollback path to the 1.3.2 digest.

How verified: `./mvnw -B test` and CI link placeholder; synthetic fixtures checked for profile access and Kafka lag
after rollback.

Rollback: Revert to the 1.3.2 digest and watch readiness and Kafka lag before re-enabling traffic.

Risks: Re-enabling too early could re-trigger duplicate side effects or unresolved consumer lag.

## Step 2 — Check the reference

PR must be reviewable without Slack archaeology.

This description keeps the scope, verification, and rollback steps explicit so reviewers can validate the change without
asking for prior live-incident chat.

## Step 3 — Verify bullets

- Verified synthetic fixtures `CUS-1001` and `CUS-1002` return to normal profile-open behavior after rollback and Kafka
  lag check.
- Verified the DLT and lag evidence from Lab 46 shows no duplicate side effects or unresolved poisoning path for
  correlation `lab-request-001`.

## Step 4 — Scope

Outline only—full packet is Lab 47.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.