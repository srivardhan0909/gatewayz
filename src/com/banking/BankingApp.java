package com.banking;

import com.banking.model.*;
import com.banking.service.BankService;
import java.util.*;

/**
 * BankingApp - Main application class
 * Console-based banking application
 */
public class BankingApp {
    private static BankService bankService;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        bankService = new BankService();
        scanner = new Scanner(System.in);
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   Welcome to Gatewayz Banking System   ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    checkBalance();
                    break;
                case 6:
                    viewTransactionHistory();
                    break;
                case 7:
                    viewAllAccounts();
                    break;
                case 8:
                    running = false;
                    System.out.println("\nThank you for using Gatewayz Banking System!");
                    break;
                default:
                    System.out.println("\n❌ Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           MAIN MENU");
        System.out.println("=".repeat(40));
        System.out.println("1. Create New Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transfer Money");
        System.out.println("5. Check Balance");
        System.out.println("6. View Transaction History");
        System.out.println("7. View All Accounts");
        System.out.println("8. Exit");
        System.out.println("=".repeat(40));
    }
    
    private static void createAccount() {
        System.out.println("\n--- Create New Account ---");
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine().trim();
        
        System.out.print("Enter Account Holder Name: ");
        String name = scanner.nextLine().trim();
        
        System.out.println("\nAccount Types:");
        System.out.println("1. Regular Account");
        System.out.println("2. Savings Account (3.5% interest)");
        System.out.println("3. Checking Account ($500 overdraft)");
        
        int typeChoice = getIntInput("Select account type: ");
        String accountType;
        
        switch (typeChoice) {
            case 2:
                accountType = "savings";
                break;
            case 3:
                accountType = "checking";
                break;
            default:
                accountType = "regular";
        }
        
        try {
            Account account = bankService.createAccount(accountNumber, name, accountType);
            System.out.println("\n✅ Account created successfully!");
            System.out.println(account);
        } catch (IllegalArgumentException e) {
            System.out.println("\n❌ Error: " + e.getMessage());
        }
    }
    
    private static void deposit() {
        System.out.println("\n--- Deposit Money ---");
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine().trim();
        
        double amount = getDoubleInput("Enter deposit amount: $");
        
        if (bankService.deposit(accountNumber, amount)) {
            System.out.println("\n✅ Deposit successful!");
        } else {
            System.out.println("\n❌ Deposit failed. Please check account number and amount.");
        }
    }
    
    private static void withdraw() {
        System.out.println("\n--- Withdraw Money ---");
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine().trim();
        
        double amount = getDoubleInput("Enter withdrawal amount: $");
        
        if (bankService.withdraw(accountNumber, amount)) {
            System.out.println("\n✅ Withdrawal successful!");
        } else {
            System.out.println("\n❌ Withdrawal failed. Insufficient balance or invalid account.");
        }
    }
    
    private static void transfer() {
        System.out.println("\n--- Transfer Money ---");
        System.out.print("Enter From Account Number: ");
        String fromAccount = scanner.nextLine().trim();
        
        System.out.print("Enter To Account Number: ");
        String toAccount = scanner.nextLine().trim();
        
        double amount = getDoubleInput("Enter transfer amount: $");
        
        if (bankService.transfer(fromAccount, toAccount, amount)) {
            System.out.println("\n✅ Transfer successful!");
        } else {
            System.out.println("\n❌ Transfer failed. Check account numbers and balance.");
        }
    }
    
    private static void checkBalance() {
        System.out.println("\n--- Check Balance ---");
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine().trim();
        
        Account account = bankService.getAccount(accountNumber);
        if (account != null) {
            System.out.println("\n" + account);
        } else {
            System.out.println("\n❌ Account not found.");
        }
    }
    
    private static void viewTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine().trim();
        
        Account account = bankService.getAccount(accountNumber);
        if (account == null) {
            System.out.println("\n❌ Account not found.");
            return;
        }
        
        List<Transaction> transactions = bankService.getTransactionHistory(accountNumber);
        
        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions found for this account.");
        } else {
            System.out.println("\nAccount: " + account.getAccountHolderName() + " (" + accountNumber + ")");
            System.out.println("-".repeat(80));
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
            System.out.println("-".repeat(80));
            System.out.println("Total Transactions: " + transactions.size());
        }
    }
    
    private static void viewAllAccounts() {
        System.out.println("\n--- All Accounts ---");
        Collection<Account> accounts = bankService.getAllAccounts();
        
        if (accounts.isEmpty()) {
            System.out.println("\nNo accounts in the system.");
        } else {
            System.out.println("-".repeat(80));
            for (Account account : accounts) {
                System.out.println(account);
            }
            System.out.println("-".repeat(80));
            System.out.println("Total Accounts: " + accounts.size());
        }
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid amount.");
            }
        }
    }
}
