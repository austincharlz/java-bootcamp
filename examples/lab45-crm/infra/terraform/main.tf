# Lab 45 — CRM infra sketch (safe local validate without cloud apply)
# This is intentionally a non-prod sketch. Real VPC/DB/runtime modules are only allowed
# in an approved sandbox after human review.
# FORBIDDEN: publicly reachable database, hardcoded passwords, and open 0.0.0.0/0 SSH.

locals {
  tags = {
    application = "crm"
    environment = var.environment
    managed_by  = "terraform"
    purpose     = "non-prod-sketch"
  }
}

resource "null_resource" "crm_stack_sketch" {
  triggers = {
    environment = var.environment
    region      = var.region
    sketch      = "northstar-crm-non-prod"
    private_db  = true
  }

  # Real infrastructure would require a private subnet and no public DB endpoint.
}
