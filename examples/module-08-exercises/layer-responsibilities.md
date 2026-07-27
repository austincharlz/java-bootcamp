#### Step 1 — Assign the tasks
| Task | Layer      |
| ---- |------------|
| Accept future create-customer input | controller |
| Reject blank customer name | service    |
| Find customer by ID | repository |
| Represent customer ID/name/status | entity     |
| Represent create request fields | dto        |
| Define customer-not-found failure | exception  |
| Wire application objects later | config     |
#### Step 3 - Repair a "god controller"
```text
Controller maps request
→ Service validates/orchestrates
→ Repository saves/finds
→ Service returns result
→ Controller maps response
```
#### Step 4 - Explain boundaries
- isolated testing
  - Boundaries let each layer be tested independently, so it is easier to find and fix bugs.
- replacing storage without changing controller
  - Storage layer can be changed without modifying the controller or business logic.
- keeping transport concerns out of business logic
  - User input or HTTP requests handling stays in the controller, while the business logic focuses only on application rules. 
- making ownership discoverable
  - Clear boundaries give each class or layer a specific responsibility, making the code easier to understand, maintain, and extend.