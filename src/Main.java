import tracker.controllers.Managers;
import tracker.controllers.TaskManager;
import tracker.model.Epic;
import tracker.model.Subtask;
import tracker.model.Task;
import tracker.controllers.InMemoryTaskManager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Создаем manager
        TaskManager manager = Managers.getDefault();

        // Создаем первый task
        int firstTaskId = manager.createTask("First name", "First description");
        manager.getTask(firstTaskId);
        printAllTasks(manager);

        // Создаем второй task
        int secondTaskId = manager.createTask("Second name", "Second description");
        manager.getTask(secondTaskId);
        printAllTasks(manager);

        // Создаем epic c двумя подзадачами
        int firstEpicId = manager.createEpic("FirstEpic name", "FirstEpic description");
        manager.getEpic(firstEpicId);
        printAllTasks(manager);

        // Создаем первую подзадачу
        int firstSubTaskId = manager.addSubtaskToEpic(firstEpicId, "First subtask name", "First subtask description");
        manager.getSubTask(firstSubTaskId);
        printAllTasks(manager);

        // Создаем вторую подзадачу
        int secondSubTaskId = manager.addSubtaskToEpic(firstEpicId, "Second subtask name", "Second subtask description");
        manager.getSubTask(secondSubTaskId);
        printAllTasks(manager);

        // Создаем epic c одной подзадачей
        int secondEpicId = manager.createEpic("SecondEpic name", "SecondEpic description");
        manager.getEpic(secondEpicId);
        printAllTasks(manager);

        // Создаем единственную подзадачу
        int ownSubtaskId = manager.addSubtaskToEpic(secondEpicId, "Own subtask name", "Own subtask description");
        manager.getSubTask(ownSubtaskId);
        printAllTasks(manager);

        // Создаем восьмую задачу для истории
        int eighthTaskId = manager.createTask("First name", "First description");
        manager.getTask(eighthTaskId);
        printAllTasks(manager);

        // Создаем девятую задачу для истории
        int ninthTaskId = manager.createTask("First name", "First description");
        manager.getTask(ninthTaskId);
        printAllTasks(manager);

        // Создаем десятую задачу для истории
        int tenthTaskId = manager.createTask("First name", "First description");
        manager.getTask(tenthTaskId);
        printAllTasks(manager);

        // Пограничный случай, по идеи задача с id 1 должна уйти из истории,
        // а последней просмотренной задачей должен стать Task c id 11
        int eleventhTaskId = manager.createTask("First name", "First description");
        manager.getTask(eleventhTaskId);
        printAllTasks(manager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getEpics()) {
            System.out.println(epic);

            for (Task task : manager.getEpicSubtasks(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubTasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
