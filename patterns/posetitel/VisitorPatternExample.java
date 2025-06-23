import java.util.Arrays;
import java.util.List;

public class VisitorPatternExample {

    // Интерфейс элемента (может принять посетителя)
    interface Shape {
        void accept(ShapeVisitor visitor);
    }

    // Конкретные элементы
    static class Circle implements Shape {
        private double radius;

        public Circle(double radius) {
            this.radius = radius;
        }

        public double getRadius() {
            return radius;
        }

        @Override
        public void accept(ShapeVisitor visitor) {
            visitor.visit(this);
        }
    }

    static class Rectangle implements Shape {
        private double width;
        private double height;

        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }

        @Override
        public void accept(ShapeVisitor visitor) {
            visitor.visit(this);
        }
    }

    // Интерфейс посетителя
    interface ShapeVisitor {
        void visit(Circle circle);
        void visit(Rectangle rectangle);
    }

    // Конкретные посетители
    static class AreaCalculator implements ShapeVisitor {
        private double totalArea = 0;

        public double getTotalArea() {
            return totalArea;
        }

        @Override
        public void visit(Circle circle) {
            double area = Math.PI * circle.getRadius() * circle.getRadius();
            System.out.println("Calculating area of circle: " + area);
            totalArea += area;
        }

        @Override
        public void visit(Rectangle rectangle) {
            double area = rectangle.getWidth() * rectangle.getHeight();
            System.out.println("Calculating area of rectangle: " + area);
            totalArea += area;
        }
    }

    static class PerimeterCalculator implements ShapeVisitor {
        private double totalPerimeter = 0;

        public double getTotalPerimeter() {
            return totalPerimeter;
        }

        @Override
        public void visit(Circle circle) {
            double perimeter = 2 * Math.PI * circle.getRadius();
            System.out.println("Calculating perimeter of circle: " + perimeter);
            totalPerimeter += perimeter;
        }

        @Override
        public void visit(Rectangle rectangle) {
            double perimeter = 2 * (rectangle.getWidth() + rectangle.getHeight());
            System.out.println("Calculating perimeter of rectangle: " + perimeter);
            totalPerimeter += perimeter;
        }
    }

    public static void main(String[] args) {
        // Создаем коллекцию фигур
        List<Shape> shapes = Arrays.asList(
            new Circle(5),
            new Rectangle(10, 20),
            new Circle(3),
            new Rectangle(5, 5)
        );

        // Создаем посетителей
        AreaCalculator areaVisitor = new AreaCalculator();
        PerimeterCalculator perimeterVisitor = new PerimeterCalculator();

        // Применяем посетителей к каждой фигуре
        for (Shape shape : shapes) {
            shape.accept(areaVisitor);
            shape.accept(perimeterVisitor);
        }

        // Выводим результаты
        System.out.println("\nTotal area: " + areaVisitor.getTotalArea());
        System.out.println("Total perimeter: " + perimeterVisitor.getTotalPerimeter());
    }
}