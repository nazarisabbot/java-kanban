import tracker.model.Epic;
import tracker.model.Subtask;
import tracker.model.Task;
import tracker.controllers.TaskManager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Создаем manager
        TaskManager manager = new TaskManager();

        // Создаем первый task
        int firstTaskId = manager.createTask("First name", "First description");

        // Создаем второй task
        int secondTaskId = manager.createTask("Second name", "Second description");

        // Создаем epic c двумя подзадачами
        int firstEpicId = manager.createEpic("FirstEpic name", "FirstEpic description");

        // Создаем первую подзадачу
        int firstSubTaskId = manager.addSubtaskToEpic(firstEpicId, "First subtask name", "First subtask description");

        // Создаем вторую подзадачу
        int secondSubTaskId = manager.addSubtaskToEpic(firstEpicId, "Second subtask name", "Second subtask description");

        // Создаем epic c одной подзадачей
        int secondEpicId = manager.createEpic("SecondEpic name", "SecondEpic description");

        // Создаем единственную подзадачу
        int ownSubtaskId = manager.addSubtaskToEpic(secondEpicId, "Own subtask name", "Own subtask description");

        System.out.println("Печать первого Task");
        System.out.println(manager.getTask(firstTaskId));
        System.out.println("Печать второго Task");
        System.out.println(manager.getTask(secondTaskId));
        System.out.println("Печать первого Epic");
        System.out.println(manager.getEpic(firstEpicId));
        System.out.println("Печать второго Epic");
        System.out.println(manager.getEpic(secondEpicId));

        // Получаем список подзадач определенного Эпика
        ArrayList<Subtask> subtasksOfEpic = manager.getEpicSubtasks(firstEpicId);
        System.out.println("Ниже список подзадач первого эпика");
        System.out.println(subtasksOfEpic);

        // Обновляем статус у первого Task
        Task updateTaskStatusFirst = manager.updateStatusTask(firstTaskId, "IN_PROGRESS");
        System.out.println("Печать обновленного статуса первого Task на in_progress");
        System.out.println(updateTaskStatusFirst);

        Task updateTaskStatusSecond = manager.updateStatusTask(firstTaskId, "DONE");
        System.out.println("Печать обновленного статуса первого Task на done");
        System.out.println(updateTaskStatusSecond);

        // Обновляем статус у первой подзадачи первого эпика
        Subtask firstUpdateSubTask = manager.updateStatusSubTask(firstEpicId, firstSubTaskId, "IN_PROGRESS");
        System.out.println("Печать обновленного статуса первой подзадачи первого эпика на in_progress");
        System.out.println(firstUpdateSubTask);

        // Обновляем статус у первого эпика
        Epic updateEpicStatusFirst = manager.updateStatusEpic(firstEpicId);

        // Печать эпика после того как изменили статус у одной из подзадачи
        // теперь его статус не NEW, а IN_PROGRESS
        System.out.println("Печать обновленного статуса первого эпика на in_progress");
        System.out.println(updateEpicStatusFirst);

        // Обновим статус обоих подзадач на DONE
        Subtask secondUpdateSubTask = manager.updateStatusSubTask(firstEpicId, firstSubTaskId, "DONE");
        Subtask thirdUpdateSubTask = manager.updateStatusSubTask(firstEpicId, secondSubTaskId, "DONE");

        // Проверяем статус обоих подзадач
        System.out.println("Печать обновленного статуса обоих подзадач на done");
        System.out.println(secondUpdateSubTask);
        System.out.println(thirdUpdateSubTask);

        // Обновляем статус эпика
        Epic updateEpicStatusSecond = manager.updateStatusEpic(firstEpicId);

        // Проверяем статус эпика, он должен быть DONE
        System.out.println("Печать обновленного статуса первого эпика на done");
        System.out.println(updateEpicStatusSecond);

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
