# Lab 45 — Fill AI Prompt TODOs

## Step 1 — Template

Fill blanks:
Goal: Generate secure Terraform and Ansible configuration for a small cloud-hosted web application. Environment: AWS
with Terraform and Ansible running in a controlled CI/local shell. Must include: VPC, subnets, security groups,
least-privilege IAM, load balancer, application host, installation steps, and outputs. Must forbid: secrets, public DB,
hardcoded credentials, localhost-only assumptions, and insecure internet exposure. Assumptions: Use existing
account/region/project settings, no production secrets are provided, and internet access is limited to required
endpoints. Output files: infra/terraform/*.tf, infra/ansible/site.yml

## Step 2 — Harden

Add explicit “do not invent credentials” and “mark TODOs for human review”.

Prompt hardening notes:

- Do not invent credentials, keys, tokens, or passwords.
- If a required secret is missing, state the exact missing value and mark TODOs for human review.
- Prefer least-privilege networking and deny public database access by default.
- Clearly label any assumption or missing requirement as a human review item.

## Step 3 — Rejection plan

Rejected AI suggestion: "Open the database security group to 0.0.0.0/0 on port 5432 and set the database username and
password to admin/admin123."
Why reject it: This exposes the database publicly and hardcodes credentials, which violates the principle of least
privilege and creates a secret management risk. A secure design would keep the database private, use managed secrets,
and require explicit human approval before any privileged access path.

## Step 4 — Scope

Prompt only—full generate/validate is Lab 45.

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.