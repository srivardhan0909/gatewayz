package com.banking.model;

/**
 * CheckingAccount - Demonstrates Polymorphism (Method Overriding)
 * Extends BankAccount with overdraft protection
 */
public class CheckingAccount extends BankAccount {
    private static final long serialVersionUID = 1L;
    private double overdraftLimit;
    
    public CheckingAccount(String accountNumber, String accountHolderName, double overdraftLimit) {
        super(accountNumber, accountHolderName, "Checking");
        this.overdraftLimit = overdraftLimit;
    }
    
    public CheckingAccount(String accountNumber, String accountHolderName, double overdraftLimit, double initialBalance) {
        super(accountNumber, accountHolderName, "Checking", initialBalance);
        this.overdraftLimit = overdraftLimit;
    }
    
    /**
     * Override withdraw to allow overdraft up to the limit
     */
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        // Allow withdrawal if within overdraft limit
        if (amount > (getBalance() + overdraftLimit)) {
            return false; // Exceeds overdraft limit
        }
        super.deposit(-amount); // Use deposit with negative amount to bypass balance check
        return true;
    }
    
    public double getOverdraftLimit() {
        return overdraftLimit;
    }
    
    @Override
    public String toString() {
        return String.format("Checking Account[%s] %s - Overdraft Limit: $%.2f - Balance: $%.2f", 
            getAccountNumber(), getAccountHolderName(), overdraftLimit, getBalance());
    }
}
