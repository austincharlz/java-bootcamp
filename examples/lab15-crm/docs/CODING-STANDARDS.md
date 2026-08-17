# Northstar CRM Coding Standards

## Layers

- controller: transport / API mapping only
- service: business rules
- repository: persistence
- entity: domain model
- dto: request/response contracts
- config: wiring
- exception: domain and API failures

## Hard rules

- Services must not depend on controllers.
- Entities must not carry HTTP or SOAP types.
- Repositories must not import controllers.
- No production passwords or API keys in source.
- Prefer CUS-#### for stable customer identities in examples.
- Stubs may throw UnsupportedOperationException — that is success for Lab 8, not a bug.
- Never accept, commit, or merge AI-generated code unless at least one developer on the team fully understands how it
  works, reviews it for correctness, security, and readability, and verifies it with appropriate testing.

## Compliance Self-Check

| # | Confirm                                                 | Your notes |
|---|---------------------------------------------------------|------------|
| 1 | Meaningful type and method names                        | Pass       |
| 2 | No raw types in new code                                | Pass       |
| 3 | Validation in clear helpers                             | Pass       |
| 4 | Exceptions instead of null for errors                   | Pass       |
| 5 | No production secrets / no PII beyond lab sample emails | Pass       |
| 6 | Service still compiles without Spring/JPA/Kafka         | Pass       |