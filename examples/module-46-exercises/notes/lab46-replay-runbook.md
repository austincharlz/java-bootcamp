# Lab 46 — Outline DLT Replay Runbook

## Step 1 — Steps

Inspect DLT (`docker exec crm-kafka … kafka-console-consumer.sh` on **`.DLT`**) → classify → dry-run → limited replay →
verify projection → stop criteria.

## Step 2 — Check the reference

Rate-limit replay; never replay blindly into prod topics.

## Step 3 — Evidence

Name what screenshots prove DLT landing and successful limited replay.

## Step 4 — Comms link

Note Lab 47 may communicate this class of incident—keep evidence shareable.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.