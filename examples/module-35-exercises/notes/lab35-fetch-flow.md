# Lab 35 — Fetch Flow

## Step 1 — States

`idle | loading | success | error` for the list view.

## Step 2 — Sequence

Mount → set loading → fetch → set data (Amina/Ravi) or error message.

## Step 3 — Abort

Note AbortController on unmount to avoid setState after navigate away.

## Step 4 — Empty

Draft empty-state copy when API returns [].

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.