# Lab 45 — Plan Terraform Checks

## Step 1 — Command order

`terraform fmt`, `init -backend=false`, `validate` (**no** `-var`), `plan -var=environment=dev` (and
`-var=db_password=…` on **plan** only).

## Step 2 — Check the reference

Read the plan: create/destroy risk before any apply discussion.

## Step 3 — State narrative

Write three bullets on encrypted remote state + locking without committing backend credentials.

## Step 4 — Evidence path

Note sanitized plan snippets go under `notes/screenshots/lab-45/`.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.