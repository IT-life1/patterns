// 1. Интерфейс стратегии
interface OperationStrategy {
    int execute(int a, int b);
}

// 2. Конкретные стратегии
class AddStrategy implements OperationStrategy {
    public int execute(int a, int b) {
        return a + b;
    }
}

class SubtractStrategy implements OperationStrategy {
    public int execute(int a, int b) {
        return a - b;
    }
}

class MultiplyStrategy implements OperationStrategy {
    public int execute(int a, int b) {
        return a * b;
    }
}

// 3. Контекст
class Calculator {
    private OperationStrategy strategy;

    public void setStrategy(OperationStrategy strategy) {
        this.strategy = strategy;
    }

    public int calculate(int a, int b) {
        return strategy.execute(a, b);
    }
}

// 4. Использование
public class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();

        calc.setStrategy(new AddStrategy());
        System.out.println("Сложение: " + calc.calculate(5, 3));

        calc.setStrategy(new SubtractStrategy());
        System.out.println("Вычитание: " + calc.calculate(5, 3));

        calc.setStrategy(new MultiplyStrategy());
        System.out.println("Умножение: " + calc.calculate(5, 3));
    }
}
