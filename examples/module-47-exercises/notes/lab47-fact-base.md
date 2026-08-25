# Lab 47 — Build Shared Fact Base

## Reference

| Audience     | Needs                                   |
|--------------|-----------------------------------------|
| Responders   | Symptoms, impact, next update time      |
| Engineers    | Change, evidence, rollback              |
| Reviewers    | PR verify + risk                        |
| Stakeholders | Business impact, ETA, no jargon pile-up |

## Step 1 — Lab scenario

Use: SEV-2, some agents HTTP 503 opening profiles, start time UTC 2026-08-25 16:00Z, suspected `crm-api` 1.4.0, fixtures
`CUS-1001`/`CUS-1002`, correlation `lab-request-001`.

## Step 2 — Check the reference

Confirmed:

- SEV-2 incident affecting agent profile opens in a limited lab traffic path.
- Affected synthetic fixtures are `CUS-1001` and `CUS-1002`.
- Correlation ID `lab-request-001` is present in the failing events.
- `crm-api` 1.4.0 is the suspected change set.

Assumed:

- The issue is related to Kafka event processing or downstream profile refresh.
- The impact is limited to the synthetic lab environment, not production data.
- A rollback to the 1.3.2 digest is the likely recovery step.

Unknown:

- The exact root-cause code path behind the HTTP 503s.
- Whether other customer IDs or agent actions are affected beyond the observed fixtures.
- The full lag or retry profile across the Kafka consumers.

## Step 3 — Mitigation stub

Rollback toward the 1.3.2 digest and watch service readiness and Kafka lag before re-enabling traffic. Validate the
profile-open path with the synthetic fixtures and confirm the queue drains without duplicate side effects.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.