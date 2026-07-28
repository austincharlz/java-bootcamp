#### Step 1 
| Question | Your answer |
| -------- | ----------- |
| What is the `groupId`? | `com.northstar` |
| What is the `artifactId`? | `customer-service` |
| What is the `version`? | `0.1.0-SNAPSHOT` |
| What is the packaging? | `jar` |
| Write the full GAV (`groupId:artifactId:version`) | `com.northstar:customer-service:0.1.0-SNAPSHOT` |
#### Step 3
- A -SNAPSHOT version means the artifact is still under active development and may change without a new release number.
#### Step 4
- `groupId` set to `com.example` while the Java packages are `com.northstar.crm`
  - It should match the organization's package naming convention.
- `artifactId` set to `CustomerService` (PascalCase)
  - Maven uses lowercase names with hyphens instead of PascalCase
- omitting `<packaging>` and assuming WAR for a plain Java library/app JAR
  - Maven defaults to JAR not WAR. WAR is used for web applications.
- committing a different `version` on every laptop with no team agreement
  - Project version should be the same for everyone on the team. Editing on different versions will create a mess for everyone with all the commits and pushes. 