# Lab 35 — CORS and Headers

## Step 1 — Origins

Typical: UI `http://localhost:5173`, API `http://localhost:8080` (adjust if your lab differs).

## Step 2 — CORS

Browser blocks cross-origin XHR unless Spring allows the UI origin.

## Step 3 — Correlation

Plan header e.g. `X-Correlation-Id: lab-request-001` on fetches.

## Step 4 — Secrets

Do not put DB passwords in frontend env — only public API base URL.

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.