terraform {
  required_version = ">= 1.5.0"

  required_providers {
    null = {
      source  = "hashicorp/null"
      version = "3.2.0"
    }
  }

  # Remote state should be backed by a secure backend with locking and encryption,
  # but credentials must never be committed to this repository.
  # backend "s3" { ... }  # credentials from environment/OIDC only
}
