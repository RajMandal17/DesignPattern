package com.designpattern.demo;

import com.designpattern.singleton.eager.DatabaseConnection;
import com.designpattern.singleton.lazy.Logger;
import com.designpattern.singleton.threadsafe.ConfigurationManager;
import com.designpattern.singleton.billpugh.CacheManager;
import com.designpattern.singleton.enumsingleton.ApplicationState;

/**
 * Demonstration class showing practical scenarios for each Singleton pattern
 */
public class SingletonScenarios {
    
    public static void demonstrateEagerSingleton() {
        System.out.println("🔹 EAGER INITIALIZATION SINGLETON - DatabaseConnection");
        System.out.println("Scenario: Database connection that's always needed in the application");
        System.out.println("Note: Instance is created even before we call getInstance()!\n");
        
        // The instance is already created when the class was loaded
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        
        System.out.println("Same instance? " + (db1 == db2));
        System.out.println(db1.getConnectionInfo());
        
        db1.executeQuery("SELECT * FROM users");
        db1.executeQuery("SELECT * FROM products");
        
        System.out.println("\n✅ Use Case: When you're certain the instance will be used");
        System.out.println("❌ Avoid when: Instance creation is expensive or might not be used");
    }
    
    public static void demonstrateLazySingleton() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🔹 LAZY INITIALIZATION SINGLETON - Logger");
        System.out.println("Scenario: Logging system that might not be used in some execution paths");
        System.out.println("Warning: NOT thread-safe - for demonstration only!\n");
        
        // Instance created only when first accessed
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        
        System.out.println("Same instance? " + (logger1 == logger2));
        System.out.println(logger1.getInstanceInfo());
        
        logger1.logInfo("Application started");
        logger1.logError("Sample error message");
        logger1.logInfo("Processing completed");
        
        System.out.println("\nTotal logs: " + logger1.getLogCount());
        
        // Demonstrate thread safety issue
        Logger.demonstrateThreadSafetyIssue();
        
        System.out.println("\n✅ Use Case: Single-threaded apps where instance might not be needed");
        System.out.println("❌ NEVER use in multi-threaded environments!");
    }
    
    public static void demonstrateThreadSafeSingleton() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🔹 THREAD-SAFE SINGLETON - ConfigurationManager");
        System.out.println("Scenario: Configuration management in multi-threaded application");
        System.out.println("Note: Uses synchronized methods for thread safety\n");
        
        ConfigurationManager config1 = ConfigurationManager.getInstance();
        ConfigurationManager config2 = ConfigurationManager.getInstance();
        
        System.out.println("Same instance? " + (config1 == config2));
        System.out.println(config1.getInstanceInfo());
        
        // Show configuration usage
        System.out.println("\nApp Name: " + config1.getConfiguration("app.name"));
        System.out.println("DB URL: " + config1.getConfiguration("database.url"));
        
        config1.setConfiguration("custom.setting", "value123");
        System.out.println("Custom Setting: " + config1.getConfiguration("custom.setting"));
        
        config1.updateEnvironment("DEVELOPMENT");
        
        // Performance test
        ConfigurationManager.demonstratePerformanceImpact();
        
        System.out.println("\n✅ Use Case: Multi-threaded apps where thread safety is critical");
        System.out.println("❌ Avoid when: High-performance requirements (use Bill Pugh instead)");
    }
    
    public static void demonstrateBillPughSingleton() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🔹 BILL PUGH SINGLETON - CacheManager");
        System.out.println("Scenario: High-performance caching system");
        System.out.println("Note: Recommended approach for most use cases!\n");
        
        CacheManager cache1 = CacheManager.getInstance();
        CacheManager cache2 = CacheManager.getInstance();
        
        System.out.println("Same instance? " + (cache1 == cache2));
        System.out.println(cache1.getInstanceInfo());
        
        // Demonstrate caching operations
        System.out.println("\n--- Cache Operations ---");
        cache1.put("user:123", "John Doe");
        cache1.put("session:abc", "active");
        cache1.put("temp:data", "temporary value");
        
        System.out.println("Retrieved user: " + cache1.get("user:123"));
        System.out.println("Retrieved session: " + cache1.get("session:abc"));
        System.out.println("Retrieved non-existent: " + cache1.get("nonexistent"));
        
        System.out.println("\n" + cache1.getCacheStats());
        
        // Performance demonstration
        CacheManager.demonstratePerformance();
        
        System.out.println("\n✅ Use Case: High-performance applications, most production scenarios");
        System.out.println("✅ Best balance of performance, thread safety, and lazy loading");
    }
    
    public static void demonstrateEnumSingleton() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🔹 ENUM SINGLETON - ApplicationState");
        System.out.println("Scenario: Application state management with maximum safety");
        System.out.println("Note: Safest against reflection and serialization attacks!\n");
        
        ApplicationState state1 = ApplicationState.INSTANCE;
        ApplicationState state2 = ApplicationState.INSTANCE;
        
        System.out.println("Same instance? " + (state1 == state2));
        System.out.println(state1.getStateInfo());
        
        // Demonstrate enum singleton benefits
        ApplicationState.demonstrateEnumBenefits();
        
        // Simulate application workflow
        state1.simulateWorkflow();
        
        System.out.println("\n✅ Use Case: When safety against reflection/serialization is crucial");
        System.out.println("✅ Application state, configuration that needs maximum protection");
        System.out.println("❌ Avoid when: Need inheritance or constructor parameters");
    }
    
    public static void compareAllPatterns() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("📊 SINGLETON PATTERNS COMPARISON SUMMARY");
        System.out.println("=".repeat(80));
        
        System.out.println("1. EAGER INITIALIZATION:");
        System.out.println("   ✅ Thread-safe, simple implementation");
        System.out.println("   ❌ Instance created even if not used");
        System.out.println("   🎯 Use for: Always-needed instances (DB connections, etc.)");
        
        System.out.println("\n2. LAZY INITIALIZATION:");
        System.out.println("   ✅ Instance created only when needed");
        System.out.println("   ❌ NOT thread-safe");
        System.out.println("   🎯 Use for: Single-threaded applications only");
        
        System.out.println("\n3. THREAD-SAFE (Synchronized):");
        System.out.println("   ✅ Thread-safe with lazy loading");
        System.out.println("   ❌ Performance overhead on every call");
        System.out.println("   🎯 Use for: Multi-threaded apps where performance isn't critical");
        
        System.out.println("\n4. BILL PUGH (RECOMMENDED):");
        System.out.println("   ✅ Best performance + thread safety + lazy loading");
        System.out.println("   ✅ No synchronization overhead");
        System.out.println("   🎯 Use for: Most production applications");
        
        System.out.println("\n5. ENUM SINGLETON:");
        System.out.println("   ✅ Safest against reflection and serialization");
        System.out.println("   ✅ Concise implementation");
        System.out.println("   ❌ Cannot extend classes, eager initialization");
        System.out.println("   🎯 Use for: Application state, maximum security needs");
        
        System.out.println("\n🏆 RECOMMENDATIONS:");
        System.out.println("   • Default choice: Bill Pugh Singleton");
        System.out.println("   • Maximum security: Enum Singleton");
        System.out.println("   • Always-used instances: Eager Initialization");
        System.out.println("   • Avoid: Lazy (non-thread-safe) in production");
    }
}