public class Main {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        History history = new History();

        editor.setContent("Version 1");
        history.save(editor.createSnapshot());

        editor.setContent("Version 2");
        history.save(editor.createSnapshot());

        editor.setContent("Version 3");
        System.out.println("Current: " + editor.getContent()); // Version 3

        editor.restore(history.undo());
        System.out.println("After undo: " + editor.getContent()); // Version 2

        editor.restore(history.undo());
        System.out.println("After second undo: " + editor.getContent()); // Version 1
    }
}

// Originator
class TextEditor {
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public EditorMemento createSnapshot() {
        return new EditorMemento(content);
    }

    public void restore(EditorMemento memento) {
        this.content = memento.getContent();
    }
}

// Memento
class EditorMemento {
    private final String content;

    public EditorMemento(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

// Caretaker
class History {
    private java.util.Stack<EditorMemento> snapshots = new java.util.Stack<>();

    public void save(EditorMemento memento) {
        snapshots.push(memento);
    }

    public EditorMemento undo() {
        if (!snapshots.isEmpty()) {
            return snapshots.pop();
        }
        return null;
    }
}
