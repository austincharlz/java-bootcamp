# Lab 45 — AI IaC review record

## Contract (human-controlled baseline)

This lab is a non-production infrastructure sketch for Northstar CRM only. It is not a production deployment and must
never target Lab 42 `k3d` or any other live runtime without explicit instructor approval.

- Environment: `dev`, `test`, or `staging` only. No `prod` values allowed.
- Region: use a neutral non-prod region placeholder; no hard-coded cloud credentials or live account IDs.
- Network: no public DB endpoints, no `0.0.0.0/0` ingress, no public SSH exposure, no public-facing database access.
- Runtime: this is a Terraform/Ansible sketch for local validation and review only. `hashicorp/null` is the default
  laptop-safe path unless a sandbox is explicitly approved.
- Database: all database resources must remain private-only and must not be publicly reachable.
- Tags: include application/environment ownership tags; no customer identifiers or PII in IaC.
- Cost limits: keep the sketch intentionally small and non-prod; avoid NAT gateways, large databases, or always-on
  infrastructure.
- Secrets: never commit real `*.tfstate`, Terraform variables, cloud keys, kubeconfig, vault passwords, or database
  credentials to Git.
- Forbidden: public database, `0.0.0.0/0` on DB/SSH, plaintext secrets, production values, or direct apply to live
  infrastructure.

## Prompts used (summarized)

Prompt entry: `lab45-001`

> Generate a non-prod Terraform sketch for Northstar CRM with environment in `dev|test|staging`. Use `hashicorp/null`
> for the laptop path unless a sandbox is explicitly authorized. No public database. No `0.0.0.0/0` on DB or SSH. No
> plaintext secrets. No production values. Pin provider versions. Keep the structure small and reviewable: separate
> providers, variables, resource definitions, and outputs. Include assumptions and a short human review checklist.

### Assumptions recorded from the prompt

- This lab is a sketch only, not an applied cloud deployment.
- The valid environment set is limited to `dev`, `test`, and `staging`.
- `null_resource` is acceptable for local validation when no approved cloud sandbox exists.
- A real database is out of scope unless the instructor approves a private non-prod sandbox.
- All credentials and state remain external to Git.
- Human review is required before any real deployment or apply action.

## AI suggestions accepted

| Item                                                      | Why accepted                                                                            |
|-----------------------------------------------------------|-----------------------------------------------------------------------------------------|
| Use a minimal `null_resource` sketch for local validation | Safe for this lab path; avoids cloud auth requirements while still proving HCL validity |
| Keep environment values limited to dev/test/staging       | Aligns with the lab contract and prevents accidental prod drift                         |
| Use placeholders for tfvars and sensitive inputs          | Keeps secrets out of source control while preserving a valid input model                |

## AI suggestions rejected or hardened

| Item                       | Risk                                  | Human change                                                    |
|----------------------------|---------------------------------------|-----------------------------------------------------------------|
| Public database endpoint   | Exposure / accidental internet access | Force private-only DB design; no `0.0.0.0/0` in DB or SSH rules |
| Hard-coded secret value    | Secret leakage in Git                 | Replace with `sensitive` variable and example tfvars only       |
| Unpinned provider versions | Drift and unexpected behavior         | Pin exact provider versions in `providers.tf`                   |

## State and locking controls

Terraform state is sensitive and must never be committed. The recommended pattern is an encrypted remote backend with
locking and least-privilege access, such as an S3/DynamoDB pattern or an equivalent managed backend in an approved
sandbox. Credentials and access keys must come from environment variables, workload identity, or a secure secret store,
not from source control. Local state files, `.terraform` directories, and real `terraform.tfvars` files remain excluded
by Git rules and must be removed before any commit.

## Validation evidence

- `terraform fmt -recursive`: succeeded
- `terraform init -backend=false`: succeeded
- `terraform validate`: succeeded (`Success! The configuration is valid.`)
- `terraform plan -var='environment=dev' -var='db_password=unused-local'`: succeeded; plan shows 1 null resource to add
  and no apply was performed
- Screenshot captured at: `notes/screenshots/lab-45/step-5-plan.png` (also mirrored in the lab folder for convenience)
- `ansible-playbook --syntax-check -i inventory.example.yml infra/ansible/site.yml`: attempted from the lab root, but
  the command is not installed on this Windows workstation (`ansible-playbook` command not found)
- Optional `ansible-lint infra/ansible/site.yml`: not run because Ansible tooling is absent in this environment

## Residual risks

- Owner: student / reviewer
- Expiry: review before any future apply or sandbox use
- No customer PII in IaC
- No real credentials, state, or secret material committed to the repo
- Ansible syntax check is recorded as residual risk because the required tooling is not installed on this machine

## Failure experiments (local only)

| # | Confirm                                      | Result |
|---|----------------------------------------------|--------|
| 1 | Work in `java-bootcamp`; starter IaC only    | Pass   |
| 2 | `validate` (no `-var`) + plan read           | Pass   |
| 3 | AI review includes ≥1 reject/harden          | Pass   |
| 4 | No `*.tfstate` / real `tfvars` / keys in Git | Pass   |

`git status --short --branch` was reviewed locally; no state or secret files were staged or committed for this lab.

## Approval

- Approved by: Austin Charlz
- Date: 2026-08-24
