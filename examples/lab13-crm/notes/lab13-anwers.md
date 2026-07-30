#### Step 8
1. Pass
2. Pass
3. Pass
4. Pass
5. Pass
6. Pass
7. Pass
#### Failure Experiments
1. Breaking the schemaLocation temporarily produced a warning in the IDE. Restoring it got rid of the warning.
2. XSD permits; business logic rejects. Lab 24 must add service-layer validation.
3. Retrying CreateCustomer generates new CUS-ID each time (unsafe). Retrying GetCustomer returns same result (safe).
#### Implementation Checkpoints
Checkpoint A
1. Pass
2. Pass
3. Pass
Checkpoint B
1. Pass
2. Pass
3. Pass
Checkpoint C
1. Pass
2. Pass
3. Pass
Checkpoint D
1. Pass
2. Pass
3. Pass
4. Pass
#### Reflection Questions
1. The correlation ID convention (lab-request-001 style) embedded in request/response envelopes most impacts usability because it enables partners to trace requests end-to-end across async systems and logs. Without explicit guidance on this pattern, partners would either invent their own naming schemes (breaking interoperability) or lose request traceability entirely.
2. The sample envelope pairs (createCustomerRequest.xml → createCustomerResponse.xml, getCustomerRequest.xml → getCustomerResponse.xml) exactly match the WSDL message definitions and XSD schema, proving the contract structure is sound and directly implementable.
3. Namespace mismatches are harder to diagnose because SOAP processors fail silently or throw generic "unexpected element" errors without pointing to the namespace mismatch, whereas wrong element names trigger precise XSD validation errors that name the field.
