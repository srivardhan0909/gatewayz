package com.banking;

import com.banking.model.*;
import com.banking.service.BankService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

/**
 * BankingAppFX - JavaFX GUI Application for Gatewayz Banking System
 * Modern user interface with all banking operations
 */
public class BankingAppFX extends Application {
    
    private BankService bankService;
    private BorderPane mainLayout;
    private VBox contentArea;
    private Label statusLabel;
    
    // Color scheme
    private static final String PRIMARY_COLOR = "#2c3e50";
    private static final String SECONDARY_COLOR = "#3498db";
    private static final String SUCCESS_COLOR = "#27ae60";
    private static final String ERROR_COLOR = "#e74c3c";
    private static final String BACKGROUND_COLOR = "#ecf0f1";
    
    @Override
    public void start(Stage primaryStage) {
        bankService = new BankService();
        
        // Create main layout
        mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");
        
        // Header
        VBox header = createHeader();
        mainLayout.setTop(header);
        
        // Navigation sidebar
        VBox sidebar = createSidebar();
        mainLayout.setLeft(sidebar);
        
        // Content area
        contentArea = new VBox(20);
        contentArea.setPadding(new Insets(30));
        contentArea.setAlignment(Pos.TOP_CENTER);
        
        ScrollPane scrollPane = new ScrollPane(contentArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: " + BACKGROUND_COLOR + ";");
        mainLayout.setCenter(scrollPane);
        
        // Status bar
        statusLabel = new Label("Welcome to Gatewayz Banking System");
        statusLabel.setStyle("-fx-background-color: " + PRIMARY_COLOR + "; -fx-text-fill: white; -fx-padding: 10;");
        statusLabel.setMaxWidth(Double.MAX_VALUE);
        mainLayout.setBottom(statusLabel);
        
        // Show dashboard by default
        showDashboard();
        
        // Create scene
        Scene scene = new Scene(mainLayout, 1100, 700);
        
        primaryStage.setTitle("Gatewayz Banking System - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }
    
    private VBox createHeader() {
        VBox header = new VBox();
        header.setStyle("-fx-background-color: " + PRIMARY_COLOR + ";");
        header.setPadding(new Insets(20));
        header.setAlignment(Pos.CENTER);
        
        Label title = new Label("🏦 Gatewayz Banking System");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.WHITE);
        
        Label subtitle = new Label("Your Trusted Financial Partner");
        subtitle.setFont(Font.font("Arial", 14));
        subtitle.setTextFill(Color.LIGHTGRAY);
        
        header.getChildren().addAll(title, subtitle);
        return header;
    }
    
    private VBox createSidebar() {
        VBox sidebar = new VBox(5);
        sidebar.setStyle("-fx-background-color: #34495e;");
        sidebar.setPadding(new Insets(20, 10, 20, 10));
        sidebar.setPrefWidth(220);
        
        Label menuTitle = new Label("MENU");
        menuTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        menuTitle.setTextFill(Color.LIGHTGRAY);
        menuTitle.setPadding(new Insets(0, 0, 10, 10));
        
        Button dashboardBtn = createMenuButton("📊 Dashboard", () -> showDashboard());
        Button createAccountBtn = createMenuButton("🆕 Create Account", () -> showCreateAccount());
        Button depositBtn = createMenuButton("💰 Deposit", () -> showDeposit());
        Button withdrawBtn = createMenuButton("💸 Withdraw", () -> showWithdraw());
        Button transferBtn = createMenuButton("🔄 Transfer", () -> showTransfer());
        Button balanceBtn = createMenuButton("📋 Check Balance", () -> showCheckBalance());
        Button historyBtn = createMenuButton("📜 Transaction History", () -> showTransactionHistory());
        Button accountsBtn = createMenuButton("👥 All Accounts", () -> showAllAccounts());
        
        sidebar.getChildren().addAll(
            menuTitle, dashboardBtn, createAccountBtn, depositBtn, 
            withdrawBtn, transferBtn, balanceBtn, historyBtn, accountsBtn
        );
        
        return sidebar;
    }
    
    private Button createMenuButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setPrefHeight(40);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-cursor: hand; " +
            "-fx-padding: 10 15;"
        );
        
        button.setOnMouseEntered(e -> 
            button.setStyle(
                "-fx-background-color: " + SECONDARY_COLOR + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 10 15; " +
                "-fx-background-radius: 5;"
            )
        );
        
        button.setOnMouseExited(e -> 
            button.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 10 15;"
            )
        );
        
