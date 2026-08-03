# Lab 18 — Mockito Anti-Patterns

| Anti-pattern | Better |
| --- | --- |
| Mock the SUT | Mock collaborators only |
| Unnecessary stubbing | Stub what is used |
| verifyNoMoreInteractions always | Use when interaction surface is critical |

Reject suggestions that mock CustomerService while testing CustomerService.
Fixtures: Ravi/Amina/CUS-9999 as appropriate.

## Scope
Pre-lab only.