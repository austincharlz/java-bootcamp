# Lab 33 — JSX on Paper

## Step 1 — Tree
```jsx
<CustomerList>
    <CustomerCard key="CUS-1001">
        <h3>Amina Khan</h3>
        <StatusBadge status="ACTIVE" />
    </CustomerCard>
    <CustomerCard key="CUS-1002">
        <h3>Ravi Singh</h3>
        <StatusBadge status="PROSPECT" />
    </CustomerCard>
</CustomerList>
```

## Step 2 — Keys
React uses keys to determine which list item corresponds to which component between renders.

## Step 3 — Badge

Nest `<StatusBadge status="ACTIVE" />` inside Amina's card.

## Step 4 — No runtime

Do not create a Vite app in this exercise.

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.