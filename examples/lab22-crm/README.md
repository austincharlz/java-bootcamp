#### Overview

Built a Maven-based CRM project using a layered architecture. The project separates models, DTOs, services,
repositories, and exceptions to create a clean foundation for future Spring Boot, database, and REST API development.

#### Commands (PowerShell)

- **Clean/Compile/Run**

```bash
mvn clean compile exec:java "-Dexec.mainClass=com.northstar.crm.Main"
```

- **Compile and Verify**

```bash
mvn -q clean compile
Get-ChildItem -Path src/main/java -Recurse -Filter *.java |
    Sort-Object FullName |
    Select-Object -ExpandProperty FullName
git status
```

- **Test**

```bash
mvn -q -Dtest=CustomerApiIT test
mvn -q -Dtest=CustomerUiIT test
```

- **Spring Boot**

```bash
mvn spring-boot:run
```

- **Cleanup**

```bash
mvn -q clean
```

- Make sure to stop the app as well.

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

####  
