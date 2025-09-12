#!/bin/bash

echo "🎯 SINGLETON PATTERN DEMO - Quick Run"
echo "====================================="
echo ""

cd /workspaces/DesignPattern

# Compile if needed
javac -cp src src/com/designpattern/singleton/*/*.java src/com/designpattern/demo/*.java src/com/designpattern/*.java 2>/dev/null

echo "🔹 Running Bill Pugh Singleton Demo..."
echo ""

# Create a simple test to show Bill Pugh singleton
java -cp src -c "
import com.designpattern.singleton.billpugh.CacheManager;

public class QuickDemo {
    public static void main(String[] args) {
        System.out.println(\"🚀 Testing Bill Pugh Singleton Pattern\");
        
        CacheManager cache1 = CacheManager.getInstance();
        CacheManager cache2 = CacheManager.getInstance();
        
        System.out.println(\"Same instance? \" + (cache1 == cache2));
        System.out.println(cache1.getInstanceInfo());
        
        cache1.put(\"test\", \"Hello Singleton!\");
        System.out.println(\"Retrieved: \" + cache1.get(\"test\"));
        
        System.out.println(\"\\n\" + cache1.getCacheStats());
    }
}
"