public class Main {
    public static void main(String[] args) {
        Handler auth = new AuthorizationHandler();
        Handler role = new RoleCheckHandler();
        Handler access = new AccessHandler();

        auth.setNext(role).setNext(access);

        System.out.println("=== 1. Правильный запрос ===");
        auth.handle("auth admin");

        System.out.println("\n=== 2. Без авторизации ===");
        auth.handle("admin");

        System.out.println("\n=== 3. Без прав администратора ===");
        auth.handle("auth user");
    }
}

// Абстрактный обработчик
abstract class Handler {
    protected Handler next;

    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }

    public abstract void handle(String request);
}

// Первый обработчик: проверка авторизации
class AuthorizationHandler extends Handler {
    public void handle(String request) {
        if (request.contains("auth")) {
            System.out.println("✔ Authorization passed");
            if (next != null) next.handle(request);
        } else {
            System.out.println("❌ Authorization failed");
        }
    }
}

// Второй обработчик: проверка роли
class RoleCheckHandler extends Handler {
    public void handle(String request) {
        if (request.contains("admin")) {
            System.out.println("✔ Role check passed");
            if (next != null) next.handle(request);
        } else {
            System.out.println("❌ Access denied: insufficient role");
        }
    }
}

// Финальный обработчик: предоставление доступа
class AccessHandler extends Handler {
    public void handle(String request) {
        System.out.println("🔓 Access granted to secure resource");
    }
}
