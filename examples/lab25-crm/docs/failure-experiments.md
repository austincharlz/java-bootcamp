# Lab 25 Step 9 — Failure Experiments & Evidence

## Failure Experiments Summary

### Experiment 1: Idempotent GET (CUS-1001)

**Expected:** Repeat GET returns identical response; 200 OK both times.

**Observation:**

- First GET: `{"id":"CUS-1001","name":"Amina Khan","email":"amina.khan@example.com","status":"ACTIVE"}`
- Second GET: `{"id":"CUS-1001","name":"Amina Khan","email":"amina.khan@example.com","status":"ACTIVE"}`

**Result:** PASS — Seeded customer is idempotent; no side effects on repeated reads.

---

### Experiment 2: Duplicate Customer Rejection (POST same ID)

**Expected:** First POST creates successfully (201 CREATED); second POST with same ID fails (5xx) because service
rejects duplicate.

**Observation:**

- First POST CUS-1004: `{"id":"CUS-1004","name":"Test","email":"test@example.com","status":"PROSPECT"}` — **201
  CREATED**
- Second POST CUS-1004: Service throws `IllegalStateException("Duplicate customer")` → **500 error**

**Result:** PASS — Service enforces duplicate ID rule; layer ensures uniqueness even if the map could silently
overwrite.

---

### Experiment 3: Not-Found Customer (CUS-9999)

**Expected:** GET non-existent ID throws `IllegalArgumentException` and surfaces as error response.

**Observation:**

- GET CUS-9999: Service throws `IllegalArgumentException("Customer not found: CUS-9999")` → **500 error**

**Result:** PASS — Service rule is active; not-found is rejected at the service layer, not silently returning null.

---

## Test Evidence

### Test Run 1 (Surefire)

**Command:** `mvn -q test`

**Result:** All 4 tests pass:

- `CrmApplicationTests.contextLoadsAndRestSeedVisible` ✓
- `CustomerControllerTest.createCustomerOverHttpAddsItToServiceList` ✓
- `CustomerEndpointTest.getCustomerReturnsCus1001` ✓
- `CustomerServiceTest.getSeededCus1001` ✓
- `CustomerServiceTest.duplicateCreateRejected` ✓

Output logged to `notes/screenshots/lab-25/test-run-1.txt`

### Test Run 2 (Surefire — Identical Success)

**Command:** `mvn -q test`

**Result:** All 4 tests pass (identical to Run 1)

Output logged to `notes/screenshots/lab-25/test-run-2.txt`

**Checkpoint D1 PASS:** Two consecutive `mvn test` runs are identical and green.

---

## Git Status Check

After `mvn clean`:

```
AM docs/LAB-25-GUIDE.MD
AM docs/lab25-001.md
AM src/main/java/com/northstar/crm/repository/CustomerRepository.java
AM src/main/java/com/northstar/crm/repository/InMemoryCustomerRepository.java
?? notes/
?? README.md
?? pom.xml
?? src/...
```

✓ No `target/` directory committed (cleaned).  
✓ No secrets in committed files (only example@example.com emails).

**Checkpoint D3 PASS:** Repository is clean of build artifacts and secrets.

---

## Layering Diagram

```
┌─────────────────────────────────────────────────────────────┐
│ HTTP / SOAP Clients                                         │
└────────────────┬────────────────────────────────────────────┘
                 │
      ┌──────────▼──────────┐
      │  CustomerController │ (thin adapter; only calls service)
      │  getMapping("/{id}")│
      │  postMapping()      │
      └──────────┬──────────┘
                 │
      ┌──────────▼──────────────────┐
      │ CustomerService             │ (owns business rules)
      │ ├─ get(id)                  │
      │ ├─ create(customer, corrId) │ (checks duplicates)
      │ └─ list()                   │
      └──────────┬──────────────────┘
                 │ (depends on interface)
      ┌──────────▼──────────────────────┐
      │ CustomerRepository (interface)  │
      │ ├─ save(customer)               │
      │ ├─ findById(id)                 │
      │ ├─ findAll()                    │
      │ └─ existsById(id)               │
      └──────────┬──────────────────────┘
                 │ (pluggable impl)
      ┌──────────▼──────────────────────┐
      │ InMemoryCustomerRepository      │
      │ (seeds CUS-1001, CUS-1002)      │
      │ (ConcurrentHashMap store)       │
      └─────────────────────────────────┘
```

**Key Invariants:**

- Controller never imports `InMemoryCustomerRepository` or `CustomerRepository`.
- Service never imports HTTP types (`@RequestBody`, `ResponseEntity`, etc.).
- Repository interface has no persistence framework annotations (JPA-ready for Lab 26+).

---