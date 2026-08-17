# Lab 36 — Token Storage Options

## Reference

| Option | Risk / note |
| --- | --- |
| In-memory variable | Lost on refresh; safer from XSS persistence |
| sessionStorage | Per-tab; XSS can read |
| localStorage | Survives refresh; XSS can read |
| HttpOnly cookie | Not JS-readable; needs CSRF strategy |

## Step 1 — Study table

Copy the reference table.

## Step 2 — Lab choice
In-memory because it is only a lab. It is also gives the most protection.

## Step 3 — Never

Never commit tokens; never put DB passwords in Vite env.

## Step 4 — Fixture

Use fake token `lab-token-001` in notes only — not a real secret.

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.
