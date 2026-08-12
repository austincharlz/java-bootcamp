# Lab 32 — Fallback Contract

## Step 1 — Fields kept

List fields still shown: customerId, displayName maybe, status UNKNOWN.

## Step 2 — Fields dropped

List fields omitted: balance, tier, lastLogin.

## Step 3 — API signal

Decide: HTTP 200 with `degraded=true` because the API is still returning a valid response, but the data is incomplete.

## Step 4 — User message

Draft one UI string: *Account details temporarily limited.*

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.