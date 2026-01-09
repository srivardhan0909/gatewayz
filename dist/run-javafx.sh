#!/bin/bash

# Gatewayz Banking System - JavaFX Launcher (macOS/Linux)
# 
# REQUIREMENTS:
# 1. Java 11+ installed
# 2. JavaFX SDK downloaded from: https://openjfx.io/
#
# SETUP:
# Set JAVAFX_HOME to your JavaFX SDK path, or edit the path below

# Try to find JavaFX
if [ -n "$JAVAFX_HOME" ]; then
    JAVAFX_PATH="$JAVAFX_HOME/lib"
elif [ -d "$HOME/javafx-sdk-21/lib" ]; then
    JAVAFX_PATH="$HOME/javafx-sdk-21/lib"
elif [ -d "$HOME/javafx-sdk-21.0.9/lib" ]; then
    JAVAFX_PATH="$HOME/javafx-sdk-21.0.9/lib"
elif [ -d "/opt/javafx-sdk/lib" ]; then
    JAVAFX_PATH="/opt/javafx-sdk/lib"
else
    echo "❌ JavaFX SDK not found!"
    echo ""
    echo "Please download JavaFX SDK from: https://openjfx.io/"
    echo "Then either:"
    echo "  1. Set JAVAFX_HOME environment variable"
    echo "  2. Extract to ~/javafx-sdk-21/"
    echo ""
    exit 1
fi

echo "🏦 Starting Gatewayz Banking System (JavaFX)..."
echo "   Using JavaFX from: $JAVAFX_PATH"
echo ""

# Get the directory where this script is located
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

java --module-path "$JAVAFX_PATH" \
     --add-modules javafx.controls,javafx.fxml \
     -jar "$SCRIPT_DIR/GatewayzBanking-JavaFX.jar"
