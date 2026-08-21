# Lab 43 — Package-Once Identity

## Step 1 — Steps

Outline: package once, write `SHA256SUMS`, record `GITHUB_SHA`, upload artifact.

## Step 2 — Check the reference

The isolated `package` job re-runs Maven with skipTests (jobs do not share disks). Lab 44 must download `crm-jar` — a
third `mvn package` on the deploy agent breaks the chain.

## Step 3 — Example lines

Draft example checksum file lines (fake hashes OK) including commit id.

## Step 4 — Anti-pattern

Name one anti-pattern: packaging differently in deploy than in CI.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.