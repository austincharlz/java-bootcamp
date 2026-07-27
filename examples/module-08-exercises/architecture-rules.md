#### Step 1
| Dependency | Decision      | Why                                                                  |
| ---------- |---------------|----------------------------------------------------------------------|
| controller → service | Acceptable    |                                                                      |
| service → repository | Acceptable    |                                                                      |
| repository → entity | Acceptable    |                                                                      |
| entity → controller | Problematic   | Domain depends on transport                                          |
| repository → controller | Problematic   | Persistence depends on presentation                                  |
| service → DTO | Needs context | Acceptable in this lab's simple mapping, but avoid transport leakage |
| DTO → repository | Problematic   | Bounadary model should not perform storage                           |
#### Step 4
- Now
  - Package names and stub responsibilities
  - Plain Java types that compile
  - Documented flow
- Later
  - Spring controller annotations
  - Validation annotations
  - Repository implementation/JPA
  - HTTP response mapping
  - Correlation-ID logging
#### Step 5
- I can locate each class package
  - Pass
- I can explain controller -> service -> repository
  - Pass
- I can distinguish DTO from entity
  - Pass
- I have not added Spring/JPA/database code
  - Pass
- I am ready to build the full Maven skeleton in Lab 8
  - Pass