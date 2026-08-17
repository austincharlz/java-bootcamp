#### Overview

Built a Maven-based CRM project using a layered architecture. The project separates models, DTOs, services,
repositories, and exceptions to create a clean foundation for future Spring Boot, database, and REST API development.

#### Compile, Run, and Cleanup (PowerShell)

- **Compile and Run**

```bash
cd "$HOME/java-bootcamp/examples/lab9-crm"
mvn -q clean compile
java -cp target/classes com.northstar.crm.Main
```

- **Compile and Verify**

```bash
cd "$HOME/java-bootcamp/examples/lab9-crm"
mvn -q clean compile
Get-ChildItem -Path src/main/java -Recurse -Filter *.java |
    Sort-Object FullName |
    Select-Object -ExpandProperty FullName
git status
```

- **Cleanup**

```bash
cd "$HOME/java-bootcamp/examples/lab9-crm"
mvn clean
git status
```

- Package Jar and Run

```bash
mvn -q clean package
jar tf target/customer-service.jar | Select-Object -First 10
java -jar target/customer-service.jar
```

#### Design Decisions

- Used a layered architecture (model, service, repository, DTO, exception) to separate responsibilities.
- Kept DTOs separate from domain models to prepare for future APIs.
- Defined repository and service interfaces with stub implementations to allow future database integration.
- Used Maven's standard project layout for compatibility with build tools and Spring Boot.
- Centralized package names under `com.northstar.crm` for consistency and maintainability.
- Avoided storing sensitive data or implementing authentication until later labs.

#### CI note (preview — pipelines deepen in later modules)

Preferred verify command on agents:

    mvn -B verify

`-B` is batch mode (non-interactive). Prefer `verify` over `install` on CI unless your pipeline intentionally publishes
to an artifact repository. Never deploy snapshots from a developer laptop without agreement.

Artifact coordinates: com.northstar:customer-service:0.1.0-SNAPSHOT Sample customer IDs (docs only): CUS-1001, CUS-1002
Correlation ID (logs later): lab-request-001