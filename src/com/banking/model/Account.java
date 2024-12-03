package com.banking.model;

/**
 * Account Interface - Demonstrates Interface concept
 * Defines the contract that all account types must follow
 */
public interface Account {
    void deposit(double amount);
    boolean withdraw(double amount);
    double getBalance();
    String getAccountNumber();
    String getAccountHolderName();
    String getAccountType();
}
