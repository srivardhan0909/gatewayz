package com.banking.service;

import com.banking.model.*;
import java.io.*;
import java.util.*;

/**
 * BankService - Main service class for banking operations
 * Demonstrates File I/O for data persistence
 */
public class BankService {
    private Map<String, Account> accounts;
    private List<Transaction> transactions;
    private int transactionCounter;
    
    private static final String ACCOUNTS_FILE = "data/accounts.dat";
    private static final String TRANSACTIONS_FILE = "data/transactions.dat";
    
    public BankService() {
        this.accounts = new HashMap<>();
        this.transactions = new ArrayList<>();
        this.transactionCounter = 1;
        loadData();
    }
    
    /**
     * Create a new bank account
     */
    public Account createAccount(String accountNumber, String accountHolderName, String accountType) {
        if (accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException("Account number already exists");
        }
        
        Account account;
        switch (accountType.toLowerCase()) {
            case "savings":
                account = new SavingsAccount(accountNumber, accountHolderName, 3.5);
                break;
            case "checking":
                account = new CheckingAccount(accountNumber, accountHolderName, 500.0);
                break;
            default:
                account = new BankAccount(accountNumber, accountHolderName, "Regular");
        }
        
        accounts.put(accountNumber, account);
        saveData();
        return account;
    }
    
    /**
     * Deposit money into an account
     */
    public boolean deposit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            return false;
        }
        
        try {
            account.deposit(amount);
            recordTransaction(accountNumber, "DEPOSIT", amount, account.getBalance(), 
                            "Deposit to account");
            saveData();
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Withdraw money from an account
     */
    public boolean withdraw(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            return false;
        }
        
        try {
            if (account.withdraw(amount)) {
                recordTransaction(accountNumber, "WITHDRAWAL", amount, account.getBalance(), 
                                "Withdrawal from account");
                saveData();
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Transfer money between accounts
     */
    public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = accounts.get(fromAccountNumber);
        Account toAccount = accounts.get(toAccountNumber);
        
        if (fromAccount == null || toAccount == null) {
            return false;
        }
        
        try {
            if (fromAccount.withdraw(amount)) {
                toAccount.deposit(amount);
                
                recordTransaction(fromAccountNumber, "TRANSFER_OUT", amount, fromAccount.getBalance(),
                                "Transfer to " + toAccountNumber);
                recordTransaction(toAccountNumber, "TRANSFER_IN", amount, toAccount.getBalance(),
                                "Transfer from " + fromAccountNumber);
                saveData();
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get account by account number
     */
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
    
    /**
     * Get transaction history for an account
     */
    public List<Transaction> getTransactionHistory(String accountNumber) {
        List<Transaction> accountTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAccountNumber().equals(accountNumber)) {
                accountTransactions.add(transaction);
            }
        }
        return accountTransactions;
    }
    
    /**
     * Get all accounts
     */
    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }
    
    /**
     * Record a transaction
     */
    private void recordTransaction(String accountNumber, String type, double amount, 
                                   double balanceAfter, String description) {
        String transactionId = String.format("TXN%05d", transactionCounter++);
        Transaction transaction = new Transaction(transactionId, accountNumber, type, 
                                                 amount, balanceAfter, description);
        transactions.add(transaction);
    }
    
    /**
     * Save data to files - Demonstrates File I/O
     */
    private void saveData() {
        try {
            // Save accounts
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(ACCOUNTS_FILE))) {
                oos.writeObject(accounts);
            }
            
            // Save transactions
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(TRANSACTIONS_FILE))) {
                oos.writeObject(transactions);
                oos.writeInt(transactionCounter);
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not save data - " + e.getMessage());
        }
    }
    
    /**
     * Load data from files - Demonstrates File I/O
     */
    @SuppressWarnings("unchecked")
    private void loadData() {
        try {
            // Load accounts
            File accountsFile = new File(ACCOUNTS_FILE);
            if (accountsFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(ACCOUNTS_FILE))) {
                    accounts = (Map<String, Account>) ois.readObject();
                }
            }
            
            // Load transactions
            File transactionsFile = new File(TRANSACTIONS_FILE);
            if (transactionsFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(TRANSACTIONS_FILE))) {
                    transactions = (List<Transaction>) ois.readObject();
                    transactionCounter = ois.readInt();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Note: Starting with fresh data - " + e.getMessage());
        }
    }
}
