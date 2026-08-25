# Lab 43 CI Runbook

## Must-haves

- Repo root workflow: `.github/workflows/crm-ci.yml`
- Work from `java-bootcamp`
- Keep secrets out of YAML

## Secrets

- `NVD_API_KEY` only
- Registry tokens are for Lab 44

## Triggers

- Pull request: `verify`
- `main` or `v*`: `verify + package`
- No deploy job in this lab

## Recovery

1. Re-run flaky Actions jobs.
2. Fix failing tests locally with `mvn -B -ntp clean verify`.
3. Confirm Surefire artifacts uploaded with `if: always()`.

## Failure experiment

- Break one real unit test.
- Watch verify fail.
- Restore the test and rerun green.

## Artifact

- `crm-jar` is produced only on `main` and `v*`.
- It contains the JAR, `SHA256SUMS`, and `GITHUB_SHA`.
- Lab 44 must download it instead of rebuilding.
