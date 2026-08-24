# Lab 44 — Delivery vs Deployment

## Reference

| Term                  | Meaning                                   |
|-----------------------|-------------------------------------------|
| Continuous delivery   | Main stays releasable; promote with gates |
| Continuous deployment | Every green build may auto-prod           |
| Immutable identity    | Digest/checksum, not :latest              |

## Step 1 — Definitions

Write two sentences: continuous delivery (always releasable) vs continuous deployment (auto-prod).

## Step 2 — Check the reference

This cohort emphasizes delivery with gates/approvals—not blind auto-prod.

## Step 3 — CRM example

Describe promoting the Lab 43 `jarSha256` that passed staging **`GET /api/customers?status=ACTIVE`** (Amina is an
`ACTIVE` list fixture — there is no `GET /api/customers/{id}`).

## Step 4 — Quiz yourself

Answer: if staging said GO on `jarSha256` X, what must prod receive?

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.