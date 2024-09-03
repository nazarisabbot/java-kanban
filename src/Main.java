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
        int firstTaskId = manager.increaseId();
        Task firstTask = new Task(firstTaskId,"First name", "First description");
        manager.createTask(firstTask);

        // Создаем второй task
        int secondTaskId = manager.increaseId();
        Task secondTask = new Task(secondTaskId,"Second name", "Second description");
        manager.createTask(secondTask);

        // Создаем epic c двумя подзадачами
        int firstEpicId = manager.increaseId();
        Epic firstEpic = new Epic(firstEpicId, "FirstEpic name", "FirstEpic description");
        manager.createEpic(firstEpic);

        // Создаем первую подзадачу
        int firstSubTaskId = manager.increaseId();
        Subtask firstSubtask = new Subtask(firstSubTaskId,"First subtask name", "First subtask description", firstEpicId);
        manager.addSubtaskToEpic(firstEpicId, firstSubtask);

        // Создаем вторую подзадачу
        int secondSubTaskId = manager.increaseId();
        Subtask secondSubtask = new Subtask(secondSubTaskId,"Second subtask name", "Second subtask description", firstEpicId);
        manager.addSubtaskToEpic(firstEpicId, secondSubtask);

        // Создаем epic c одной подзадачей
        int secondEpicId = manager.increaseId();
        Epic secondEpic = new Epic(secondEpicId,"SecondEpic name", "SecondEpic description");
        manager.createEpic(secondEpic);

        // Создаем единственную подзадачу
        int ownSubtaskId = manager.increaseId();
        Subtask ownSubtask = new Subtask(ownSubtaskId,"Own subtask name", "Own subtask description", secondEpicId);
        manager.addSubtaskToEpic(secondEpicId, ownSubtask);

        System.out.println("Печать первого Task");
        System.out.println(firstTask);
        System.out.println("Печать второго Task");
        System.out.println(secondTask);
        System.out.println("Печать первого Epic");
        System.out.println(firstEpic);
        System.out.println("Печать второго Epic");
        System.out.println(secondEpic);

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
