# Lab 45 — Draft Infra Contract

## Reference

| Allowed in IaC           | Forbidden in IaC     |
|--------------------------|----------------------|
| Network/runtime sketches | Real cloud keys      |
| tfvars.example           | terraform.tfstate    |
| inventory.example.yml    | Customer PII         |
| Tags/labels              | Unreviewed public DB |

## Step 1 — Contract fields

env names (`crm-dev`/`crm-test`), region, network, runtime, DB, tags, cost limits, forbidden public exposure.

## Step 2 — Check the reference

Syntactically valid Terraform that opens a public DB still fails the lab.

## Step 3 — Tags

Propose tags: `application=crm`, `environment=dev`, `owner=(your note here)`.

## Step 4 — Data rule

State: fixtures `CUS-1001`/`CUS-1002` stay in app labs—not IaC state.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.