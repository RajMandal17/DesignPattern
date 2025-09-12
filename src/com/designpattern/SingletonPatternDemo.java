package com.designpattern;

import com.designpattern.demo.SingletonScenarios;
import java.util.Scanner;

/**
 * Main Application: Comprehensive Singleton Pattern Demonstration
 * 
 * This application demonstrates all types of Singleton patterns with:
 * - Practical use cases and scenarios
 * - Performance comparisons
 * - Thread safety demonstrations
 * - When to use each pattern and when to avoid them
 * 
 * @author Learning Java Design Patterns
 * @version 1.0
 */
public class SingletonPatternDemo {
    
    public static void main(String[] args) {
        printWelcomeMessage();
        
        Scanner scanner = new Scanner(System.in);
        boolean continueDemo = true;
        
        while (continueDemo) {
            printMenu();
            
            System.out.print("Enter your choice (1-7): ");
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    SingletonScenarios.demonstrateEagerSingleton();
                    break;
                case "2":
                    SingletonScenarios.demonstrateLazySingleton();
                    break;
                case "3":
                    SingletonScenarios.demonstrateThreadSafeSingleton();
                    break;
                case "4":
                    SingletonScenarios.demonstrateBillPughSingleton();
                    break;
                case "5":
                    SingletonScenarios.demonstrateEnumSingleton();
                    break;
                case "6":
                    runCompleteDemo();
                    break;
                case "7":
                    SingletonScenarios.compareAllPatterns();
                    break;
                case "0":
                    continueDemo = false;
                    System.out.println("\n👋 Thank you for exploring Singleton patterns!");
                    System.out.println("Remember: Choose the right pattern for your specific use case!");
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please enter a number between 0-7.");
            }
            
            if (continueDemo) {
                System.out.println("\n" + "-".repeat(80));
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                clearScreen();
            }
        }
        
        scanner.close();
    }
    
    private static void printWelcomeMessage() {
        clearScreen();
        System.out.println("🎯 SINGLETON DESIGN PATTERN - COMPREHENSIVE DEMO");
        System.out.println("=".repeat(60));
        System.out.println("📚 Learn all types of Singleton patterns with practical examples!");
        System.out.println("🔍 Understand when to use each pattern and why");
        System.out.println("⚡ See performance comparisons and thread safety demos");
        System.out.println("=".repeat(60));
    }
    
    private static void printMenu() {
        System.out.println("\n🎯 SINGLETON PATTERN MENU:");
        System.out.println("┌────────────────────────────────────────────────────────┐");
        System.out.println("│ 1. Eager Initialization Singleton (DatabaseConnection) │");
        System.out.println("│ 2. Lazy Initialization Singleton (Logger)              │");
        System.out.println("│ 3. Thread-Safe Singleton (ConfigurationManager)        │");
        System.out.println("│ 4. Bill Pugh Singleton (CacheManager) - RECOMMENDED    │");
        System.out.println("│ 5. Enum Singleton (ApplicationState) - SAFEST          │");
        System.out.println("│ 6. Run Complete Demo (All Patterns)                    │");
        System.out.println("│ 7. Compare All Patterns (Summary)                      │");
        System.out.println("│ 0. Exit                                                │");
        System.out.println("└────────────────────────────────────────────────────────┘");
    }
    
    private static void runCompleteDemo() {
        System.out.println("\n🚀 RUNNING COMPLETE SINGLETON DEMONSTRATION");
        System.out.println("This will demonstrate all singleton patterns in sequence...\n");
        
        try {
            // Run all demonstrations
            SingletonScenarios.demonstrateEagerSingleton();
            Thread.sleep(2000);
            
            SingletonScenarios.demonstrateLazySingleton();
            Thread.sleep(2000);
            
            SingletonScenarios.demonstrateThreadSafeSingleton();
            Thread.sleep(2000);
            
            SingletonScenarios.demonstrateBillPughSingleton();
            Thread.sleep(2000);
            
            SingletonScenarios.demonstrateEnumSingleton();
            Thread.sleep(2000);
            
            SingletonScenarios.compareAllPatterns();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Demo interrupted");
        }
        
        System.out.println("\n🎉 COMPLETE DEMO FINISHED!");
        System.out.println("You've seen all Singleton pattern implementations!");
    }
    
    private static void clearScreen() {
        // Clear screen for better presentation
        System.out.print("\033[2J\033[H");
        System.out.flush();
    }
    
    /**
     * Additional utility method to demonstrate singleton violations
     */
    public static void demonstrateSingletonViolations() {
        System.out.println("\n⚠️  DEMONSTRATING SINGLETON VIOLATIONS:");
        System.out.println("These are anti-patterns - what NOT to do!");
        
        // This would be a violation - but our classes prevent it
        try {
            // Trying to create multiple instances through reflection
            // (This will fail for enum singleton)
            System.out.println("Attempting reflection-based instantiation...");
            
            Class<?> dbClass = Class.forName("com.designpattern.singleton.eager.DatabaseConnection");
            Object instance1 = dbClass.getDeclaredConstructor().newInstance();
            Object instance2 = dbClass.getDeclaredConstructor().newInstance();
            
            System.out.println("❌ VIOLATION: Created multiple instances through reflection!");
            System.out.println("Instance 1: " + instance1.hashCode());
            System.out.println("Instance 2: " + instance2.hashCode());
            
        } catch (Exception e) {
            System.out.println("✅ PROTECTION: " + e.getClass().getSimpleName() + 
                             " - Singleton protected against reflection");
        }
    }
    
    /**
     * Performance comparison utility
     */
    public static void performanceComparison() {
        System.out.println("\n⚡ PERFORMANCE COMPARISON:");
        System.out.println("Comparing getInstance() performance across patterns...");
        
        final int ITERATIONS = 1000000;
        
        // Warm up JVM
        for (int i = 0; i < 10000; i++) {
            com.designpattern.singleton.eager.DatabaseConnection.getInstance();
            com.designpattern.singleton.billpugh.CacheManager.getInstance();
            com.designpattern.singleton.enumsingleton.ApplicationState.INSTANCE.getCurrentUser();
        }
        
        // Test Eager Singleton
        long startTime = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            com.designpattern.singleton.eager.DatabaseConnection.getInstance();
        }
        long eagerTime = System.nanoTime() - startTime;
        
        // Test Bill Pugh Singleton
        startTime = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            com.designpattern.singleton.billpugh.CacheManager.getInstance();
        }
        long billPughTime = System.nanoTime() - startTime;
        
        // Test Enum Singleton
        startTime = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            com.designpattern.singleton.enumsingleton.ApplicationState.INSTANCE.getCurrentUser();
        }
        long enumTime = System.nanoTime() - startTime;
        
        System.out.println("📊 Performance Results (" + ITERATIONS + " iterations):");
        System.out.println("   Eager Singleton:    " + (eagerTime / 1_000_000.0) + " ms");
        System.out.println("   Bill Pugh Singleton: " + (billPughTime / 1_000_000.0) + " ms");
        System.out.println("   Enum Singleton:     " + (enumTime / 1_000_000.0) + " ms");
        
        System.out.println("\n💡 Note: All are very fast! Choose based on features, not just performance.");
    }
}