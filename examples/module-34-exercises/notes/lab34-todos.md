# Lab 34 — Fill useState TODOs

## Step 1 — Paste

Create `notes/lab34-todos.md`:

```tsx
const [name, setName] = useState("");
const [status, setStatus] = useState<"ACTIVE" | "INACTIVE">("ACTIVE");
const [error, setError] = useState<string | null>(null);

function onSubmit(e: FormEvent) {
  e.preventDefault();
  if (!name.trim()) { setError("Name is required"); return; }
  // Lab 35: POST to API
  console.log({ name, status, correlation: "lab-request-001" });
}

<input value={name} onChange={(e) => setName(e.target.value)} />
```