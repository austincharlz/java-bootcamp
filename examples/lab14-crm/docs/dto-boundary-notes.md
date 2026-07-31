#### Entity vs DTO
Entity:
- Represents the internal domain model used by the service layer.
- Contains business data and behavior.
- Stored/managed by the application (for example, Customer with createdAt and status).
- Should not be exposed directly through the API.

DTO (Data Transfer Object):
- Represents data sent to or received from an API boundary.
- Controls the API contract and hides internal implementation details.
- Can have a different structure from the entity (for example, Instant timestamps instead of LocalDateTime).
- Used to validate requests and format responses.