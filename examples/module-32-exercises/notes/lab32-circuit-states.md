# Lab 32 — Circuit States

## Step 1 — Closed

Normal calls flow; failures counted.

## Step 2 — Open

Calls fail fast / use fallback; Account Profile is not hammered.

## Step 3 — Half-open

Trial calls probe recovery; success → closed, failure → open.

## Step 4 — Draw

```mermaid
stateDiagram
    [*] --> Closed
    Closed --> Open : failures exceed threshold
    Open --> HalfOpen : wait duration elapsed
    HalfOpen --> Closed : trial success
    HalfOpen --> Open : trial failure
```

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.