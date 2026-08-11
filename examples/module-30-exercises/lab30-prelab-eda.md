# Lab 30 — Why Async for CRM

## Step 1 — List sync pain
1. Slower response times to so synchronous processing.
2. Failure coupling if one service fails.
3. Thread/resource usage is not ideal since HTTP request thread is waiting for other downstream operations.

## Step 2 — Event idea
Publish a `CustomerCreated` event to the message broker and send it out to all necessary services asynchronously.

## Step 3 — Coupling check
False. The Customer JVM does not need to run.

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.