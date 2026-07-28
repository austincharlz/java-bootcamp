#### Step 1
| Intent | Command                             |
| ------ |-------------------------------------|
| Confirm POM parses before coding further | `mvn validate`                      |
| Compile production Java only (stops before tests if you use the phase carefully) | `mvn compile`                       |
| Run unit tests | `mvn test`                          |
| Produce `target/customer-service.jar` | `mvn package`                       |
| Run package plus verification checks CI cares about | `mvn verify` (often `mvn -B verify) |
| Put the JAR into your local Maven cache | `mvn install`                        |
#### Step 3
1. Validate
2. Compile
3. Test
4. Package
5. Verify
6. Install
#### Step 4
- Continuous Integration usually runs `mvn -B verify` so the build is batch/non-interactive and stops after verification without casually installing or deploying from every laptop.
