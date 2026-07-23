package com.academy.bank;

public class SavingsAccount extends Account implements Printable {
    private double interestRate;

    public SavingsAccount(String accountNumber, double balance, Customer customer, double interestRate) {
        super(accountNumber, balance, customer);
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }
        this.interestRate = interestRate;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }
    @Override
    public double calculateInterest() {
        return getBalance() * interestRate / 100.0;
    }

    @Override
    public void displayAccount() {
        System.out.printf(
                "Account Type: %s%n" +
                        "Account Number: %s%n" +
                        "Customer Name: %s%n" +
                        "Balance: %.2f%n" +
                        "Interest Rate: %.2f%%%n" +
                        "Interest: %.2f%n",
                getAccountType(),
                getAccountNumber(),
                getCustomer().getName(),
                getBalance(),
                interestRate,
                calculateInterest()
        );
    }

    @Override
    public void printDetails() {
        displayAccount();
    }
}