package com.designpattern.singleton.lazy;

/**
 * LAZY INITIALIZATION SINGLETON PATTERN (NOT THREAD-SAFE)
 * 
 * WHEN TO USE:
 * - Single-threaded applications only
 * - When instance creation is expensive and might not be needed
 * - For educational purposes to understand the basic concept
 * 
 * WHEN NOT TO USE:
 * - Multi-threaded applications (NEVER!)
 * - Production environments with concurrent access
 * - When thread safety is a requirement
 * 
 * PROS:
 * + Instance created only when needed (lazy loading)
 * + Memory efficient if instance is never used
 * + Simple implementation
 * 
 * CONS:
 * - NOT THREAD-SAFE (major issue in multi-threaded environments)
 * - Multiple instances can be created in concurrent scenarios
 * - Breaks singleton contract in multi-threaded apps
 */
public class Logger {
    
    private static Logger instance; // Instance created lazily
    
    private StringBuilder logBuffer;
    private long creationTime;
    private int logCount;
    
    // Private constructor
    private Logger() {
        // Simulate some initialization work
        this.logBuffer = new StringBuilder();
        this.creationTime = System.currentTimeMillis();
        this.logCount = 0;
        
        System.out.println("📝 Logger instance created lazily");
        System.out.println("   Creation Time: " + creationTime);
        
        // Simulate initialization delay
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // NOT THREAD-SAFE getInstance method
    public static Logger getInstance() {
        if (instance == null) {
            // PROBLEM: Multiple threads can pass this check simultaneously
            // This can lead to multiple instances being created
            instance = new Logger();
        }
        return instance;
    }
    
    // Business methods
    public void log(String message) {
        logCount++;
        String timestampedMessage = String.format("[%d] %s: %s", 
                                                 System.currentTimeMillis(), 
                                                 Thread.currentThread().getName(), 
                                                 message);
        logBuffer.append(timestampedMessage).append("\n");
        System.out.println("📝 " + timestampedMessage);
    }
    
    public void logError(String error) {
        log("ERROR - " + error);
    }
    
    public void logInfo(String info) {
        log("INFO - " + info);
    }
    
    public String getLogs() {
        return logBuffer.toString();
    }
    
    public int getLogCount() {
        return logCount;
    }
    
    public String getInstanceInfo() {
        return String.format("Logger Instance [Created: %d, Logs: %d, HashCode: %d]", 
                           creationTime, logCount, this.hashCode());
    }
    
    // Method to demonstrate thread-safety issues
    public static void demonstrateThreadSafetyIssue() {
        System.out.println("\n🚨 DEMONSTRATING THREAD-SAFETY ISSUE:");
        System.out.println("Creating multiple threads to access Logger simultaneously...\n");
        
        // Reset instance for demonstration
        instance = null;
        
        // Create multiple threads that try to get the instance
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                Logger logger = Logger.getInstance();
                logger.log("Thread " + threadId + " got instance: " + logger.hashCode());
            });
        }
        
        // Start all threads simultaneously
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton cannot be cloned");
    }
}