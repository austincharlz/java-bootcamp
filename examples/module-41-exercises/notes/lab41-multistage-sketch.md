# Lab 41 — Sketch Multi-Stage Build

## Reference

| Stage   | Contains                    | Must not contain       |
|---------|-----------------------------|------------------------|
| build   | JDK 21, Maven, sources      | runtime secrets        |
| runtime | JRE, app JAR, non-root user | Maven, .git, passwords |

## Step 1 — Stages

Name two stages: `build` (Maven + JDK 21) and `runtime` (JRE 21). List what copies between them (the JAR only).

## Step 2 — Check the reference

Runtime must not include Maven, source, or `.git`. Prefer Temurin/Eclipse JRE base images as instructed.

## Step 3 — User

Plan non-root UID (example `10001`) and note why root fails the lab.

## Step 4 — CRM note

State that fixtures `CUS-1001`/`CUS-1002` are app data at runtime—not build args.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.