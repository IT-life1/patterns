import java.util.HashMap;
import java.util.Map;

// 1. Класс разделяемого объекта (Flyweight)
class Character {
    private final char symbol;  // Внутреннее состояние (неизменяемое)
    private final String font;  // Внутреннее состояние

    public Character(char symbol, String font) {
        this.symbol = symbol;
        this.font = font;
    }

    public void draw(int x, int y) {  // Внешнее состояние передаётся извне
        System.out.printf("Symbol: %c (Font: %s) at (%d, %d)\n", symbol, font, x, y);
    }
}

// 2. Фабрика для создания/переиспользования Flyweight-объектов
class CharacterFactory {
    private static final Map<Character, Character> pool = new HashMap<>();

    public static Character getCharacter(char symbol, String font) {
        Character key = new Character(symbol, font);
        if (!pool.containsKey(key)) {
            pool.put(key, key);
        }
        return pool.get(key);
    }
}

// 3. Клиентский код
public class Main {
    public static void main(String[] args) {
        // Символы 'A' и 'B' с общим шрифтом переиспользуются
        Character a1 = CharacterFactory.getCharacter('A', "Times New Roman");
        Character a2 = CharacterFactory.getCharacter('A', "Times New Roman");  // Возвращает существующий объект
        Character b = CharacterFactory.getCharacter('B', "Arial");

        a1.draw(10, 20);  // Symbol: A (Font: Times New Roman) at (10, 20)
        a2.draw(30, 40);  // Symbol: A (Font: Times New Roman) at (30, 40)
        b.draw(50, 60);   // Symbol: B (Font: Arial) at (50, 60)
    }
}