#!/bin/bash

echo "🎯 FACADE PATTERN DEMO - Quick Run"
echo "=================================="
echo ""

cd /workspaces/DesignPattern/FacadePattern

# Compile if needed
javac -cp src src/com/designpattern/facade/*.java 2>/dev/null

# Run the demo
java -cp src com.designpattern.facade.Main

echo ""
echo "✅ Demo completed!"