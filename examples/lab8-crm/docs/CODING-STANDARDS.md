# Northstar CRM Coding Standards (Lab 8)
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