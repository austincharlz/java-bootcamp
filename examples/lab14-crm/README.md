#### Overview
Built a Maven-based CRM project using a layered architecture. The project separates models, DTOs, services, repositories, and exceptions to create a clean foundation for future Spring Boot, database, and REST API development.
#### Commands (PowerShell)
- **Clean/Compile/Run**
```bash
mvn clean compile exec:java "-Dexec.mainClass=com.northstar.crm.Main"
```
or
```bash
cd "$HOME/java-bootcamp/examples/lab14-crm"
mvn -q clean compile
java -cp target/classes com.northstar.crm.Main
```
- **Compile and Verify**
```bash
cd "$HOME/java-bootcamp/examples/lab14-crm"
mvn -q clean compile
Get-ChildItem -Path src/main/java -Recurse -Filter *.java |
    Sort-Object FullName |
    Select-Object -ExpandProperty FullName
git status
```
- **Test**
```bash
mvn test
```
- **Cleanup**
```bash
cd "$HOME/java-bootcamp/examples/lab14-crm"
mvn clean
git status
```
#### Validation rules (CustomerRequestDTO)
| Field | Constraints |
| ----- | ----------- |
| customerId | @NotBlank, @Size(max=32) |
| fullName | @NotBlank, @Size(2..100) |
| email | @NotBlank, @Email, @Size(max=254) |
| status | @NotBlank (ACTIVE\|PROSPECT\|SUSPENDED\|CLOSED) |
#### Sample invalid (email)
- email=not-an-email → IllegalArgumentException with field message
- correlationId=lab-request-001
#### Implementation Checkpoints
Checkpoint A
- Pass
- Pass
- Pass
Checkpoint B
- Pass
- Pass
- Pass
- Pass
Checkpoint C
- Pass
- Pass
- Pass
Checkpoint D
- Pass
- Pass
- Pass
#### Reflection Questions
1. Separating the API DTOs from the Customer entity and using a mapper made the implementation more reliable by keeping validation and API data separate from the domain model.
2. The project compiled, the tests passed with mvn test, and the application successfully created and retrieved customers as response DTOs using the API facade.
3. The hardest issue was resolving the mismatch between the facade and service APIs, where the facade called a non-existent addCustomer() method instead of the service's existing createCustomer() method.