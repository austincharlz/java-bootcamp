#### Implementation Checkpoints
Checkpoint A
- Pass
- Pass
- Pass
Checkpoint B
- Pass
- Pass
- Pass
Checkpoint C
- Pass
- Pass
- Pass
- Pass
Checkpoint D
- Pass
- Pass
- Pass
#### Reflection Questions
1. Page Objects because centralizing selectors and waits in a page object reduced duplication and brittle selectors, making tests more stable and correct than scattering inline locators.  
2. Unit, integration (CustomerApiIT) and the Selenium UI test ran and passed; the build logs show WebDriverManager/chromedriver download and successful test runs, and HTTP behavior (POST 201 / GET 200 with X-Correlation-Id) observed. 
3. The Jackson deserialization error (test expecting Customer but receiving an error payload with numeric status) was the trickiest; it required inspecting server responses, exception handlers, and logs to discover the response/body-type mismatch.