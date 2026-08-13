# Lab 33 — Fill Component TODOs

## Step 1 — Paste

Create `notes/lab33-todos.md`:

```tsx
type CustomerCardProps = {
  customerId: string;
  name: string;
  status: 'ACTIVE' | 'SUSPENDED';
  onSelect: (id: string) => void;
};

export function CustomerCard({ customerId, name, status, onSelect }: CustomerCardProps) {
  return (
    <article aria-label={`${name} (${customerId})`}>
      <h3>{name}</h3>
      <StatusBadge status={status} />
      <button type="button" onClick={() => onSelect(customerId)}>View</button>
    </article>
  );
}
```