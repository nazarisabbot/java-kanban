package tracker.controllers;

public class Managers {

    private Managers() {
        // Предотвращение создания экземпляров
        // Так как есть прописанный конструктор,
        // по идеи не должен сработать конструктор по умолчанию.
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager();
    }
}

