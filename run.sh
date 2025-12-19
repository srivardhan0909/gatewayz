#!/bin/bash

# Gatewayz Banking System - JavaFX Launcher
JAVAFX_PATH="/Users/srivardhanjakkani/Desktop/code+/java/javafx-sdk-21.0.9/lib"

echo "🏦 Starting Gatewayz Banking System..."

# Compile
javac --module-path "$JAVAFX_PATH" --add-modules javafx.controls,javafx.fxml -d bin \
    src/com/banking/*.java \
    src/com/banking/model/*.java \
    src/com/banking/service/*.java 2>/dev/null

# Run
java --module-path "$JAVAFX_PATH" --add-modules javafx.controls,javafx.fxml -cp bin com.banking.BankingAppFX
