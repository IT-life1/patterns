import java.util.HashMap;
import java.util.Map;

public class ControllerPatternExample {

    // Модель
    static class User {
        private String name;
        private String email;
        
        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
    }

    // Бизнес-логика (сервис)
    static class UserService {
        public void registerUser(User user) {
            // логика регистрации пользователя
            System.out.println("User registered: " + user.getName() + ", email: " + user.getEmail());
        }
    }

    // Контроллер
    static class UserController {
        private UserService userService;
        
        public UserController() {
            this.userService = new UserService();
        }
        
        public void handleRegistration(HttpRequest request) {
            // Получаем данные из запроса
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            
            // Валидация
            if (name == null || email == null) {
                throw new IllegalArgumentException("Name and email are required");
            }
            
            // Создаем модель
            User user = new User(name, email);
            
            // Вызываем бизнес-логику
            userService.registerUser(user);
            
            // Перенаправляем на страницу успеха
            redirect("/registration-success");
        }
        
        private void redirect(String url) {
            System.out.println("Redirecting to: " + url);
        }
    }

    // Вспомогательный класс для имитации HTTP запроса
    static class HttpRequest {
        private Map<String, String> params = new HashMap<>();
        
        public String getParameter(String name) {
            return params.get(name);
        }
        
        public void setParameter(String name, String value) {
            params.put(name, value);
        }
    }

    public static void main(String[] args) {
        UserController controller = new UserController();
        
        // Имитация HTTP запроса
        HttpRequest mockRequest = new HttpRequest();
        mockRequest.setParameter("name", "John Doe");
        mockRequest.setParameter("email", "john@example.com");
        
        controller.handleRegistration(mockRequest);
    }
}