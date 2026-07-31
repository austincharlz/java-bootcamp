#### Step 8 - Service Responsibilities
- Bean Validation protects the API boundary, while CustomerValidator protects the business rules. 
- Allowed Transitions 
```bash
PROSPECT  -> ACTIVE, CLOSED
ACTIVE    -> SUSPENDED, CLOSED
SUSPENDED -> ACTIVE, CLOSED
CLOSED    -> (none)
```
- The application manually creates and injects dependencies by constructing CustomerRepository, CustomerValidator, DefaultCustomerService, and CustomerApiFacade in Main. This mirrors how Spring dependency injection will automatically manage and provide these components later.
- Same-status transitions will be treated as a noop because changing ACTIVE to ACTIVE does not violate business rules and avoids unnecessary errors for idempotent requests. The service will simply return the existing customer without changing state.