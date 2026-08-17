#### Overview

Built a Maven-based CRM project using a layered architecture. The project separates models, DTOs, services,
repositories, and exceptions to create a clean foundation for future Spring Boot, database, and REST API development.

#### Commands (PowerShell)

- **Clean/Compile/Run**

```bash
mvn clean compile exec:java "-Dexec.mainClass=com.northstar.crm.Main"
```

or

```bash
cd "$HOME/java-bootcamp/examples/lab15-crm"
mvn -q clean compile
java -cp target/classes com.northstar.crm.Main
```

- **Compile and Verify**

```bash
cd "$HOME/java-bootcamp/examples/lab15-crm"
mvn -q clean compile
Get-ChildItem -Path src/main/java -Recurse -Filter *.java |
    Sort-Object FullName |
    Select-Object -ExpandProperty FullName
git status
```

- **Test**

```bash
mvn clean test
```

```bash
mvn -q test -Dtest=CustomerValidatorTest
```

- **Cleanup**

```bash
cd "$HOME/java-bootcamp/examples/lab15-crm"
mvn clean
git status
```

#### Validation rules (CustomerRequestDTO)

| Field      | Constraints                                     |
|------------|-------------------------------------------------|
| customerId | @NotBlank, @Size(max=32)                        |
| fullName   | @NotBlank, @Size(2..100)                        |
| email      | @NotBlank, @Email, @Size(max=254)               |
| status     | @NotBlank (ACTIVE\|PROSPECT\|SUSPENDED\|CLOSED) |

#### Sample invalid (email)

- email=not-an-email → IllegalArgumentException with field message
- correlationId=lab-request-001
