package com.banking.model;

/**
 * SavingsAccount - Demonstrates Polymorphism (Method Overriding)
 * Extends BankAccount with interest calculation feature
 */
public class SavingsAccount extends BankAccount {
    private static final long serialVersionUID = 1L;
    private double interestRate;
    
    public SavingsAccount(String accountNumber, String accountHolderName, double interestRate) {
        super(accountNumber, accountHolderName, "Savings");
        this.interestRate = interestRate;
    }
    
    public SavingsAccount(String accountNumber, String accountHolderName, double interestRate, double initialBalance) {
        super(accountNumber, accountHolderName, "Savings", initialBalance);
        this.interestRate = interestRate;
    }
    
    /**
     * Calculate and add interest to account
     */
    public void addInterest() {
        double interest = getBalance() * interestRate / 100;
        deposit(interest);
    }
    
    public double getInterestRate() {
        return interestRate;
    }
    
    @Override
    public String toString() {
        return String.format("Savings Account[%s] %s - Interest Rate: %.2f%% - Balance: $%.2f", 
            getAccountNumber(), getAccountHolderName(), interestRate, getBalance());
    }
}
