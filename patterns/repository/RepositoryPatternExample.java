import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryPatternExample {

    // Доменный объект (модель)
    static class User {
        private Long id;
        private String name;
        private String email;

        public User(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        // Геттеры и сеттеры
        public Long getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }

        @Override
        public String toString() {
            return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
        }
    }

    // Интерфейс репозитория
    interface UserRepository {
        User save(User user);
        Optional<User> findById(Long id);
        List<User> findAll();
        void deleteById(Long id);
        boolean existsById(Long id);
    }

    // Реализация репозитория (в памяти для примера)
    static class InMemoryUserRepository implements UserRepository {
        private final List<User> users = new ArrayList<>();
        private Long currentId = 1L;

        @Override
        public User save(User user) {
            if (user.getId() == null) {
                // Новый пользователь
                User newUser = new User(currentId++, user.getName(), user.getEmail());
                users.add(newUser);
                return newUser;
            } else {
                // Обновление существующего
                return users.stream()
                        .filter(u -> u.getId().equals(user.getId()))
                        .findFirst()
                        .map(existingUser -> {
                            existingUser.name = user.getName();
                            existingUser.email = user.getEmail();
                            return existingUser;
                        })
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));
            }
        }

        @Override
        public Optional<User> findById(Long id) {
            return users.stream()
                    .filter(user -> user.getId().equals(id))
                    .findFirst();
        }

        @Override
        public List<User> findAll() {
            return new ArrayList<>(users);
        }

        @Override
        public void deleteById(Long id) {
            users.removeIf(user -> user.getId().equals(id));
        }

        @Override
        public boolean existsById(Long id) {
            return users.stream().anyMatch(user -> user.getId().equals(id));
        }
    }

    // Сервисный слой (бизнес-логика)
    static class UserService {
        private final UserRepository userRepository;

        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        public User registerUser(String name, String email) {
            User newUser = new User(null, name, email);
            return userRepository.save(newUser);
        }

        public User updateUserEmail(Long userId, String newEmail) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            
            User updatedUser = new User(user.getId(), user.getName(), newEmail);
            return userRepository.save(updatedUser);
        }

        public List<User> getAllUsers() {
            return userRepository.findAll();
        }
    }

    public static void main(String[] args) {
        // Создаем репозиторий
        UserRepository userRepository = new InMemoryUserRepository();
        
        // Создаем сервис, инжектируя репозиторий
        UserService userService = new UserService(userRepository);

        // Регистрируем пользователей
        User user1 = userService.registerUser("Иван Иванов", "ivan@example.com");
        User user2 = userService.registerUser("Петр Петров", "petr@example.com");

        System.out.println("Зарегистрированные пользователи:");
        userService.getAllUsers().forEach(System.out::println);

        // Обновляем email пользователя
        User updatedUser = userService.updateUserEmail(user1.getId(), "ivan.new@example.com");
        System.out.println("\nПосле обновления email:");
        System.out.println(updatedUser);

        // Получаем всех пользователей
        System.out.println("\nТекущий список пользователей:");
        userService.getAllUsers().forEach(System.out::println);
    }
}