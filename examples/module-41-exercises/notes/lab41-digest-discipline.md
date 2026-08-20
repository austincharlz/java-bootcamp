# Lab 41 — Digest vs Latest

## Step 1 — Define

- Image digest: cryptographic SHA-256 hash that uniquely identifies container contents.
- Mutable tag: human-readable label that can be reassigned to point to different images over time.

## Step 2 — Check the reference

Lab 42/44 promote by digest; `:latest` can drift between staging and prod.

## Step 3 — CRM example

Write an example tag scheme: `crm-api:lab41` plus digest note placeholder `sha256:(your note here)`.

## Step 4 — Runbook heading

Add a `docs/container-runbook.md` heading list: build, inspect user, run, stop, digest capture.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.