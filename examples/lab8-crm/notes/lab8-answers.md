#### Concepts to Discuss
1. The main data or request flow once create-customer is implemented (even though stubs only today)
2. The trust boundary and which layer will own input validation later
3. The success and failure contract for “create customer” (happy path vs `CustomerNotFoundException` later)
4. Stable identity (`CUS-1001`) versus display name (`Amina Khan`)
5. Retry and idempotency implications at the repository boundary
6. Local development shortcut versus production design (in-memory vs PostgreSQL)
7. Logs, metrics, or UI evidence support will need once APIs exist (`lab-request-001`)
8. Behavior with two application instances sharing the same customer IDs
9. Why entity must not import controller (layer direction)
10. What belongs in `dto` vs `entity` for the same Amina Khan create request
#### Step 3 - Layers
| Layer concept | Package folder | Owns | Must NOT own |
| ------------- | -------------- | ---- | ------------ |
| Presentation | `controller` | Accept/return DTOs; map calls | SQL, business rules |
| Business | `service` | Rules, orchestration | HTTP headers, JDBC details |
| Persistence | `repository` | Save/find | REST mapping |
| Domain | `entity` | Customer fields | Request JSON shapes |
| Contracts | `dto` | Request/response | Persistence annotations (later JPA stays on entity) |
| Cross-cutting | `config`, `exception` | Wiring, failure types | Happy-path create logic |
#### Step 13 - Failure Experiments
1. Rename `pom.xml` temporarily; run `mvn compile`
   - Observed: Maven cannot find POM -> build failure
   - Fix: Restore filename
2. Run `mvn clean compile` twice
   - Observed: Second run still `BUILD SUCCESS`
   - Fix: Keep both output in notes
3. Temporarily `import com.northstar.crm.controller.CustomerController` inside `CustomerRepository`
   - Observed: Technically compiles but layer rule is violated
   - Fix: Remove bad import
#### Implementation Checkpoints
- Checkpoint A
  1. Pass
  2. Pass
  3. Pass
  4. Pass
- Checkpoint B
  1. Pass
  2. Pass
  3. Pass
  4. Pass
- Checkpoint C
    1. Pass
    2. Pass
    3. Pass
- Checkpoint D
    1. Pass
    2. Pass
    3. Pass
#### Manual Verification
1. `pwd` is `.../lab8-crm` (or agreed alternate name).
   - Pass
2. `mvn clean compile` prints `BUILD SUCCESS`.
   - Pass
3. `find src/main/java -name '*.java' | sort` lists all expected stubs + `Main`.
   - Pass
4. `java -cp target/classes com.northstar.crm.Main` prints packages + `CUS-1001` / `CUS-1002`.
   - Pass
5. `docs/CODING-STANDARDS.md` and `docs/layer-flow.md` exist and mention layers.
   - Pass
6. `rg springframework src` (or equivalent search) finds nothing required.
   - Pass
7. `git check-ignore -v target` (or `git status`) shows `target/` untracked/ignored.
   - Pass
8. Stub call intentional failure: repository `findById("CUS-1001")` throws `UnsupportedOperationException` if you exercise it from a temporary harness.
   - Pass
9. Re-run compile twice—second run still succeeds.
   - Pass
10. Notes include correlation ID `lab-request-001` and NOW vs FUTURE boundaries.
    - Pass
#### Security and Production Review
1. Which browser, network, event, or database inputs are untrusted? *(Design: future API inputs)*
   - All external inputs, including browser requests, API calls, network data, events, and future database input, should be treated as untrusted until validated.
2. Where are authentication, authorization, and validation enforced? *(Which layer will own them?)*
   - Authentication and authorization should be enforced at the API/service layer, while input validation should occur before business logic is executed.
3. Which values are sensitive, and where are they stored? *(None in Lab 8—keep it that way)*
   - Lab 8 does not store sensitive values such as passwords or API keys, and it should remain that way.
4. What can be retried safely? *(`mvn compile`; not “create customer” yet)*
   - Build commands like mvn compile are safe to retry because they have no side effects, while operations like creating a customer should not be retried without safeguards.
5. What happens after a partial failure? *(Stub methods throw before storing)*
   - Stub methods fail before saving any data, so no partial or inconsistent state is left behind.
6. What would an operator monitor later? *(API latency, DB health—note the gap)*
   - In a production system, operators would monitor API response times, database health, error rates, and system availability.
7. Which local default is unacceptable in production? *(Empty stubs / no auth / later in-memory without hardening)*
   - Empty stub implementations, missing authentication, and unsecured in-memory storage are acceptable for a lab but not for a production system.
8. How are schema/event/API contracts versioned later? *(Packages + future WSDL/OpenAPI labs)*
   - Future versions can be managed through package versioning and API specifications such as WSDL or OpenAPI to maintain compatibility.
#### Reflection Questions
1. Which design decision most affected correctness of the skeleton?
   - Choosing to go for layers and stubs impacted the correctness of the skeleton the most. Without a good scaffolding to build the rest of our system upon, it could crumble. Each layer has a single responsibility, making the code easier to understand and maintain. It also reduces coupling between different parts of the application.
2. Which failure was hardest to diagnose (pathing, packages, POM)?
   - Package and directory mismatches were the hardest to diagnose because they often caused "class not found" or import errors. These problems were not always obvious until the project was compiled. Keeping package names consistent solved most of these issues.
3. What evidence proves the layered structure is real, not only aspirational?
   - The classes interact only through their intended layers instead of accessing everything directly. DTOs, services, and repositories each have separate responsibilities. This separation shows the architecture is actually being followed.
4. What breaks first at ten times the team size if packages are messy?
   - Developers would have trouble finding code and avoiding duplicate functionality. Merge conflicts and incorrect imports would become much more common. A clear package structure helps large teams work independently.
5. Which concern should move to shared infrastructure later?
   - Logging, authentication, configuration, and exception handling should eventually move into shared infrastructure. This avoids repeating the same code across multiple services. Centralized infrastructure also makes maintenance easier.
6. What must change before real customer data is used?
   - The application needs authentication, authorization, secure data storage, and proper validation before handling real customer information. Sensitive data should be protected and stored securely. Audit logging and error handling should also be added.
7. How does this lab connect to Labs 9–12 and later CRM platform pieces?
   - This lab provides the project structure that later labs will build on. Future labs will add databases, APIs, Spring Boot, and more business logic while keeping the same layered design. The CRM platform grows by extending this foundation.
8. What metric, log field, query plan, or UI state matters most once APIs exist?
   - API response time is one of the most important metrics because it directly affects users. Error rates and request logs are also valuable for troubleshooting. Database query performance becomes important as data grows
9. Why keep DTOs separate from entities for creating Amina Khan (`CUS-1001`)?
   - DTOs expose only the data needed by the client, while entities represent the internal data model. Keeping them separate prevents accidental exposure of internal fields. It also makes it easier to change the database model without affecting the API.
10. (Forward look) When Spring Boot arrives, which packages stay stable vs which files change first?
    - The domain packages, such as models, DTOs, and business services, should remain mostly stable. Configuration files, controllers, repositories, and application startup classes are more likely to change first. This keeps the core business logic independent of the framework.
