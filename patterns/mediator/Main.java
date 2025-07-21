public class Main {
    public static void main(String[] args) {
        ChatMediator mediator = new ChatRoom();

        User alice = new ConcreteUser("Alice", mediator);
        User bob = new ConcreteUser("Bob", mediator);
        User charlie = new ConcreteUser("Charlie", mediator);

        mediator.addUser(alice);
        mediator.addUser(bob);
        mediator.addUser(charlie);

        alice.send("Hi everyone!");
        bob.send("Hey Alice!");
    }
}

// Интерфейс посредника
interface ChatMediator {
    void sendMessage(String message, User sender);
    void addUser(User user);
}

// Конкретный посредник
class ChatRoom implements ChatMediator {
    private java.util.List<User> users = new java.util.ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public void sendMessage(String message, User sender) {
        for (User u : users) {
            if (u != sender) {
                u.receive(message);
            }
        }
    }
}

// Абстрактный участник
abstract class User {
    protected ChatMediator mediator;
    protected String name;

    public User(String name, ChatMediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }

    public abstract void send(String message);
    public abstract void receive(String message);
}

// Конкретный участник
class ConcreteUser extends User {
    public ConcreteUser(String name, ChatMediator mediator) {
        super(name, mediator);
    }

    public void send(String message) {
        System.out.println(name + " отправляет: " + message);
        mediator.sendMessage(message, this);
    }

    public void receive(String message) {
        System.out.println(name + " получил сообщение: " + message);
    }
}
