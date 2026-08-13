# Lab 33 — Props Sketch

## Reference

| Prop | Example |
| --- | --- |
| customerId | CUS-1001 |
| name | Amina Khan |
| status | ACTIVE |
| onSelect | () => void |

## Step 2 — Types

Write TypeScript-ish types: `status: 'ACTIVE' | 'SUSPENDED' | ...`.

## Step 3 — Children?
CustomerCard should take only props if its content is fixed, and children if callers need to customize or inject content.

## Step 4 — Anti-pattern

Note: do not pass the entire global store as one mega-prop.

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.