# Banking Domain Notes

| Entity | Identity | Important attributes | Main responsibility |
| ------ | -------- | -------------------- | ------------------- |
| Customer | customerId | name, email, phone | Maintain customer profile |
| Account | accountNumber | owner, balance, accountType | Protect balance and perform deposits/withdrawals |
| Transaction | transactionId | account, type, amount, timestamp | Record one account operation |

## Relationships
- One Customer can own zero or more Accounts.
- One Account belongs to exactly one Customer.
- One Account can have many Transactions.
- One Transaction belongs to exactly one Account.

## Rules
- An account balance cannot be changed directly from outside Account.
- A deposit amount must be positive.
- A withdrawal cannot exceed the allowed balance.

## Exercise 1 Notes
- Main should only be used by the user to navigate the program, while Account owns the balance itself and has a main responsibility of performing withdrawals. 

## SRP spot-check
- The original method could change because the formula changes or because the output format changes. These are separate responsibilities.

## Exercise 6 Notes
- Main should manage menu input, BankService should coordinate banking operations, and domain classes should protect their own state.

## Exercise 7 Notes
#### Principle Notes
- OCP: Keep old, tested, and verified code the same and only add new classes so it remains stable and modular. 
- LSP: Subclasses should work wherever their parent class is used so the program behaves consistently.
- ISP: Small, focused interfaces keep classes from implementing methods they don't need.
- DIP: Depending on abstractions instead of specific classes makes code more flexible, reusable, and easier to test.
#### Letters
- S.O.L.I.D.: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion.

