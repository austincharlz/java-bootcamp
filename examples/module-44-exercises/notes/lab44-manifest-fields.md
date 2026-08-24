# Lab 44 — Sketch Artifact Manifest

## Step 1 — Fields

semver, git_sha, **required** jar_sha256 (from Lab 43 `SHA256SUMS`, not a local rebuild), optional image_digest (null if
never pushed), known_good_previous, pipeline_run_url.

## Step 2 — Check the reference

Prod candidate must match staging **`jarSha256`** exactly. Do not invent `ghcr.io/…@sha256:…`.

## Step 3 — Sample JSON

Write a JSON stub with placeholder `jarSha256`, `imageDigest: null`, and version `1.4.0-rc.1`.

```bash
{
  "version": "1.4.0-rc.1", "gitSha": "<commit-sha>",
  "jarSha256": "<calculated-value>", "imageDigest": "sha256:<registry-digest>",
  "knownGoodPrevious": { "version": "1.3.2", "imageDigest": "sha256:<prior-digest>" }
}
```

## Step 4 — Rollback target

Add `known_good_previous` example `1.3.2` + digest placeholder.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.