# Lab 44 — Release checklist

## Go / No-Go

| # | Check                                                                                                      | Status (GO/NO-GO) | Evidence / notes                                                                                                     |
|---|------------------------------------------------------------------------------------------------------------|-------------------|----------------------------------------------------------------------------------------------------------------------|
| 1 | Manifest `jarSha256` matches downloaded Lab 43 `crm-jar` (not a local rebuild)                             | GO                | `jarSha256=314c1350fdb567954c816ce714c72640d18ddc495859de3314b91566dd095d2b` matches `dist/SHA256SUMS`               |
| 2 | Lab 43 verify gate green                                                                                   | PENDING           | External GitHub Actions run URL required; this repo has the immutable SHA but not the live run metadata              |
| 3 | Staging smoke: readiness + `GET /api/customers?status=ACTIVE` + `lab-request-001` (or documented tabletop) | PENDING           | No live k3d cluster was used in this session; record curl output or named tabletop if later executed                 |
| 4 | Rollback target recorded **before** promote (`knownGoodPrevious.jarSha256` / prior version or Image Id)    | GO                | Baseline version `1.4.0-lab44` and SHA are recorded in `artifact-manifest.json` and `rollback-runbook.md`            |
| 5 | Security gate residual risks accepted with owners (Lab 40)                                                 | PENDING           | Fill in owner name + acceptance note when approvals are captured                                                     |
| 6 | No secrets in Git, manifest, or release notes                                                              | GO                | Manifest and runbook use names/placeholders only; no credential values are committed                                 |
| 7 | `crm-cd.yml` is at the **git root**; no `mvn` in CD                                                        | GO                | `.github/workflows/crm-cd.yml` is at repo root and the workflow downloads the Lab 43 artifact rather than rebuilding |
| 8 | Approver + timestamp recorded                                                                              | PENDING           | Complete Decision section below when production approval is captured                                                 |

## Decision

- **Decision:** PENDING
- **Approver:** PENDING
- **Date/time:** PENDING
- **Rationale:** The immutable SHA and rollback baseline are recorded. Final GO requires the Lab 43 run URL, staging
  smoke evidence, security owner acceptance, and production approver timestamp.