#!/bin/bash

echo "🎯 DECORATOR PATTERN DEMO - Quick Run"
echo "====================================="
echo ""

cd /workspaces/DesignPattern/DecoratorPattern

# Compile if needed
javac -cp src src/com/designpattern/decorator/*.java 2>/dev/null

# Run the demo
java -cp src com.designpattern.decorator.Main

echo ""
echo "✅ Demo completed!"