# Lab 44 CRM

This project is the Lab 44 continuous-delivery exercise for the Northstar CRM.

## Purpose

Promote a single immutable Lab 43 artifact through the release pipeline without rebuilding the JAR in deployment. The
release is anchored to the validated `crm-jar` artifact and checksum from Lab 43.

## Core rules

- Use the Lab 43 artifact identity (`gitCommit` + `jarSha256`), not a fresh Maven build.
- Keep the CD workflow at the repository root: `.github/workflows/crm-cd.yml`.
- Verify the artifact checksum before deploy.
- Smoke test with `GET /api/customers?status=ACTIVE` and `X-Correlation-Id: lab-request-001`.
- No secrets, kubeconfig, or token values in Git.
- Do not invent `ghcr.io` digests unless an image was actually pushed.

## Key files

- `artifact-manifest.json` — immutable release identity and prior known-good record
- `docs/release-plan.md` — environment flow and DB compatibility notes
- `docs/release-checklist.md` — objective GO/NO-GO checklist
- `docs/rollback-runbook.md` — rollback procedure and verification steps
- `.github/workflows/crm-cd.yml` — root workflow for manual promotion

## Typical flow

1. Download the Lab 43 `crm-jar` artifact.
2. Validate the SHA256 in `artifact-manifest.json`.
3. Promote by checksum to the target environment.
4. Smoke the list API and record evidence.
5. Keep rollback target information ready before any promotion.

## Notes

This lab intentionally avoids Maven in the CD path. The release candidate must be the same bytes that passed Lab 43 CI.
