public class AbstractFactoryDemo {

    public static void main(String[] args) {
        // Создаем фабрику для Windows
        GUIFactory windowsFactory = new WindowsFactory();
        Application windowsApp = new Application(windowsFactory);
        windowsApp.renderUI();
        
        System.out.println("\n====================\n");
        
        // Создаем фабрику для macOS
        GUIFactory macFactory = new MacOSFactory();
        Application macApp = new Application(macFactory);
        macApp.renderUI();
    }

    // 1. Абстрактные продукты
    interface Button {
        void render();
        void onClick();
    }
    
    interface Checkbox {
        void render();
        void toggle();
    }
    
    // 2. Конкретные продукты для Windows
    static class WindowsButton implements Button {
        @Override
        public void render() {
            System.out.println("[Windows] Отрисована кнопка в стиле Win32");
        }
        
        @Override
        public void onClick() {
            System.out.println(">>> Windows кнопка нажата");
        }
    }
    
    static class WindowsCheckbox implements Checkbox {
        @Override
        public void render() {
            System.out.println("[Windows] Отрисован чекбокс в стиле Win32");
        }
        
        @Override
        public void toggle() {
            System.out.println(">>> Windows чекбокс переключен");
        }
    }
    
    // 3. Конкретные продукты для macOS
    static class MacOSButton implements Button {
        @Override
        public void render() {
            System.out.println("[macOS] Отрисована кнопка в стиле Aqua");
        }
        
        @Override
        public void onClick() {
            System.out.println(">>> macOS кнопка нажата");
        }
    }
    
    static class MacOSCheckbox implements Checkbox {
        @Override
        public void render() {
            System.out.println("[macOS] Отрисован чекбокс в стиле Aqua");
        }
        
        @Override
        public void toggle() {
            System.out.println(">>> macOS чекбокс переключен");
        }
    }
    
    // 4. Абстрактная фабрика
    interface GUIFactory {
        Button createButton();
        Checkbox createCheckbox();
    }
    
    // 5. Конкретные фабрики
    static class WindowsFactory implements GUIFactory {
        @Override
        public Button createButton() {
            return new WindowsButton();
        }
        
        @Override
        public Checkbox createCheckbox() {
            return new WindowsCheckbox();
        }
    }
    
    static class MacOSFactory implements GUIFactory {
        @Override
        public Button createButton() {
            return new MacOSButton();
        }
        
        @Override
        public Checkbox createCheckbox() {
            return new MacOSCheckbox();
        }
    }
    
    // 6. Клиентский код
    static class Application {
        private final Button button;
        private final Checkbox checkbox;
        
        public Application(GUIFactory factory) {
            // Создаем связанные объекты из одной фабрики
            button = factory.createButton();
            checkbox = factory.createCheckbox();
        }
        
        public void renderUI() {
            System.out.println("Создаем UI...");
            button.render();
            checkbox.render();
            
            // Тестируем взаимодействие
            System.out.println("\nТестируем элементы:");
            button.onClick();
            checkbox.toggle();
        }
    }
}