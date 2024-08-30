public class Main {

    public static void main(String[] args) {
        // Создаем manager
        TaskManager manager = new TaskManager();

        // Создаем первый task
        Task firstTask = new Task("First name", "First description");
        manager.createTask(firstTask);
        int firstTaskId = firstTask.getId();

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
        int firstSubTaskId = firstSubtask.getId();

        // Создаем вторую подзадачу
        Subtask secondSubtask = new Subtask("Second subtask name", "Second subtask description");
        manager.addSubtaskToEpic(firstEpicId, secondSubtask);
        int secondSubTaskId = secondSubtask.getId();

        // Создаем epic c одной подзадачей
        Epic secondEpic = new Epic("SecondEpic name", "SecondEpic description");
        manager.createEpic(secondEpic);
        int secondEpicId = secondEpic.getId();

        // Создаем единственную подзадачу
        Subtask ownSubtask = new Subtask("Own subtask name", "Own subtask description");
        manager.addSubtaskToEpic(secondEpicId, ownSubtask);

        System.out.println("Печать первого Task");
        System.out.println(firstTask.toString());
        System.out.println("Печать второго Task");
        System.out.println(secondTask.toString());
        System.out.println("Печать первого Epic");
        System.out.println(firstEpic.toString());
        System.out.println("Печать второго Epic");
        System.out.println(secondEpic.toString());

        // Обновляем статус у первого Task
        manager.updateStatusTask(firstTaskId, "IN_PROGRESS");
        System.out.println("Печать обновленного статуса первого Task на in_progress");
        System.out.println(firstTask.getStatus());

        manager.updateStatusTask(firstTaskId, "DONE");
        System.out.println("Печать обновленного статуса первого Task на done");
        System.out.println(firstTask.getStatus());

        // Обновляем статус у первой подзадачи первого эпика
        manager.updateStatusSubTask(firstEpicId, firstSubTaskId, "IN_PROGRESS");
        System.out.println("Печать обновленного статуса первой подзадачи первого эпика на in_progress");
        System.out.println(firstSubtask.getStatus());

        // Обновляем статус у первого эпика
        manager.updateStatusEpic(firstEpicId);

        // Печать эпика после того как изменили статус у одной из подзадачи
        // теперь его статус не NEW, а IN_PROGRESS
        System.out.println("Печать обновленного статуса первого эпика на in_progress");
        System.out.println(firstEpic.getStatus());

        // Обновим статус обоих подзадач на DONE
        manager.updateStatusSubTask(firstEpicId, firstSubTaskId, "DONE");
        manager.updateStatusSubTask(firstEpicId, secondSubTaskId, "DONE");

        // Проверяем статус обоих подзадач
        System.out.println("Печать обновленного статуса обоих подзадач на done");
        System.out.println(firstSubtask.getStatus());
        System.out.println(secondSubtask.getStatus());

        // Обновляем статус эпика
        manager.updateStatusEpic(firstEpicId);

        // Проверяем статус эпика, ондолжен быть DONE
        System.out.println("Печать обновленного статуса первого эпика на done");
        System.out.println(firstEpic.getStatus());

        // Удаляем первый Task
        manager.deleteTask(firstTaskId);

        // Пытаемся получить первый Task
        System.out.println("Попытка получить удаленный Task");
        System.out.println(manager.getTask(firstTaskId));

        // Удалем первый эпик
        manager.deleteEpic(firstEpicId);

        // Пытаемся получить первый эпик
        System.out.println("Попытка получить удаленный Epic");
        System.out.println(manager.getEpic(firstEpicId));
    }
}
