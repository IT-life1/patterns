import java.util.ArrayList;
import java.util.List;

// Общий интерфейс для файлов и папок
interface FileSystemComponent {
    void display(int depth);
}

// Лист (отдельный файл)
class File implements FileSystemComponent {
    private String name;

    public File(String name) {
        this.name = name;
    }

    @Override
    public void display(int depth) {
        System.out.println(" ".repeat(depth * 2) + "📄 " + name);
    }
}

// Композит (папка с файлами и подпапками)
class Folder implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void add(FileSystemComponent component) {
        children.add(component);
    }

    @Override
    public void display(int depth) {
        System.out.println(" ".repeat(depth * 2) + "📁 " + name);
        for (FileSystemComponent child : children) {
            child.display(depth + 1);  // Рекурсивный вывод
        }
    }
}

// Клиентский код
public class Main {
    public static void main(String[] args) {
        Folder root = new Folder("Root");

        Folder documents = new Folder("Documents");
        documents.add(new File("resume.pdf"));
        documents.add(new File("notes.txt"));

        Folder images = new Folder("Images");
        images.add(new File("photo.jpg"));
        images.add(new File("screenshot.png"));

        root.add(documents);
        root.add(images);
        root.add(new File("README.md"));

        root.display(0);  // Вывод всей структуры
    }
}