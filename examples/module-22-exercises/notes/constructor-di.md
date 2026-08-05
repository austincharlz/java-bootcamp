# Lab 22 — Constructor Injection Preference

## Preferred pattern
Constructor with final CustomerRepository + NotificationService

## Why (testability)
Required deps explicit; unit test = new CustomerService(fakeRepo, fakeNotifier).

## Avoid
Field @Autowired as primary pattern

## Setter role (one line)
Optional only - not Lab 22 primary wiring.

## Scope
Pre-lab only.