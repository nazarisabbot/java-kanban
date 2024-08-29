public class Main {

    public static void main(String[] args) {
        // Создаем manager
        TaskManager manager = new TaskManager();

        // Создаем первый task
        Task firstTask = new Task("First name", "First description");
        manager.createTask(firstTask);

        // Создаем второй task
        Task secondTask = new Task("Second name", "Second description");
        manager.createTask(secondTask);

        // Создаем epic c двумя подзадачами
        Epic firstEpic = new Epic("FirstEpic name", "FirstEpic description");
        manager.createEpic(firstEpic);
        int firstEpicId = firstEpic.getId();

        // Создаем первую подзадачу
        Subtask firstSubtask = new Subtask("First subtask name", "First subtask description");
        manager.addSubtaskToEpic(firstEpicId, firstSubtask);

        // Создаем вторую подзадачу
        Subtask secondSubtask = new Subtask("Second subtask name", "Second subtask description");
        manager.addSubtaskToEpic(firstEpicId, secondSubtask);

        // Создаем epic c одной подзадачей
        Epic secondEpic = new Epic("SecondEpic name", "SecondEpic description");
        manager.createEpic(secondEpic);
        int secondEpicId = secondEpic.getId();

        // Создаем единственную подзадачу
        Subtask ownSubtask = new Subtask("Own subtask name", "Own subtask description");
        manager.addSubtaskToEpic(secondEpicId, ownSubtask);

        System.out.println(firstTask.toString());
        System.out.println(secondTask.toString());
        System.out.println(firstEpic.toString());
        System.out.println(secondEpic.toString());
    }
}
