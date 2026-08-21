# Lab 42 — ConfigMap vs Secret Split

## Step 1 — Sort list

1. SPRING_PROFILES_ACTIVE
2. CRM_DB_HOST
3. CRM_DB_NAME
4. CRM_DB_USER
5. CRM_DB_PASSWORD
6. Log level

## Step 2 — Check the reference

Secret data is created out-of-band; Git only gets `secret.example.yaml` without values. Never `kubectl apply` the
example file.

## Step 3 — CRM fixtures

Confirm `CUS-1001`/`CUS-1002` are app fixtures, not K8s config keys.

## Step 4 — Write table

| ConfigMap                                   | Secret                        |
|---------------------------------------------|-------------------------------|
| Datasource URL host, Kafka boostrap servers | Database passwords            |
| JWT issue URI                               | TLS private keys/certificates |
| Log level, feature flags                    | Registry pull credentials     |

## Scope

Pre-lab only — do not finish the full graded lab in this exercise.