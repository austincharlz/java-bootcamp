# Lab 35 — Fill Fetch TODOs

## Step 1 — Paste

Create `notes/lab35-todos.md`:

```bash
export type Customer = { customerId: string; name: string; status: string };

export async function listCustomers(signal?: AbortSignal): Promise<Customer[]> {
  const res = await fetch([_____](http://localhost:8080/api/customers), {
    headers: { "X-Correlation-Id": "lab-request-001" },
    signal,
  });
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return (await res.json()) as Customer[];
}

export async function getCustomer(id: string): Promise<Customer> {
  const res = await fetch(`${[_____](http://localhost:8080/api/customers)}/${id}`);
  // TODO: handle 404 for unknown id
  return (await res.json()) as Customer;
}
```