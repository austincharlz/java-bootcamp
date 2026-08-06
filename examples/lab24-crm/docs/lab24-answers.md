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
Checkpoint D
- Pass
- Pass
- Pass
#### Reflection Questions
1. Making the XSD the source of truth mattered most because it forced the SOAP payload names, namespace, and WSDL shape to stay aligned. Once `customer.xsd` was correct, the endpoint and mapper had a stable contract to implement against.
2. Both SOAP `GetCustomerRequest` for `CUS-1001` and REST `GET /api/customers/CUS-1001` return the same seeded Amina Khan record. The SOAP endpoint delegates to the same injected `CustomerService` that the REST controller uses.
3. Payload-root and XML-shape issues are the harder timed-path failures because UsernameToken is not wired in this lab scope. A namespace or localPart mismatch can look like the endpoint is being ignored until you inspect the contract and request body closely.
