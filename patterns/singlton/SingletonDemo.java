import java.lang.reflect.Constructor;

public class SingletonDemo {

    public static void main(String[] args) throws Exception {
        testVulnerableSingleton();
        testProtectedSingleton();
    }

    // Тестируем уязвимый Singleton
    private static void testVulnerableSingleton() throws Exception {
        System.out.println("\n=== Тест 1: Уязвимый Singleton ===");
        
        // Создаем первый экземпляр обычным способом
        VulnerableSingleton vs1 = VulnerableSingleton.getInstance();
        System.out.println("Создан vs1: " + vs1.hashCode());
        
        // Создаем второй экземпляр через рефлексию
        Constructor<VulnerableSingleton> constructor = VulnerableSingleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        VulnerableSingleton vs2 = constructor.newInstance();
        
        System.out.println("Создан vs2: " + vs2.hashCode());
        System.out.println("vs1 == vs2? " + (vs1 == vs2)); // false - разные объекты!
    }

    // Тестируем защищенный Singleton
    private static void testProtectedSingleton() throws Exception {
        System.out.println("\n=== Тест 2: Защищенный Singleton ===");
        
        // Создаем первый экземпляр обычным способом
        ProtectedSingleton ps1 = ProtectedSingleton.getInstance();
        System.out.println("Создан ps1: " + ps1.hashCode());
        
        // Пробуем создать второй экземпляр через рефлексию
        try {
            Constructor<ProtectedSingleton> constructor = ProtectedSingleton.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            ProtectedSingleton ps2 = constructor.newInstance();
            System.out.println("Создан ps2: " + ps2.hashCode());
        } catch (Exception e) {
            System.out.println("Ошибка при создании второго экземпляра: " + e.getMessage());
        }
    }
}

// Уязвимая реализация Singleton
class VulnerableSingleton {
    private static volatile VulnerableSingleton instance;
    
    private VulnerableSingleton() {
        System.out.println("Уязвимый Singleton: конструктор вызван");
    }
    
    public static VulnerableSingleton getInstance() {
        if (instance == null) {
            synchronized (VulnerableSingleton.class) {
                if (instance == null) {
                    instance = new VulnerableSingleton();
                }
            }
        }
        return instance;
    }
}

// Защищенная реализация Singleton
class ProtectedSingleton {
    private static volatile ProtectedSingleton instance;
    private static volatile boolean created = false;
    
    private ProtectedSingleton() {
        if (created) {
            throw new IllegalStateException("Экземпляр уже создан!");
        }
        created = true;
        System.out.println("Защищенный Singleton: конструктор вызван");
    }
    
    public static ProtectedSingleton getInstance() {
        if (instance == null) {
            synchronized (ProtectedSingleton.class) {
                if (instance == null) {
                    instance = new ProtectedSingleton();
                }
            }
        }
        return instance;
    }
}