public class EncapsulationDemo {
    public static void main(String[] args) {
        Account account = new Account(100.00);

        // account.balance = 999999; // Added via Exercise 02

        account.deposit(50.00);     // balance: 150
        account.withdraw(30.00);    // balance: 120
        account.withdraw(500.00);   // rejected; balance remains 120

        System.out.printf(
                "Final balance: %.2f%n", account.getBalance());
    }
}