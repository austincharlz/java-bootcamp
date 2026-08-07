# Lab 26 — Profile Purposes

| Profile | Purpose                                       |
| --- |-----------------------------------------------|
| dev | Local CRM smoke; relaxed logging; H2-friendly |
| test | Surefire/BootTest isolation                   |
| prod | Deployed settings; secrets via env; fail fast |

## One risk if prod uses dev YAML
Sensitive information could be exposed.

## Scope
Pre-lab only.