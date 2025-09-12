package com.designpattern.singleton.enumsingleton;

import java.util.concurrent.atomic.AtomicLong;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ENUM SINGLETON PATTERN
 * 
 * WHEN TO USE:
 * - When you need the safest singleton implementation
 * - When protection against reflection attacks is important
 * - When serialization safety is crucial
 * - For application state management
 * - When you want the most concise implementation
 * 
 * WHEN NOT TO USE:
 * - When you need to extend a class (enums cannot extend classes)
 * - When you need lazy initialization (enum is eager by nature)
 * - When the singleton needs constructor parameters
 * - When inheritance is required
 * 
 * PROS:
 * + Thread-safe by default
 * + Reflection-proof (cannot be instantiated via reflection)
 * + Serialization-safe (handles serialization correctly)
 * + Concise and clean implementation
 * + Cannot be cloned
 * + JVM guarantees single instance
 * 
 * CONS:
 * - Cannot extend other classes
 * - Eager initialization (instance created at enum loading)
 * - Less flexible than class-based implementations
 * - May be confusing for developers unfamiliar with enum singletons
 */
public enum ApplicationState {
    
    INSTANCE; // The single instance
    
    // Instance variables
    private final AtomicLong requestCounter;
    private final LocalDateTime startupTime;
    private volatile String currentUser;
    private volatile String applicationMode;
    private final StringBuilder eventLog;
    
    // Enum constructor (called only once when enum is loaded)
    ApplicationState() {
        System.out.println("🚀 ApplicationState: Initializing application state...");
        
        this.requestCounter = new AtomicLong(0);
        this.startupTime = LocalDateTime.now();
        this.currentUser = "anonymous";
        this.applicationMode = "PRODUCTION";
        this.eventLog = new StringBuilder();
        
        // Log startup event
        logEvent("Application state initialized");
        
        // Simulate initialization work
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("✅ ApplicationState initialized successfully");
        System.out.println("   Startup Time: " + startupTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println("   Mode: " + applicationMode);
    }
    
    // Business methods
    public long getNextRequestId() {
        long requestId = requestCounter.incrementAndGet();
        logEvent("Generated request ID: " + requestId);
        return requestId;
    }
    
    public long getCurrentRequestCount() {
        return requestCounter.get();
    }
    
    public void setCurrentUser(String username) {
        String previousUser = this.currentUser;
        this.currentUser = username;
        logEvent("User changed from '" + previousUser + "' to '" + username + "'");
        System.out.println("👤 Current user set to: " + username);
    }
    
    public String getCurrentUser() {
        return currentUser;
    }
    
    public void setApplicationMode(String mode) {
        String previousMode = this.applicationMode;
        this.applicationMode = mode;
        logEvent("Application mode changed from '" + previousMode + "' to '" + mode + "'");
        System.out.println("⚙️  Application mode set to: " + mode);
    }
    
    public String getApplicationMode() {
        return applicationMode;
    }
    
    public LocalDateTime getStartupTime() {
        return startupTime;
    }
    
    public String getUptime() {
        LocalDateTime now = LocalDateTime.now();
        long seconds = java.time.Duration.between(startupTime, now).getSeconds();
        
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }
    
    private void logEvent(String event) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        eventLog.append("[").append(timestamp).append("] ").append(event).append("\n");
    }
    
    public String getEventLog() {
        return eventLog.toString();
    }
    
    public void clearEventLog() {
        eventLog.setLength(0);
        logEvent("Event log cleared");
    }
    
    public String getStateInfo() {
        return String.format("ApplicationState:\n" +
                           "  Startup Time: %s\n" +
                           "  Uptime: %s\n" +
                           "  Current User: %s\n" +
                           "  Mode: %s\n" +
                           "  Request Count: %d\n" +
                           "  Instance Hash: %d",
                           startupTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                           getUptime(),
                           currentUser,
                           applicationMode,
                           requestCounter.get(),
                           this.hashCode());
    }
    
    // Method to demonstrate enum singleton benefits
    public static void demonstrateEnumBenefits() {
        System.out.println("\n🔒 DEMONSTRATING ENUM SINGLETON BENEFITS:");
        
        // 1. Show that there's only one instance
        ApplicationState state1 = ApplicationState.INSTANCE;
        ApplicationState state2 = ApplicationState.INSTANCE;
        
        System.out.println("Same instance check: " + (state1 == state2));
        System.out.println("Hash codes: " + state1.hashCode() + " vs " + state2.hashCode());
        
        // 2. Demonstrate reflection protection
        try {
            Class<?> enumClass = ApplicationState.class;
            // This will throw an exception - enums cannot be instantiated via reflection
            enumClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("✅ Reflection protection: " + e.getClass().getSimpleName());
        }
        
        // 3. Show thread safety
        System.out.println("\n🧵 Testing thread safety...");
        Thread[] threads = new Thread[5];
        
        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                ApplicationState state = ApplicationState.INSTANCE;
                long requestId = state.getNextRequestId();
                System.out.println("Thread " + threadId + " got request ID: " + requestId + 
                                 " (instance hash: " + state.hashCode() + ")");
            });
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    // Method to simulate application workflow
    public void simulateWorkflow() {
        System.out.println("\n📋 SIMULATING APPLICATION WORKFLOW:");
        
        setCurrentUser("admin");
        
        for (int i = 1; i <= 3; i++) {
            long requestId = getNextRequestId();
            System.out.println("Processing request " + requestId);
            
            try {
                Thread.sleep(50); // Simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        setApplicationMode("MAINTENANCE");
        
        for (int i = 1; i <= 2; i++) {
            long requestId = getNextRequestId();
            System.out.println("Maintenance request " + requestId);
        }
        
        setApplicationMode("PRODUCTION");
        setCurrentUser("user123");
        
        System.out.println("\n📊 Final State:");
        System.out.println(getStateInfo());
    }
}