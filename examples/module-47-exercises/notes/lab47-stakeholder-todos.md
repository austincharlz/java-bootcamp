# Lab 47 — Fill Stakeholder Email TODOs

## Step 1 — Template

```bash
Subject: Customer update delay for agent profile access - next update 18:30 UTC
What customers/agents see: A small set of agent profile openings is failing for synthetic customer IDs CUS-1001 and CUS-1002.
What we are doing: We are validating the event path and rolling back the suspected change to restore normal service.
When next update: 18:30 UTC.
What we need from you: Please hold off on broad change activity while we confirm the rollback and monitor the queue.
```

## Step 2 — Translate

Replace jargon (DLT, digest) with plain language or short gloss.

Plain-language version:

- “queue” instead of “DLT pipeline”
- “rollback to the previous working version” instead of “digest rollback”
- “service is being restored” instead of “recovery is in flight”

## Step 3 — Consistency

Ensure severity/impact matches the fact base—no contradictions.

This message stays consistent with SEV-2, the same synthetic customer fixtures, and the 18:30 UTC update time.

## Step 4 — Scrub

Remove any token-looking strings or real emails.

No tokens, emails, or production identifiers were included.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.