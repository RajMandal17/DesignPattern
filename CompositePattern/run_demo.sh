#!/bin/bash

echo "🎯 COMPOSITE PATTERN DEMO - Quick Run"
echo "====================================="
echo ""

cd /workspaces/DesignPattern/CompositePattern

# Compile if needed
javac -cp src src/com/designpattern/composite/*.java 2>/dev/null

# Run the demo
java -cp src com.designpattern.composite.Main

echo ""
echo "✅ Demo completed!"