#!/bin/bash

# Gatewayz Banking System - JAR Builder
# This script creates executable JARs for both console and JavaFX versions

echo "🏦 Gatewayz Banking System - JAR Builder"
echo "========================================="

# Create directories
mkdir -p build/classes
mkdir -p dist

# Clean previous builds
rm -rf build/classes/*
rm -f dist/*.jar

echo ""
echo "📦 Building Console Version..."
echo "------------------------------"

# Compile console version
javac -d build/classes \
    src/com/banking/model/*.java \
    src/com/banking/service/*.java \
    src/com/banking/BankingApp.java

if [ $? -eq 0 ]; then
    # Create manifest for console app
    echo "Main-Class: com.banking.BankingApp" > build/MANIFEST-CONSOLE.MF
    echo "" >> build/MANIFEST-CONSOLE.MF
    
    # Create console JAR
    jar cfm dist/GatewayzBanking-Console.jar build/MANIFEST-CONSOLE.MF -C build/classes .
    echo "✅ Created: dist/GatewayzBanking-Console.jar"
else
    echo "❌ Console version compilation failed!"
    exit 1
fi

echo ""
echo "📦 Building JavaFX Version..."
echo "-----------------------------"

# Check for JavaFX path
JAVAFX_PATH=""

# Try common JavaFX locations
if [ -d "/Users/srivardhanjakkani/Desktop/code+/java/javafx-sdk-21.0.9/lib" ]; then
    JAVAFX_PATH="/Users/srivardhanjakkani/Desktop/code+/java/javafx-sdk-21.0.9/lib"
elif [ -d "$HOME/javafx-sdk/lib" ]; then
    JAVAFX_PATH="$HOME/javafx-sdk/lib"
elif [ -n "$JAVAFX_HOME" ]; then
    JAVAFX_PATH="$JAVAFX_HOME/lib"
fi

if [ -z "$JAVAFX_PATH" ]; then
    echo "⚠️  JavaFX SDK not found. Skipping JavaFX build."
    echo "   Set JAVAFX_HOME or install JavaFX SDK to build JavaFX version."
else
    echo "   Using JavaFX from: $JAVAFX_PATH"
    
    # Clean and recompile with JavaFX
    rm -rf build/classes/*
    
    javac --module-path "$JAVAFX_PATH" --add-modules javafx.controls,javafx.fxml \
        -d build/classes \
        src/com/banking/model/*.java \
        src/com/banking/service/*.java \
        src/com/banking/BankingApp.java \
        src/com/banking/BankingAppFX.java
    
    if [ $? -eq 0 ]; then
        # Create manifest for JavaFX app
        echo "Main-Class: com.banking.BankingAppFX" > build/MANIFEST-FX.MF
        echo "" >> build/MANIFEST-FX.MF
        
        # Create JavaFX JAR
        jar cfm dist/GatewayzBanking-JavaFX.jar build/MANIFEST-FX.MF -C build/classes .
        echo "✅ Created: dist/GatewayzBanking-JavaFX.jar"
    else
        echo "❌ JavaFX version compilation failed!"
    fi
fi

echo ""
echo "========================================="
echo "📁 Output files in 'dist/' folder:"
ls -la dist/
echo ""
echo "🚀 To run:"
echo "   Console: java -jar dist/GatewayzBanking-Console.jar"
echo "   JavaFX:  See run-javafx.sh or run-javafx.bat"
echo ""
