public class ProtectionProxyDemo {
    public static void main(String[] args) {
        User admin = new User("admin", "ADMIN");
        User guest = new User("guest", "GUEST");
        
        SensitiveService service = new SensitiveServiceProxy();
        
        service.accessData(admin);  // Доступ разрешен
        service.accessData(guest); // Доступ запрещен
    }
}

// Класс пользователя (добавлен)
class User {
    private final String username;
    private final String role;
    
    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }
    
    public String username() {
        return username;
    }
    
    public String role() {
        return role;
    }
}

interface SensitiveService {
    void accessData(User user);
}

class RealSensitiveService implements SensitiveService {
    @Override
    public void accessData(User user) {
        System.out.println("Доступ к критическим данным для " + user.username());
    }
}

class SensitiveServiceProxy implements SensitiveService {
    private final SensitiveService service = new RealSensitiveService();
    
    @Override
    public void accessData(User user) {
        if ("ADMIN".equals(user.role())) {
            service.accessData(user);
        } else {
            System.out.println("ОШИБКА: Доступ запрещен для " + user.username());
        }
    }
}