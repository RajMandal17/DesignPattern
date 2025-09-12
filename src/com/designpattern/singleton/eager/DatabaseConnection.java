package com.designpattern.singleton.eager;

/**
 * EAGER INITIALIZATION SINGLETON PATTERN
 * 
 * WHEN TO USE:
 * - When you're sure the instance will be used in your application
 * - When creating the instance is not resource-intensive
 * - When you need thread-safety without synchronization overhead
 * 
 * WHEN NOT TO USE:
 * - When instance creation is expensive (memory/time)
 * - When the instance might not be used at all
 * - When you need lazy loading for performance reasons
 * 
 * PROS:
 * + Thread-safe without synchronization
 * + Simple implementation
 * + No performance overhead on getInstance()
 * 
 * CONS:
 * - Instance created even if never used (memory waste)
 * - Cannot handle exceptions during construction
 * - Instance created at class loading time (may increase startup time)
 */
public class DatabaseConnection {
    
    // Instance created at class loading time
    private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    
    private String connectionUrl;
    private boolean isConnected;
    private long connectionTime;
    
    // Private constructor prevents external instantiation
    private DatabaseConnection() {
        // Simulate expensive database connection setup
        try {
            Thread.sleep(100); // Simulate connection delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        this.connectionUrl = "jdbc:mysql://localhost:3306/testdb";
        this.isConnected = true;
        this.connectionTime = System.currentTimeMillis();
        
        System.out.println("🔗 DatabaseConnection created eagerly at class loading time");
        System.out.println("   Connection URL: " + connectionUrl);
        System.out.println("   Connection Time: " + connectionTime);
    }
    
    // Public method to get the instance
    public static DatabaseConnection getInstance() {
        return INSTANCE;
    }
    
    // Business methods
    public void executeQuery(String query) {
        if (!isConnected) {
            throw new IllegalStateException("Database not connected");
        }
        System.out.println("📊 Executing query: " + query);
    }
    
    public String getConnectionInfo() {
        return String.format("Database Connection [URL: %s, Connected: %s, Created: %d]", 
                           connectionUrl, isConnected, connectionTime);
    }
    
    public void closeConnection() {
        isConnected = false;
        System.out.println("🔒 Database connection closed");
    }
    
    // Demonstrate that this prevents cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton cannot be cloned");
    }
    
    // Handle serialization (if needed)
    private Object readResolve() {
        return INSTANCE;
    }
}