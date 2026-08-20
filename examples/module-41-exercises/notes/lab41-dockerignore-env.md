# Lab 41 — Plan .dockerignore and Env

## Step 1 — Ignore list

Draft `.dockerignore` candidates: `.git`, `target/`, `.env`, `*.tfstate`, `notes/`, IDE folders.

## Step 2 — Check the reference

Runtime config via env (DB URL, user, password)—never `ENV PASSWORD=...` in Dockerfile.

## Step 3 — .env.example

List keys only (no values): `CRM_DB_HOST`, `CRM_DB_PORT`, `CRM_DB_NAME`, `CRM_DB_USER`, `CRM_DB_PASSWORD` (empty in
example), `SPRING_PROFILES_ACTIVE=docker`. Local compose user is `crm`.

## Step 4 — Evidence path

Note where Lab 41 will store `docker images` / inspect evidence under `notes/screenshots/lab-41/`.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.