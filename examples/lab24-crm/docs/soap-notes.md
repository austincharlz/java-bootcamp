# SOAP notes

- **WSDL:** `http://localhost:8080/ws/customers.wsdl`
- **Timed-path sample:** `requests/get-customer.xml`
- **Security:** UsernameToken is not wired on the timed path, so the unsecured sample request succeeds by design.
- **Shared service proof:** SOAP `GetCustomerRequest` for `CUS-1001` and REST `GET /api/customers/CUS-1001` both return
  the seeded `Amina Khan` record from the same `CustomerService`.
