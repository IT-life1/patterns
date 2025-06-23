public class TemplateMethodPattern {

    // Абстрактный класс с шаблонным методом
    abstract static class Game {
        // Шаблонный метод (финализирован, чтобы нельзя было изменить структуру)
        final void play() {
            initialize();
            startPlay();
            endPlay();
            printResult();
        }

        // Реализованные шаги (могут быть переопределены)
        void initialize() {
            System.out.println("Игра инициализирована");
        }

        // Абстрактные шаги (должны быть реализованы подклассами)
        abstract void startPlay();
        abstract void endPlay();

        // Переопределяемый шаг (хук)
        void printResult() {
            System.out.println("Результаты игры: ...");
        }
    }

    // Конкретная реализация для футбола
    static class Football extends Game {
        @Override
        void startPlay() {
            System.out.println("Футбол: матч начался!");
        }

        @Override
        void endPlay() {
            System.out.println("Футбол: матч закончился со счетом 2:1");
        }

        @Override
        void printResult() {
            System.out.println("Футбол: победа команды А!");
        }
    }

    // Конкретная реализация для шахмат
    static class Chess extends Game {
        @Override
        void startPlay() {
            System.out.println("Шахматы: партия началась!");
        }

        @Override
        void endPlay() {
            System.out.println("Шахматы: партия закончилась матом на 25 ходу");
        }

        @Override
        void initialize() {
            System.out.println("Шахматы: расставлены фигуры, часы включены");
        }
        // printResult() не переопределен - используется реализация по умолчанию
    }

    public static void main(String[] args) {
        System.out.println("--- Играем в футбол ---");
        Game football = new Football();
        football.play();

        System.out.println("\n--- Играем в шахматы ---");
        Game chess = new Chess();
        chess.play();
    }
}