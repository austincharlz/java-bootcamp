| File | Destination |
| ---- | ----------- |
| `Customer.java` | `src/main/java/...` |
| `CustomerServiceTest.java` | `src/test/java/...` |
| `application.properties` | `src/main/resources/` |
| test JSON | `src/test/resources/` |
| standards | `docs/` |
| `Customer.class` | generated under `target/classes/` |
#### Explain `target/`
- 'target/' is generated from source by Maven. It can be deleted and rebuilt, so it should be ignored rather than committed. 
#### Spot the mistakes
- production Java in `src/test/java`;
  - Production Java should be under main.
- passwords commited in `application.properties`;
  - Secrets should not be committed in config files.
- hand-editing `target/classes`;
  - These are the class files compiled from the Java files. They are not to be hand-edited.
- test fixtures in production resources without a runtime need.
  - Test fixtures need to be under test and they have their own test resources. 