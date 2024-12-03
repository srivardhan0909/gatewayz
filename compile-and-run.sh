#!/bin/bash

# Simple Banking Application - Compile and Run Script

echo "╔════════════════════════════════════════╗"
echo "║   Banking Application Build Script     ║"
echo "╚════════════════════════════════════════╝"
echo ""

# Create bin directory if it doesn't exist
if [ ! -d "bin" ]; then
    echo "📁 Creating bin directory..."
    mkdir bin
fi

# Compile all Java files
echo "🔨 Compiling Java files..."
javac -d bin src/com/banking/*.java src/com/banking/model/*.java src/com/banking/service/*.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo ""
    echo "🚀 Starting Banking Application..."
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo ""
    
    # Run the application
    java -cp bin com.banking.BankingApp
else
    echo "❌ Compilation failed. Please check for errors."
    exit 1
fi
