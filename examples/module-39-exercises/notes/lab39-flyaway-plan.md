# Lab 39 — Flyway Plan

## Step 1 — Version file

Name idea: `V1__crm_schema.sql` under `db/migration`.

## Step 2 — Content

Include customer + account DDL from Lab 37 design.

## Step 3 — Why Flyway

Schema changes are versioned and repeatable across machines.

## Step 4 — Anti-pattern

Avoid relying on `spring.jpa.hibernate.ddl-auto=create-drop` for shared envs.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.