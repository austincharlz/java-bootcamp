# Lab 28 — Production IdP Checklist

## IdP note
Prefer enterprise IdP / OAuth2 in production (lab JWT is teaching)

## Key rotation
Store signing keys in a secret manager; rotate on schedule/incident

## Transport / TTL
Short token TTL; HTTPS only

## Logging hygiene
Audit failed logins; never log raw bearer tokens; enforce the least privilege roles; review admin grants

## Scope
Pre-lab only. No real secrets.
