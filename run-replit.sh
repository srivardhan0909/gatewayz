#!/bin/bash

# Gatewayz Banking System - Replit Console Version

echo "🏦 Compiling Gatewayz Banking System..."

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile all Java files
javac -d bin \
    src/com/banking/model/*.java \
    src/com/banking/service/*.java \
    src/com/banking/BankingApp.java

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo ""
    echo "🏦 Starting Gatewayz Banking System..."
    echo "========================================"
    echo ""
    # Run the console version (not JavaFX)
    java -cp bin com.banking.BankingApp
else
    echo "❌ Compilation failed!"
    exit 1
fi
