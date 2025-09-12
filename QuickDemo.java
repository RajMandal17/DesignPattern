import com.designpattern.singleton.billpugh.CacheManager;
import com.designpattern.singleton.enumsingleton.ApplicationState;
import com.designpattern.singleton.eager.DatabaseConnection;

public class QuickDemo {
    public static void main(String[] args) {
        System.out.println("🎯 SINGLETON PATTERN QUICK DEMONSTRATION");
        System.out.println("=" .repeat(50));
        
        // 1. Bill Pugh Singleton Demo
        System.out.println("\n🔹 Bill Pugh Singleton (CacheManager):");
        CacheManager cache1 = CacheManager.getInstance();
        CacheManager cache2 = CacheManager.getInstance();
        
        System.out.println("Same instance? " + (cache1 == cache2));
        cache1.put("message", "Hello from Singleton!");
        cache1.put("language", "Java");
        
        System.out.println("Retrieved message: " + cache1.get("message"));
        System.out.println("Cache size: " + cache1.size());
        
        // 2. Enum Singleton Demo
        System.out.println("\n🔹 Enum Singleton (ApplicationState):");
        ApplicationState state1 = ApplicationState.INSTANCE;
        ApplicationState state2 = ApplicationState.INSTANCE;
        
        System.out.println("Same instance? " + (state1 == state2));
        state1.setCurrentUser("developer");
        
        long requestId1 = state1.getNextRequestId();
        long requestId2 = state1.getNextRequestId();
        
        System.out.println("Request IDs: " + requestId1 + ", " + requestId2);
        System.out.println("Current user: " + state1.getCurrentUser());
        System.out.println("Uptime: " + state1.getUptime());
        
        // 3. Eager Singleton Demo  
        System.out.println("\n🔹 Eager Singleton (DatabaseConnection):");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        
        System.out.println("Same instance? " + (db1 == db2));
        db1.executeQuery("SELECT * FROM users WHERE active = true");
        
        System.out.println("\n✅ All singletons working correctly!");
        System.out.println("💡 Remember: Bill Pugh is recommended for most use cases!");
    }
}