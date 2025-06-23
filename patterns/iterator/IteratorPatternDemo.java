import java.util.NoSuchElementException;

public class IteratorPatternDemo {

    public static void main(String[] args) {
        // Создаем коллекцию книг
        BookCollection library = new BookCollection(5);
        library.addBook(new Book("1984", "George Orwell"));
        library.addBook(new Book("Brave New World", "Aldous Huxley"));
        library.addBook(new Book("Fahrenheit 451", "Ray Bradbury"));
        library.addBook(new Book("The Martian Chronicles", "Ray Bradbury"));
        library.addBook(new Book("Animal Farm", "George Orwell"));

        // Получаем итератор
        Iterator<Book> iterator = library.createIterator();

        // 1. Простой обход коллекции
        System.out.println("Все книги в коллекции:");
        while (iterator.hasNext()) {
            Book book = iterator.next();
            System.out.println(" - " + book);
        }

        // 2. Фильтрация с помощью итератора
        System.out.println("\nКниги Рэя Брэдбери:");
        iterator = library.createIterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getAuthor().equals("Ray Bradbury")) {
                System.out.println(" - " + book);
            }
        }

        // 3. Удаление элемента с помощью итератора
        iterator = library.createIterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getTitle().equals("Brave New World")) {
                iterator.remove();
                System.out.println("\nУдалена книга: " + book);
            }
        }

        // 4. Обход после удаления
        System.out.println("\nОставшиеся книги:");
        iterator = library.createIterator();
        while (iterator.hasNext()) {
            System.out.println(" - " + iterator.next());
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
            return title + " by " + author;
        }
    }

    // Интерфейс итератора
    interface Iterator<T> {
        boolean hasNext();
        T next();
        void remove();
    }

    // Интерфейс коллекции
    interface IterableCollection<T> {
        Iterator<T> createIterator();
    }

    // Конкретная коллекция (на основе массива)
    static class BookCollection implements IterableCollection<Book> {
        private Book[] books;
        private int count = 0;

        public BookCollection(int capacity) {
            books = new Book[capacity];
        }

        public void addBook(Book book) {
            if (count < books.length) {
                books[count++] = book;
            } else {
                System.out.println("Коллекция переполнена! Нельзя добавить: " + book);
            }
        }

        @Override
        public Iterator<Book> createIterator() {
            return new BookArrayIterator();
        }

        // Внутренний класс-итератор для массива
        private class BookArrayIterator implements Iterator<Book> {
            private int currentIndex = 0;
            private int lastReturned = -1; // Для поддержки remove()

            @Override
            public boolean hasNext() {
                return currentIndex < count;
            }

            @Override
            public Book next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lastReturned = currentIndex;
                return books[currentIndex++];
            }

            @Override
            public void remove() {
                if (lastReturned < 0) {
                    throw new IllegalStateException();
                }
                
                // Сдвигаем элементы массива
                for (int i = lastReturned; i < count - 1; i++) {
                    books[i] = books[i + 1];
                }
                
                // Очищаем последний элемент и уменьшаем счетчик
                books[--count] = null;
                currentIndex = lastReturned;
                lastReturned = -1;
            }
        }
    }
}