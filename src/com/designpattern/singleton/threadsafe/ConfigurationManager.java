package com.designpattern.singleton.threadsafe;

import java.util.HashMap;
import java.util.Map;

/**
 * THREAD-SAFE SINGLETON PATTERN (Using Synchronized Methods)
 * 
 * WHEN TO USE:
 * - Multi-threaded applications where thread safety is crucial
 * - When you need lazy initialization with thread safety
 * - When performance of getInstance() is not critical
 * 
 * WHEN NOT TO USE:
 * - High-performance applications where getInstance() is called frequently
 * - When you need the best performance (use Bill Pugh instead)
 * - Single-threaded applications (unnecessary overhead)
 * 
 * PROS:
 * + Thread-safe with lazy initialization
 * + Guarantees only one instance in multi-threaded environment
 * + Simple to understand and implement
 * 
 * CONS:
 * - Performance overhead due to synchronization on every getInstance() call
 * - Can become a bottleneck in high-concurrency scenarios
 * - Synchronized methods are slower than other approaches
 */
public class ConfigurationManager {
    
    private static ConfigurationManager instance;
    
    private Map<String, String> configurations;
    private long creationTime;
    private String environment;
    private volatile boolean isInitialized = false;
    
    // Private constructor
    private ConfigurationManager() {
        // Simulate loading configuration from file/database
        System.out.println("🔧 ConfigurationManager: Initializing configurations...");
        
        this.configurations = new HashMap<>();
        this.creationTime = System.currentTimeMillis();
        this.environment = "PRODUCTION";
        
        // Load default configurations
        loadDefaultConfigurations();
        
        // Simulate initialization delay
        try {
            Thread.sleep(200); // Simulate time-consuming initialization
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        this.isInitialized = true;
        System.out.println("✅ ConfigurationManager initialized successfully");
        System.out.println("   Environment: " + environment);
        System.out.println("   Configurations loaded: " + configurations.size());
    }
    
    // Thread-safe getInstance method using synchronized
    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }
    
    // Alternative: Double-checked locking (more efficient)
    public static ConfigurationManager getInstanceDoubleChecked() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }
    
    private void loadDefaultConfigurations() {
        configurations.put("app.name", "SingletonDemo");
        configurations.put("app.version", "1.0.0");
        configurations.put("database.url", "jdbc:mysql://localhost:3306/app");
        configurations.put("database.username", "admin");
        configurations.put("cache.size", "1000");
        configurations.put("log.level", "INFO");
        configurations.put("max.connections", "50");
        configurations.put("timeout.seconds", "30");
    }
    
    // Business methods (thread-safe)
    public synchronized String getConfiguration(String key) {
        return configurations.get(key);
    }
    
    public synchronized void setConfiguration(String key, String value) {
        configurations.put(key, value);
        System.out.println("🔧 Configuration updated: " + key + " = " + value);
    }
    
    public synchronized Map<String, String> getAllConfigurations() {
        return new HashMap<>(configurations); // Return copy for thread safety
    }
    
    public synchronized void updateEnvironment(String env) {
        this.environment = env;
        System.out.println("🌍 Environment changed to: " + env);
    }
    
    public String getEnvironment() {
        return environment;
    }
    
    public boolean isInitialized() {
        return isInitialized;
    }
    
    public String getInstanceInfo() {
        return String.format("ConfigurationManager [Environment: %s, Created: %d, Configs: %d, HashCode: %d]", 
                           environment, creationTime, configurations.size(), this.hashCode());
    }
    
    // Method to demonstrate synchronization overhead
    public static void demonstratePerformanceImpact() {
        System.out.println("\n⚡ DEMONSTRATING SYNCHRONIZATION PERFORMANCE IMPACT:");
        
        // Reset instance
        instance = null;
        
        long startTime = System.nanoTime();
        
        // Multiple threads accessing getInstance simultaneously
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    ConfigurationManager config = ConfigurationManager.getInstance();
                    config.getConfiguration("app.name");
                }
                System.out.println("Thread " + threadId + " completed 1000 getInstance() calls");
            });
        }
        
        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for completion
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        long endTime = System.nanoTime();
        double totalTimeMs = (endTime - startTime) / 1_000_000.0;
        
        System.out.println("📊 Performance Test Results:");
        System.out.println("   Total time for 10,000 getInstance() calls: " + totalTimeMs + " ms");
        System.out.println("   Average time per call: " + (totalTimeMs / 10000) + " ms");
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton cannot be cloned");
    }
    
    private Object readResolve() {
        return instance;
    }
}