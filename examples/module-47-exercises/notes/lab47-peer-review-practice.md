# Lab 47 — Peer Review Rewrite Practice

## Step 1 — Weak sentence

Given: “Someone broke Kafka again.” Rewrite to blameless + factual.

Rewrite:

- “The Kafka consumer lag increased after the 1.4.0 deployment, and we are validating the failing event path before
  rollback.”

## Step 2 — Check the reference

Feedback should cite the line/section and suggest a rewrite.

Example review note:

- “In the incident summary, the phrase ‘broke Kafka again’ is blame-oriented and unsupported. Please replace it with the
  observed symptom and a factual statement such as: ‘Kafka consumer lag increased after the 1.4.0 deployment, and the
  failing fixtures were CUS-1001/CUS-1002 with correlation `lab-request-001`.’”

## Step 3 — Second rewrite

Improve a vague PR line: “Fixed stuff” → specific verify/rollback language.

Rewrite:

- “Recovered the failing profile-open path by rolling back to the 1.3.2 digest and verifying the synthetic fixtures
  CUS-1001 and CUS-1002 are no longer returning HTTP 503s after the Kafka lag check.”

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.