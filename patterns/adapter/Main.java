// 1. Интерфейс - контракт для ВСЕХ принтеров
interface Printer {
    void print(String text); // гарантирует что у всех принтеров будет этот метод
}

// 2. Старый принтер (без изменений)
class LegacyPrinter {
    void printText(String text) {
        System.out.println("LEGACY: " + text.toUpperCase());
    }
}

// 3. Адаптер (реализует контракт)
class LegacyPrinterAdapter implements Printer {
    private LegacyPrinter legacy;
    
    public LegacyPrinterAdapter(LegacyPrinter legacy) {
        this.legacy = legacy;
    }
    
    @Override
    public void print(String text) {
        legacy.printText(text); // адаптация вызова
    }
}

// 4. НОВЫЙ принтер (сразу реализует контракт)
class ModernPrinter implements Printer {
    @Override
    public void print(String text) {
        System.out.println("MODERN: " + text);
    }
}

// 5. Система работает с ЛЮБЫМИ принтерами
class PrintingSystem {
    static void printDocument(Printer printer, String text) {
        System.out.println("Начало печати...");
        printer.print(text);
        System.out.println("Печать завершена");
    }
}

// 6. Использование
public class Main {
    public static void main(String[] args) {
        // Работа со старым принтером через адаптер
        Printer legacy = new LegacyPrinterAdapter(new LegacyPrinter());
        PrintingSystem.printDocument(legacy, "Документ 1");
        
        // Работа с современным принтером
        Printer modern = new ModernPrinter();
        PrintingSystem.printDocument(modern, "Документ 2");
        
        // Можно даже создать массив принтеров!
        Printer[] printers = {legacy, modern};
        for (Printer p : printers) {
            p.print("Один интерфейс - разные реализации!");
        }
    }
}