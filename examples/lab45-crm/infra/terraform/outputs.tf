output "environment" {
  description = "Validated non-production environment for the CRM sketch"
  value       = var.environment
}

output "region" {
  description = "Target region for the local sketch"
  value       = var.region
}

output "sketch_note" {
  description = "Human review note for the non-prod null sketch"
  value       = "This sketch stays non-prod and private-only; a real database must not be publicly reachable."
}
