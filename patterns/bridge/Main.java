// Реализация (цвета)
interface Color {
    String fill();
}

class Red implements Color {
    public String fill() { return "Красный"; }
}

class Blue implements Color {
    public String fill() { return "Синий"; }
}

// Абстракция (фигуры)
abstract class Shape {
    protected Color color;  // Мост между Shape и Color

    public Shape(Color color) {
        this.color = color;
    }

    abstract String draw();
}

class Circle extends Shape {
    public Circle(Color color) {
        super(color);
    }

    public String draw() {
        return "Круг (" + color.fill() + ")";
    }
}

class Square extends Shape {
    public Square(Color color) {
        super(color);
    }

    public String draw() {
        return "Квадрат (" + color.fill() + ")";
    }
}

// Использование
public class Main {
    public static void main(String[] args) {
        Shape redCircle = new Circle(new Red());
        Shape blueSquare = new Square(new Blue());

        System.out.println(redCircle.draw());   // "Круг (Красный)"
        System.out.println(blueSquare.draw());  // "Квадрат (Синий)"
    }
}