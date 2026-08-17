#### Step 9

- `java -jar` output

```bash
PS C:\Users\austi\java-bootcamp\examples\lab9-crm> java -jar target/customer-service.jar
Northstar CRM skeleton - Lab 8
Packages: controller, service, repository, entity, dto, config, exception
Examples: CUS-1001 Amina Khan ACTIVE | CUS-1002 Ravi Singh PROSPECT
```

- `mvn -B verify` output

```bash
PS C:\Users\austi\java-bootcamp\examples\lab9-crm> mvn -B verify
[INFO] Scanning for projects...
[INFO] 
[INFO] -------------------< com.northstar:customer-service >-------------------
[INFO] Building Northstar Customer Service 0.1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.4.0:resources (default-resources) @ customer-service ---
[INFO] Copying 2 resources from src\main\resources to target\classes
[INFO] 
[INFO] --- compiler:3.13.0:compile (default-compile) @ customer-service ---
[INFO] Nothing to compile - all classes are up to date.
[INFO] 
[INFO] --- resources:3.4.0:testResources (default-testResources) @ customer-service ---
[INFO] skip non existing resourceDirectory C:\Users\austi\java-bootcamp\examples\lab9-crm\src\test\resources
[INFO] 
[INFO] --- compiler:3.13.0:testCompile (default-testCompile) @ customer-service ---
[INFO] Nothing to compile - all classes are up to date.
[INFO] 
[INFO] --- surefire:3.5.2:test (default-test) @ customer-service ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.northstar.crm.PlaceholderTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.037 s -- in com.northstar.crm.PlaceholderTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- jar:3.4.2:jar (default-jar) @ customer-service ---
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.570 s
[INFO] Finished at: 2026-07-28T14:50:49-04:00
[INFO] ------------------------------------------------------------------------
```

#### Implementation Checkpoints

Checkpoint A

- Pass
- Pass
- Pass
- Fail | Using IntelliJ IDEA Checkpoint B
- Pass
- Pass
- Pass
- Pass Checkpoint C
- Pass
- Pass
- Pass
- Pass Checkpoint D
- Pass
- Pass
- Pass
- Pass

#### Manual Verification

1. `pwd` ends with `lab9-crm`.
    - Pass
2. `mvn validate` … `mvn install` each succeed individually (evidence file filled).
    - Pass
3. `mvn test` runs `PlaceholderTest` with 0 failures.
    - Pass
4. `mvn dependency:tree` shows `spring-context` (compile) and `junit-jupiter` (test).
    - Pass
5. `mvn help:active-profiles` shows `dev` by default; `-Pprod` activates `prod`.
    - Pass
6. `java -jar target/customer-service.jar` prints skeleton banner / example customer IDs.
    - Pass
7. `mvn -B verify` succeeds non-interactively.
    - Pass
8. Search POM/properties for passwords → none.
    - Pass
9. `git status` does not stage `target/` or secrets.
    - Pass
10. Concepts/reflection drafts mention artifact GAV vs `CUS-1001` distinction.
    - Pass

#### Failure Experiments

| # | Experiment                                                                 | Observe                                                  | Restore                     |
|---|----------------------------------------------------------------------------|----------------------------------------------------------|-----------------------------|
| 1 | Set `spring.version` to nonsense; `mvn compile`                            | Artifact resolution failure                              | Restore real version        |
| 2 | Change `PlaceholderTest` to `assertTrue(false)`; `mvn test` / `mvn verify` | Tests fail; verify fails                                 | Restore assertion           |
| 3 | Run `mvn install` twice                                                    | Second install succeeds; snapshot overwritten in `~/.m2` | Note idempotency in answers |

#### Security and Production Review

1. Which inputs are untrusted? *(Downloaded Maven artifacts; later API inputs)*
    - Downloaded Maven artifacts; later API inputs.
2. Where are authn/authz/validation enforced later? *(App layers + CI/repo managers)*
    - App layers (controllers/services) and CI/repo managers (build/release gates).
3. Which values are sensitive, and where stored? *(Never in POM; use secrets stores)*
    - PII, passwords, cloud keys. Never in POM or repo; use secret stores (Vault, CI secrets, cloud KMS).
4. What can be retried safely? *(`mvn verify`, snapshot install)*
    - `mvn verify`, snapshot installs, idempotent network fetches/tests.
5. What happens after a partial failure? *(Failed test stops verify; no bad promotion in CI)*
    - Failed tests stop verify; CI prevents promotion/publish of bad artifacts.
6. What would an operator monitor? *(CI duration, failed verify jobs)*
    - CI duration, failed/flake tests, verify job failures, promotion/publish events.
7. Which local default is unacceptable in production? *(`dev` profile active by default with real secrets—never do
   that)*
    - Dev profile active by default or shipping real secrets in local configs.
8. How are contracts versioned? *(Artifact version + later OpenAPI/WSDL)*
    - Artifact versioning (Maven) plus OpenAPI/WSDL/schema semantic versioning.

#### Reflection Questions

1. Which design decision most affected build correctness?
    - Choosing snapshot artifacts and multi-module lifecycle sequencing; incorrect dependency versions or wrong
      lifecycle phase ordering broke reproducible builds.
2. Which failure was hardest to diagnose?
    - Transitive dependency conflict from a downloaded artifact (version skew) causing runtime tests to fail; symptoms
      are opaque until dependency:tree analysis.
3. What evidence proves the lifecycle walk was real (not only `package` once)?
    - Logs showing execute of verify/integration-test phases, installed snapshot artifacts, and CI job steps that run
      mvn verify and publish only after verify success.
4. What breaks first at ten times the dependency count?
    - Dependency resolution time and conflicts; CI timeout failures, classpath collisions, and flaky tests due to
      environment contamination.
5. Which concern should move to shared infrastructure (artifact repository, CI cache)?
    - Dependency caching, artifact hosting (Nexus/Artifactory) and shared build tools to avoid repeated remote fetches
      and ensure consistent resolution.
6. What must change before real customer data is used?
    - Replace any dev/test data with sanitized samples, enable proper access controls, ensure secrets live in vaults,
      and add data-handling policies and audits.
7. How does this lab connect to Lab 8 structure and Lab 10+ code?
    - Lab 8 introduced modular build structure and test patterns; Lab 9 exercises lifecycle and artifact concerns which
      Lab 10+ will extend with larger integrations and service contracts.
8. What metric, log field, or CI signal matters most when verify fails?
    - Failing test count and first failing test name, build phase where it failed (verify/integration-test), and job
      duration/exit code in CI.
9. Why is `test` scope on JUnit more than a style preference?
    - It prevents test-only libs from leaking into production classpath, keeps artifact size and classpath sane, and
      enforces correct separation of test vs runtime dependencies.
10. (Forward look) When Spring Boot arrives, what stays stable in this POM vs what changes first?
    - Stable: groupId/artifactId/versioning, basic plugin lifecycle phases. Changes first: dependency set
      (spring-boot-starter BOM), plugin configuration (repackage, re-imposed dependency management), and active
      profiles.
