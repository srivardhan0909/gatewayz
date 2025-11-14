# Gatewayz Banking Application - Java Project

## 📋 Project Overview
A comprehensive banking application demonstrating core Java OOP concepts including **Encapsulation**, **Polymorphism**, **Interfaces**, and **File I/O**.

## 🎯 Concepts Covered

### 1. **Encapsulation**
- All fields in `BankAccount` class are private
- Access controlled through public getter/setter methods
- Data validation in methods (e.g., deposit/withdraw)

### 2. **Polymorphism**
- `SavingsAccount` and `CheckingAccount` extend `BankAccount`
- Method overriding: `withdraw()` in `CheckingAccount` allows overdraft
- Runtime polymorphism: All account types treated as `Account` interface

### 3. **Interface**
- `Account` interface defines contract for all account types
- Ensures consistent behavior across different account implementations

### 4. **File I/O**
- Persistent data storage using Java Serialization
- Accounts and transactions saved to `.dat` files
- Data automatically loaded on application restart

## 🚀 Features

1. **Create Account**: Create Regular, Savings, or Checking accounts
2. **Deposit Money**: Add funds to any account
3. **Withdraw Money**: Withdraw with balance validation
4. **Transfer Money**: Transfer between accounts
5. **Check Balance**: View current account balance
6. **Transaction History**: View all transactions for an account
7. **View All Accounts**: List all accounts in the system

## 📁 Project Structure

```
banking-app/
├── src/
│   └── com/
│       └── banking/
│           ├── BankingApp.java          # Main application
│           ├── model/
│           │   ├── Account.java         # Interface
│           │   ├── BankAccount.java     # Base class (Encapsulation)
│           │   ├── SavingsAccount.java  # Polymorphism
│           │   ├── CheckingAccount.java # Polymorphism
│           │   └── Transaction.java     # Transaction model
│           └── service/
│               └── BankService.java     # Business logic & File I/O
└── data/
    ├── accounts.dat                     # Persisted accounts
    └── transactions.dat                 # Persisted transactions
```

## 🔧 How to Compile and Run

### Option 1: Using provided script
```bash
cd banking-app
chmod +x compile-and-run.sh
./compile-and-run.sh
```

### Option 2: Manual compilation
```bash
cd banking-app
javac -d bin src/com/banking/*.java src/com/banking/model/*.java src/com/banking/service/*.java
java -cp bin com.banking.BankingApp
```

## 💡 Interview Highlights

### Encapsulation in Account Class
- **Private fields**: `accountNumber`, `accountHolderName`, `balance`, `accountType`
- **Controlled access**: Only through getters (read) and specific methods (modify)
- **Data validation**: Deposit/withdraw methods validate amounts before modifying balance
- **Benefits**: Data integrity, security, maintainability

### File Persistence as Lightweight Data Storage
- **Java Serialization**: Objects saved directly to disk
- **No database overhead**: Simple file-based storage
- **Automatic state management**: Data persists across application restarts
- **Trade-offs**: 
  - ✅ Simple to implement
  - ✅ No external dependencies
  - ❌ Not suitable for concurrent access
  - ❌ Limited query capabilities

### Polymorphism Benefits
- **Code reusability**: Common behavior in base `BankAccount` class
- **Extensibility**: Easy to add new account types
- **Flexibility**: Different accounts can have specialized behavior
- **Example**: `CheckingAccount` overrides `withdraw()` to allow overdraft

### Interface Usage
- **Contract definition**: `Account` interface ensures all accounts have required methods
- **Loose coupling**: Code depends on interface, not concrete implementations
- **Multiple implementations**: Different account types implement same interface

## 📝 Sample Usage

```
1. Create accounts (Regular/Savings/Checking)
2. Deposit money to accounts
3. Transfer between accounts
4. View transaction history
5. All data automatically saved to files
```

## 🎓 Learning Points

1. **Encapsulation protects data integrity**
2. **Polymorphism enables flexible design**
3. **Interfaces define contracts**
4. **File I/O provides simple persistence**
5. **Serialization for object storage**
6. **Exception handling for robustness**

## 🔒 Security Features

- Input validation on all operations
- Balance checks before withdrawal
- Account number uniqueness enforcement
- Overdraft limits on checking accounts

---

**Author**: Banking Application Demo  
**Purpose**: Educational demonstration of Java OOP concepts
