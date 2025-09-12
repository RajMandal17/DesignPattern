package com.designpattern.singleton.billpugh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * BILL PUGH SINGLETON PATTERN (Initialization-on-demand holder idiom)
 * 
 * WHEN TO USE:
 * - Multi-threaded applications requiring optimal performance
 * - When you need lazy initialization without synchronization overhead
 * - Production applications where getInstance() is called frequently
 * - RECOMMENDED APPROACH for most scenarios
 * 
 * WHEN NOT TO USE:
 * - When you need to pass parameters to constructor
 * - Very simple single-threaded applications (though still works fine)
 * - When enum singleton is more appropriate (for specific use cases)
 * 
 * PROS:
 * + Thread-safe without synchronization overhead
 * + Lazy initialization (instance created only when needed)
 * + High performance - no locking after class loading
 * + Clean and elegant implementation
 * + JVM handles the thread safety
 * 
 * CONS:
 * - Slightly more complex to understand for beginners
 * - Uses class loading mechanism (but this is reliable)
 */
public class CacheManager {
    
    private final Map<String, Object> cache;
    private final long creationTime;
    private final int maxSize;
    private volatile long lastAccessTime;
    private volatile int hitCount;
    private volatile int missCount;
    
    // Private constructor
    private CacheManager() {
        System.out.println("🗃️  CacheManager: Initializing cache system...");
        
        this.cache = new ConcurrentHashMap<>();
        this.creationTime = System.currentTimeMillis();
        this.maxSize = 10000;
        this.lastAccessTime = creationTime;
        this.hitCount = 0;
        this.missCount = 0;
        
        // Simulate cache initialization
        initializeDefaultCache();
        
        // Simulate initialization delay
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("✅ CacheManager initialized with Bill Pugh pattern");
        System.out.println("   Max Cache Size: " + maxSize);
        System.out.println("   Initial entries: " + cache.size());
    }
    
    /**
     * Bill Pugh Solution: Inner static helper class
     * 
     * The inner class is not loaded until someone calls getInstance().
     * When the class is loaded, the instance is created in a thread-safe way
     * by the JVM's class loading mechanism.
     */
    private static class CacheManagerHolder {
        private static final CacheManager INSTANCE = new CacheManager();
    }
    
    // Public method to get the instance
    public static CacheManager getInstance() {
        return CacheManagerHolder.INSTANCE;
    }
    
    private void initializeDefaultCache() {
        // Pre-populate cache with some default values
        cache.put("app.version", "1.0.0");
        cache.put("user.session.timeout", 3600);
        cache.put("system.startup.time", System.currentTimeMillis());
        cache.put("cache.enabled", true);
        cache.put("debug.mode", false);
    }
    
    // Business methods
    public Object get(String key) {
        lastAccessTime = System.currentTimeMillis();
        Object value = cache.get(key);
        
        if (value != null) {
            hitCount++;
            System.out.println("🎯 Cache HIT for key: " + key);
        } else {
            missCount++;
            System.out.println("❌ Cache MISS for key: " + key);
        }
        
        return value;
    }
    
    public void put(String key, Object value) {
        if (cache.size() >= maxSize) {
            System.out.println("⚠️  Cache full, implementing LRU eviction...");
            evictLeastRecentlyUsed();
        }
        
        cache.put(key, value);
        lastAccessTime = System.currentTimeMillis();
        System.out.println("💾 Cached: " + key + " = " + value);
    }
    
    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }
    
    public void remove(String key) {
        Object removed = cache.remove(key);
        if (removed != null) {
            System.out.println("🗑️  Removed from cache: " + key);
        }
    }
    
    public void clear() {
        cache.clear();
        System.out.println("🧹 Cache cleared");
    }
    
    public int size() {
        return cache.size();
    }
    
    public double getHitRatio() {
        int total = hitCount + missCount;
        return total == 0 ? 0.0 : (double) hitCount / total;
    }
    
    private void evictLeastRecentlyUsed() {
        // Simple implementation - remove first entry
        // In real implementation, you'd track access times
        if (!cache.isEmpty()) {
            String firstKey = cache.keySet().iterator().next();
            cache.remove(firstKey);
            System.out.println("🗑️  Evicted: " + firstKey);
        }
    }
    
    public String getCacheStats() {
        return String.format("Cache Statistics:\n" +
                           "  Size: %d/%d\n" +
                           "  Hits: %d\n" +
                           "  Misses: %d\n" +
                           "  Hit Ratio: %.2f%%\n" +
                           "  Last Access: %d",
                           cache.size(), maxSize, hitCount, missCount, 
                           getHitRatio() * 100, lastAccessTime);
    }
    
    public String getInstanceInfo() {
        return String.format("CacheManager [Created: %d, Size: %d, HitRatio: %.2f%%, HashCode: %d]", 
                           creationTime, cache.size(), getHitRatio() * 100, this.hashCode());
    }
    
    // Method to demonstrate Bill Pugh performance benefits
    public static void demonstratePerformance() {
        System.out.println("\n🚀 DEMONSTRATING BILL PUGH PERFORMANCE:");
        
        long startTime = System.nanoTime();
        
        // Multiple threads accessing getInstance simultaneously
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    CacheManager cache = CacheManager.getInstance();
                    cache.get("app.version"); // Access cache
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
        
        System.out.println("📊 Bill Pugh Performance Results:");
        System.out.println("   Total time for 10,000 getInstance() calls: " + totalTimeMs + " ms");
        System.out.println("   Average time per call: " + (totalTimeMs / 10000) + " ms");
        System.out.println("   Note: Much faster than synchronized approach!");
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton cannot be cloned");
    }
    
    private Object readResolve() {
        return getInstance();
    }
}