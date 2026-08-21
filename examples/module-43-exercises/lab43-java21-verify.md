# Lab 43 — Plan JDK 21 Verify Job

## Step 1 — Setup

List Actions steps: checkout, setup-java Temurin 21 with Maven cache
(`cache-dependency-path: examples/lab43-crm/pom.xml`), `mvn -B clean verify` with
`working-directory: examples/lab43-crm`.

## Step 2 — Check the reference

Upload Surefire/Failsafe reports even on failure (`if: always()`).

## Step 3 — Failure drill plan

Write how you will intentionally break one test, observe CI red, then restore (plan only).

## Step 4 — Local habit

Note local preflight: `java -version` shows 21; `mvn -v` before pushing (Lab 41 has no `mvnw`).

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.