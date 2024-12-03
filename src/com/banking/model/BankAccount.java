package com.banking.model;

import java.io.Serializable;

/**
 * BankAccount class - Demonstrates Encapsulation
 * All fields are private and accessed via getters/setters
 * Implements Serializable for file persistence
 */
public class BankAccount implements Account, Serializable {
    private static final long serialVersionUID = 1L;
    
    // Private fields - Encapsulation
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String accountType;
    
    // Constructor
    public BankAccount(String accountNumber, String accountHolderName, String accountType) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = 0.0;
        this.accountType = accountType;
    }
    
    // Overloaded constructor with initial balance
    public BankAccount(String accountNumber, String accountHolderName, String accountType, double initialBalance) {
        this(accountNumber, accountHolderName, accountType);
        this.balance = initialBalance;
    }
    
    /**
     * Deposit money into account
     * Validates amount is positive
     */
    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }
    
    /**
     * Withdraw money from account
     * Validates sufficient balance
     */
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            return false; // Insufficient balance
        }
        this.balance -= amount;
        return true;
    }
    
    // Getters - Encapsulation principle (controlled access)
    @Override
    public double getBalance() {
        return balance;
    }
    
    @Override
    public String getAccountNumber() {
        return accountNumber;
    }
    
    @Override
    public String getAccountHolderName() {
        return accountHolderName;
    }
    
    @Override
    public String getAccountType() {
        return accountType;
    }
    
    // Setter for account holder name (controlled modification)
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
    
    @Override
    public String toString() {
        return String.format("Account[%s] %s - %s: $%.2f", 
            accountNumber, accountHolderName, accountType, balance);
    }
}