        button.setOnAction(e -> action.run());
        return button;
    }
    
    private void showDashboard() {
        contentArea.getChildren().clear();
        
        Label title = createSectionTitle("Dashboard");
        
        // Stats cards
        HBox statsBox = new HBox(20);
        statsBox.setAlignment(Pos.CENTER);
        
        Collection<Account> accounts = bankService.getAllAccounts();
        int totalAccounts = accounts.size();
        double totalBalance = accounts.stream().mapToDouble(Account::getBalance).sum();
        
        VBox accountsCard = createStatCard("Total Accounts", String.valueOf(totalAccounts), SECONDARY_COLOR);
        VBox balanceCard = createStatCard("Total Balance", String.format("$%.2f", totalBalance), SUCCESS_COLOR);
        
        statsBox.getChildren().addAll(accountsCard, balanceCard);
        
        // Quick actions
        Label quickActionsLabel = new Label("Quick Actions");
        quickActionsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        quickActionsLabel.setPadding(new Insets(20, 0, 10, 0));
        
        HBox quickActions = new HBox(15);
        quickActions.setAlignment(Pos.CENTER);
        
        Button quickDeposit = createActionButton("💰 Quick Deposit", SUCCESS_COLOR, () -> showDeposit());
        Button quickWithdraw = createActionButton("💸 Quick Withdraw", "#e67e22", () -> showWithdraw());
        Button quickTransfer = createActionButton("🔄 Quick Transfer", SECONDARY_COLOR, () -> showTransfer());
        
        quickActions.getChildren().addAll(quickDeposit, quickWithdraw, quickTransfer);
        
        contentArea.getChildren().addAll(title, statsBox, quickActionsLabel, quickActions);
        updateStatus("Dashboard loaded");
    }
    
    private VBox createStatCard(String label, String value, String color) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(30));
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 10; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );
        card.setPrefWidth(200);
        
        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        valueLabel.setStyle("-fx-text-fill: " + color + ";");
        
        Label nameLabel = new Label(label);
        nameLabel.setFont(Font.font("Arial", 14));
        nameLabel.setStyle("-fx-text-fill: #7f8c8d;");
        
        card.getChildren().addAll(valueLabel, nameLabel);
        return card;
    }
    
    private Button createActionButton(String text, String color, Runnable action) {
        Button button = new Button(text);
        button.setStyle(
            "-fx-background-color: " + color + "; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-padding: 15 25; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand;"
        );
        button.setOnAction(e -> action.run());
        return button;
    }
    
    private void showCreateAccount() {
        contentArea.getChildren().clear();
        
        Label title = createSectionTitle("Create New Account");
        
        GridPane form = createFormGrid();
        
        TextField accountNumberField = createTextField("Enter account number");
        TextField nameField = createTextField("Enter account holder name");
        ComboBox<String> accountTypeCombo = new ComboBox<>();
        accountTypeCombo.getItems().addAll("Regular", "Savings (3.5% interest)", "Checking ($500 overdraft)");
        accountTypeCombo.setValue("Regular");
        accountTypeCombo.setPrefWidth(300);
        accountTypeCombo.setStyle("-fx-font-size: 14px;");
        
        form.add(new Label("Account Number:"), 0, 0);
        form.add(accountNumberField, 1, 0);
        form.add(new Label("Account Holder Name:"), 0, 1);
        form.add(nameField, 1, 1);
        form.add(new Label("Account Type:"), 0, 2);
        form.add(accountTypeCombo, 1, 2);
        
        Button submitBtn = createSubmitButton("Create Account");
        submitBtn.setOnAction(e -> {
            String accountNumber = accountNumberField.getText().trim();
            String name = nameField.getText().trim();
            String type = accountTypeCombo.getValue();
            
            if (accountNumber.isEmpty() || name.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields");
                return;
            }
            
            String accountType = "regular";
            if (type.contains("Savings")) accountType = "savings";
            else if (type.contains("Checking")) accountType = "checking";
            
            try {
                Account account = bankService.createAccount(accountNumber, name, accountType);
                showAlert(Alert.AlertType.INFORMATION, "Success", 
                    "Account created successfully!\n" + account.toString());
                accountNumberField.clear();
                nameField.clear();
                updateStatus("Account " + accountNumber + " created successfully");
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });
        
        VBox formBox = new VBox(20, form, submitBtn);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(20));
        formBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        formBox.setMaxWidth(500);
        
        contentArea.getChildren().addAll(title, formBox);
        updateStatus("Create Account form loaded");
    }
    
    private void showDeposit() {
        contentArea.getChildren().clear();
        
        Label title = createSectionTitle("Deposit Money");
        
        GridPane form = createFormGrid();
        
        TextField accountNumberField = createTextField("Enter account number");
        TextField amountField = createTextField("Enter amount");
        
        form.add(new Label("Account Number:"), 0, 0);
        form.add(accountNumberField, 1, 0);
        form.add(new Label("Amount ($):"), 0, 1);
        form.add(amountField, 1, 1);
        
        Button submitBtn = createSubmitButton("Deposit");
        submitBtn.setOnAction(e -> {
            String accountNumber = accountNumberField.getText().trim();
            String amountStr = amountField.getText().trim();
            
            if (accountNumber.isEmpty() || amountStr.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields");
                return;
            }
            
            try {
                double amount = Double.parseDouble(amountStr);
                if (bankService.deposit(accountNumber, amount)) {
                    Account account = bankService.getAccount(accountNumber);
                    showAlert(Alert.AlertType.INFORMATION, "Success", 
                        String.format("Deposited $%.2f successfully!\nNew Balance: $%.2f", 
                            amount, account.getBalance()));
                    amountField.clear();
                    updateStatus("Deposit of $" + amount + " completed");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Account not found");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid amount");
            }
        });
        
        VBox formBox = createFormBox(form, submitBtn);
        contentArea.getChildren().addAll(title, formBox);
        updateStatus("Deposit form loaded");
    }
    
    private void showWithdraw() {
        contentArea.getChildren().clear();
        
        Label title = createSectionTitle("Withdraw Money");
        
        GridPane form = createFormGrid();
        
        TextField accountNumberField = createTextField("Enter account number");
        TextField amountField = createTextField("Enter amount");
        
        form.add(new Label("Account Number:"), 0, 0);
        form.add(accountNumberField, 1, 0);
        form.add(new Label("Amount ($):"), 0, 1);
        form.add(amountField, 1, 1);
        
        Button submitBtn = createSubmitButton("Withdraw");
        submitBtn.setOnAction(e -> {
            String accountNumber = accountNumberField.getText().trim();
            String amountStr = amountField.getText().trim();
            
            if (accountNumber.isEmpty() || amountStr.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields");
                return;
            }
            
            try {
                double amount = Double.parseDouble(amountStr);
                if (bankService.withdraw(accountNumber, amount)) {
                    Account account = bankService.getAccount(accountNumber);
                    showAlert(Alert.AlertType.INFORMATION, "Success", 
                        String.format("Withdrawn $%.2f successfully!\nNew Balance: $%.2f", 
                            amount, account.getBalance()));
                    amountField.clear();
                    updateStatus("Withdrawal of $" + amount + " completed");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Withdrawal failed. Check account number and balance.");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid amount");
            }
        });
        
        VBox formBox = createFormBox(form, submitBtn);
        contentArea.getChildren().addAll(title, formBox);
        updateStatus("Withdraw form loaded");
    }
    
    private void showTransfer() {
        contentArea.getChildren().clear();
        
        Label title = createSectionTitle("Transfer Money");
        
        GridPane form = createFormGrid();
        
        TextField fromAccountField = createTextField("Enter source account");
        TextField toAccountField = createTextField("Enter destination account");
        TextField amountField = createTextField("Enter amount");
        
        form.add(new Label("From Account:"), 0, 0);
        form.add(fromAccountField, 1, 0);
        form.add(new Label("To Account:"), 0, 1);
        form.add(toAccountField, 1, 1);
        form.add(new Label("Amount ($):"), 0, 2);
        form.add(amountField, 1, 2);
        
        Button submitBtn = createSubmitButton("Transfer");
        submitBtn.setOnAction(e -> {
            String fromAccount = fromAccountField.getText().trim();
            String toAccount = toAccountField.getText().trim();
            String amountStr = amountField.getText().trim();
            
            if (fromAccount.isEmpty() || toAccount.isEmpty() || amountStr.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields");
                return;
            }
            
            try {
                double amount = Double.parseDouble(amountStr);
                if (bankService.transfer(fromAccount, toAccount, amount)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", 
                        String.format("Transferred $%.2f from %s to %s successfully!", 
                            amount, fromAccount, toAccount));
                    amountField.clear();
                    updateStatus("Transfer of $" + amount + " completed");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Transfer failed. Check account numbers and balance.");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid amount");
            }
        });
        
        VBox formBox = createFormBox(form, submitBtn);
        contentArea.getChildren().addAll(title, formBox);
        updateStatus("Transfer form loaded");
    }
    
    private void showCheckBalance() {
        contentArea.getChildren().clear();
        
        Label title = createSectionTitle("Check Balance");
        
        GridPane form = createFormGrid();
        
        TextField accountNumberField = createTextField("Enter account number");
        
        form.add(new Label("Account Number:"), 0, 0);
        form.add(accountNumberField, 1, 0);
        
        // Result display
        VBox resultBox = new VBox(10);
        resultBox.setAlignment(Pos.CENTER);
        resultBox.setPadding(new Insets(20));
        resultBox.setVisible(false);
        
        Label balanceLabel = new Label();
        balanceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        balanceLabel.setStyle("-fx-text-fill: " + SUCCESS_COLOR + ";");
        
        Label accountInfoLabel = new Label();
        accountInfoLabel.setFont(Font.font("Arial", 14));
        accountInfoLabel.setStyle("-fx-text-fill: #7f8c8d;");
        
        resultBox.getChildren().addAll(balanceLabel, accountInfoLabel);
        
        Button submitBtn = createSubmitButton("Check Balance");
        submitBtn.setOnAction(e -> {
            String accountNumber = accountNumberField.getText().trim();
            
            if (accountNumber.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter account number");
                return;
            }
            
            Account account = bankService.getAccount(accountNumber);
            if (account != null) {
                balanceLabel.setText(String.format("$%.2f", account.getBalance()));
                accountInfoLabel.setText(String.format("%s - %s (%s)", 
                    account.getAccountNumber(), account.getAccountHolderName(), account.getAccountType()));
                resultBox.setVisible(true);
                updateStatus("Balance checked for account " + accountNumber);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Account not found");
                resultBox.setVisible(false);
            }
        });
        
        VBox formBox = createFormBox(form, submitBtn);
        contentArea.getChildren().addAll(title, formBox, resultBox);
        updateStatus("Check Balance form loaded");
    }
    
    private void showTransactionHistory() {
        contentArea.getChildren().clear();
        
        Label title = createSectionTitle("Transaction History");
        
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        
        TextField accountNumberField = createTextField("Enter account number");
        accountNumberField.setPrefWidth(250);
        
        Button searchBtn = createSubmitButton("Search");
        
        searchBox.getChildren().addAll(accountNumberField, searchBtn);
        
        // Transaction table
        TableView<Transaction> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle("-fx-background-color: white;");
        table.setPrefHeight(400);
        
        TableColumn<Transaction, String> idCol = new TableColumn<>("Transaction ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        
        TableColumn<Transaction, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        
        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountCol.setCellFactory(col -> new TableCell<Transaction, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", item));
                }
            }
        });
        
        TableColumn<Transaction, Double> balanceCol = new TableColumn<>("Balance After");
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balanceAfter"));
        balanceCol.setCellFactory(col -> new TableCell<Transaction, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", item));
                }
            }
        });
        
        TableColumn<Transaction, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        table.getColumns().addAll(idCol, typeCol, amountCol, balanceCol, descCol);
        
        searchBtn.setOnAction(e -> {
            String accountNumber = accountNumberField.getText().trim();
            if (accountNumber.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter account number");
                return;
            }
            
            List<Transaction> transactions = bankService.getTransactionHistory(accountNumber);
            ObservableList<Transaction> data = FXCollections.observableArrayList(transactions);
            table.setItems(data);
            
            if (transactions.isEmpty()) {
                updateStatus("No transactions found for account " + accountNumber);
            } else {
                updateStatus("Found " + transactions.size() + " transactions for account " + accountNumber);
            }
        });
        
        VBox container = new VBox(20, searchBox, table);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        
        contentArea.getChildren().addAll(title, container);
        updateStatus("Transaction History view loaded");
    }
    
    private void showAllAccounts() {
        contentArea.getChildren().clear();
        
        Label title = createSectionTitle("All Accounts");
        
        // Accounts table
        TableView<Account> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle("-fx-background-color: white;");
        table.setPrefHeight(400);
        
        TableColumn<Account, String> numberCol = new TableColumn<>("Account Number");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        
        TableColumn<Account, String> nameCol = new TableColumn<>("Account Holder");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("accountHolderName"));
        
        TableColumn<Account, String> typeCol = new TableColumn<>("Account Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        
        TableColumn<Account, Double> balanceCol = new TableColumn<>("Balance");
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        balanceCol.setCellFactory(col -> new TableCell<Account, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", item));
                    if (item > 0) {
                        setStyle("-fx-text-fill: " + SUCCESS_COLOR + "; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: " + ERROR_COLOR + "; -fx-font-weight: bold;");
                    }
                }
            }
        });
        
        table.getColumns().addAll(numberCol, nameCol, typeCol, balanceCol);
        
        Collection<Account> accounts = bankService.getAllAccounts();
        ObservableList<Account> data = FXCollections.observableArrayList(accounts);
        table.setItems(data);
        
        Button refreshBtn = createSubmitButton("Refresh");
        refreshBtn.setOnAction(e -> {
            Collection<Account> refreshedAccounts = bankService.getAllAccounts();
            ObservableList<Account> refreshedData = FXCollections.observableArrayList(refreshedAccounts);
            table.setItems(refreshedData);
            updateStatus("Account list refreshed - " + refreshedAccounts.size() + " accounts");
        });
        
        VBox container = new VBox(20, table, refreshBtn);
        container.setPadding(new Insets(20));
        container.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        
        contentArea.getChildren().addAll(title, container);
        updateStatus("Viewing " + accounts.size() + " accounts");
    }
    
    // Helper methods
    private Label createSectionTitle(String text) {
        Label title = new Label(text);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: " + PRIMARY_COLOR + ";");
        title.setPadding(new Insets(0, 0, 20, 0));
        return title;
    }
    
    private GridPane createFormGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);
        return grid;
    }
    
    private TextField createTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setPrefWidth(300);
        field.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
        return field;
    }
    
    private Button createSubmitButton(String text) {
        Button button = new Button(text);
        button.setStyle(
            "-fx-background-color: " + SECONDARY_COLOR + "; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 12 30; " +
            "-fx-background-radius: 5; " +
            "-fx-cursor: hand;"
        );
        
        button.setOnMouseEntered(e -> 
            button.setStyle(
                "-fx-background-color: " + PRIMARY_COLOR + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 12 30; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;"
            )
        );
        
        button.setOnMouseExited(e -> 
            button.setStyle(
                "-fx-background-color: " + SECONDARY_COLOR + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 12 30; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;"
            )
        );
        
        return button;
    }
    
    private VBox createFormBox(GridPane form, Button submitBtn) {
        VBox formBox = new VBox(20, form, submitBtn);
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(30));
        formBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        formBox.setMaxWidth(500);
        return formBox;
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void updateStatus(String message) {
        statusLabel.setText("✓ " + message);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
