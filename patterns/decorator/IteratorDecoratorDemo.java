import java.util.NoSuchElementException;

public class IteratorDecoratorDemo {

    public static void main(String[] args) {
        // 1. Создаем коллекцию книг
        BookCollection library = new BookCollection();
        library.addBook(new Book("1984", "George Orwell"));
        library.addBook(new Book("Animal Farm", "George Orwell"));
        library.addBook(new Book("Fahrenheit 451", "Ray Bradbury"));
        library.addBook(new Book("The Martian Chronicles", "Ray Bradbury"));
        library.addBook(new Book("Brave New World", "Aldous Huxley"));

        // 2. Получаем базовый итератор
        System.out.println("Все книги:");
        Iterator<Book> baseIterator = library.createIterator();
        while (baseIterator.hasNext()) {
            System.out.println(" - " + baseIterator.next());
        }

        // 3. Создаем декорированный итератор (фильтр по автору)
        System.out.println("\nКниги Джорджа Оруэлла:");
        Iterator<Book> orwellIterator = new AuthorIterator(library.createIterator(), "George Orwell");
        while (orwellIterator.hasNext()) {
            System.out.println(" - " + orwellIterator.next());
        }

        // 4. Фильтр для другого автора
        System.out.println("\nКниги Рэя Брэдбери:");
        Iterator<Book> bradburyIterator = new AuthorIterator(library.createIterator(), "Ray Bradbury");
        while (bradburyIterator.hasNext()) {
            System.out.println(" - " + bradburyIterator.next());
        }

        // 5. Комбинируем с другим декоратором (например, лимит)
        System.out.println("\nПервая книга Оруэлла:");
        Iterator<Book> limitedOrwell = new LimitIterator<>(
            new AuthorIterator(library.createIterator(), "George Orwell"), 
            1
        );
        while (limitedOrwell.hasNext()) {
            System.out.println(" - " + limitedOrwell.next());
        }
    }

    // Класс книги
    static class Book {
        private final String title;
        private final String author;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        @Override
        public String toString() {
            return title + " (" + author + ")";
        }
    }

    // Интерфейс итератора
    interface Iterator<T> {
        boolean hasNext();
        T next();
    }

    // Базовая коллекция
    static class BookCollection {
        private static final int MAX_BOOKS = 10;
        private final Book[] books = new Book[MAX_BOOKS];
        private int count = 0;

        public void addBook(Book book) {
            if (count < MAX_BOOKS) {
                books[count++] = book;
            }
        }

        public Iterator<Book> createIterator() {
            return new BookArrayIterator();
        }

        // Внутренний итератор для массива
        private class BookArrayIterator implements Iterator<Book> {
            private int position = 0;

            @Override
            public boolean hasNext() {
                return position < count;
            }

            @Override
            public Book next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return books[position++];
            }
        }
    }

    // Декоратор для фильтрации по автору (реализует паттерн Декоратор)
    static class AuthorIterator implements Iterator<Book> {
        private final Iterator<Book> source;
        private final String author;
        private Book nextBook;
        private boolean hasNextCalled;

        public AuthorIterator(Iterator<Book> source, String author) {
            this.source = source;  // Оборачиваем исходный итератор
            this.author = author;  // Критерий фильтрации
        }

        @Override
        public boolean hasNext() {
            if (hasNextCalled) {
                return nextBook != null;
            }
            
            hasNextCalled = true;
            nextBook = null;
            
            // Итерируем по исходному итератору
            while (source.hasNext()) {
                Book candidate = source.next();
                if (candidate.getAuthor().equals(author)) {
                    nextBook = candidate;
                    break;
                }
            }
            
            return nextBook != null;
        }

        @Override
        public Book next() {
            if (!hasNextCalled && !hasNext()) {
                throw new NoSuchElementException();
            }
            
            hasNextCalled = false;
            Book result = nextBook;
            nextBook = null;
            return result;
        }
    }

    // Еще один декоратор - ограничение количества элементов
    static class LimitIterator<T> implements Iterator<T> {
        private final Iterator<T> source;
        private final int maxItems;
        private int itemCount = 0;

        public LimitIterator(Iterator<T> source, int maxItems) {
            this.source = source;
            this.maxItems = maxItems;
        }

        @Override
        public boolean hasNext() {
            return itemCount < maxItems && source.hasNext();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            itemCount++;
            return source.next();
        }
    }
}