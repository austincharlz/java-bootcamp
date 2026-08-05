# Lab 22 — IoC Versus Manual Wiring

| Approach | Who creates collaborators?                    | Test impact                   |
| --- |-----------------------------------------------|-------------------------------|
| Manual `new` | Service constructs InMemoryCustomerRepository | Hard to swap fakes            |
| IoC / DI | Spring (or test) supplies collaborators       | Constructor takes a fake repo |

## Smell (one sentence)
CustomerService owns `new InMemoryCustomerRepository()`.

## Fix (one sentence)
Let the container (or test) supply it.

## Scope
Pre-lab only.