# Lab 29 — DTO Constraint Plan

| Field | Constraints |
| --- | --- |
| fullName | @NotBlank, @Size |
| email | @NotBlank, @Email |
| status | @NotNull (or allowed values) |

Trigger: @Valid on controller create method.

## Scope
Pre-lab only.