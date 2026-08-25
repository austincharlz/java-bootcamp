variable "environment" {
  type        = string
  description = "Non-production CRM environment. Allowed values: dev, test, staging"

  validation {
    condition     = contains(["dev", "test", "staging"], var.environment)
    error_message = "environment must be one of: dev, test, staging. Production values are forbidden in this lab."
  }
}

variable "region" {
  type        = string
  description = "Target cloud region for the non-prod sketch"
  default     = "us-east-1"
}

variable "db_password" {
  type        = string
  description = "Sensitive database password; supply locally or via a secret manager; do not commit"
  sensitive   = true
  default     = ""
}
