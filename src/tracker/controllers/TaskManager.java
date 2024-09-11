package tracker.controllers;

import tracker.model.Epic;
import tracker.model.Subtask;
import tracker.model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    // Получение списков задач
    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<Subtask> getSubTasks();

    // Удаление всех задач из map
    void deleteListOfTask();

    void deleteListOfEpics();

    // Удаление подзадач у Epic
    void deleteSubtasks();

    // Обновление задачи
    // Я вот тут не понял если честно:-) как именно это протестировать,
    // но я так понял, что это задача на вторую итерацию
    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubTask(Subtask subtask);

    // Обновление статуса задачи
    Epic updateStatusEpic(int id);

    // Получение задачи по идентификатору
    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubTask(int id);

    // Получаем список подзадач определенного Эпика
    ArrayList<Subtask> getEpicSubtasks(int epicId);

    // Создание задачи
    int createTask(String name, String description);

    int createEpic(String name, String description);

    int addSubtaskToEpic(Subtask newSubtask);

    // Удаление по идентификатору
    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id);

    // Отображение последних 10 задач
    List<Task> getHistory();

    @Override
    String toString();
}
