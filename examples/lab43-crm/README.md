# Lab 43 CRM

CI lab for the CRM in `java-bootcamp`.

## Key paths

- Workflow: `.github/workflows/crm-ci.yml`
- App: `examples/lab43-crm`
- Runbook: `examples/lab43-crm/docs/ci-runbook.md`

## Local verify

```powershell
cd $env:USERPROFILE\java-bootcamp\examples\lab43-crm
mvn -B -ntp clean verify
```

## CI notes

- PRs run `verify` only.
- `main` and `v*` run `verify + package`.
- `NVD_API_KEY` is optional and stays in GitHub Secrets.
- `crm-jar` includes the JAR, `SHA256SUMS`, and `GITHUB_SHA`.
