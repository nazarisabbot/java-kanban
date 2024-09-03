package tracker.controllers;

import tracker.model.Epic;
import tracker.model.ProgressStatus;
import tracker.model.Subtask;
import tracker.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int id = 0;

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subTasks = new HashMap<>();

    public int increaseId() {
        return TaskManager.id = TaskManager.id + 1;
    }

    // Получение списков задач
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }
    /* end */

    // Удаление всех задач из map
    public void deleteListOfTask() {
        tasks.clear();
    }

    public void deleteListOfEpics() {
        epics.clear();
        subTasks.clear();
    }
    /* end */

    // Удаление подзадач у Epic
    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds();
            updateStatusEpic(epic.getId());
        }
        subTasks.clear();
    }
    /* end */

    // Обновление задачи
    // Я вот тут не понял если честно :-) как именно это про тестировать
    // но я так понял, что это задача на вторую итерацию
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateSubTask(Subtask subtask) {

        subTasks.put(subtask.getId(), subtask);

        int idOfEpic = subtask.getEpicId();

        Epic epic = epics.get(idOfEpic);

        if (epic != null) {
            ArrayList<Subtask> oldSubtasks = epic.getSubTasks();
            oldSubtasks.add(subtask);
        }

        updateStatusEpic(idOfEpic);
    }
    /* end */

    // Обновление статуса задачи
    public Task updateStatusTask(int id, String newStatus) {
        Task task = tasks.get(id);

        if (task != null) {
            switch (newStatus) {
                case "IN_PROGRESS":
                    task.setStatus(ProgressStatus.IN_PROGRESS);
                    break;
                case "DONE":
                    task.setStatus(ProgressStatus.DONE);
                    break;
                default:
                    task.setStatus(ProgressStatus.NEW);
            }
            return task;
        } else {
            return null;
        }
    }

    public Epic updateStatusEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            ArrayList<Subtask> subtasks = epic.getSubTasks();
            if (subtasks == null) {
                epic.setStatus(ProgressStatus.NEW);
            } else {

                boolean allNew = true;
                boolean allDone = true;

                for (Subtask task : subtasks) {

                    switch (task.getStatus()) {
                        case IN_PROGRESS:
                            allNew = false;
                            allDone = false;
                            break;
                        case DONE:
                            allNew = false;
                            break;
                        case NEW:
                            allDone = false;
                            break;
                    }

                    if (allDone) {
                        epic.setStatus(ProgressStatus.DONE);
                    } else if (allNew) {
                        epic.setStatus(ProgressStatus.NEW);
                    } else {
                        epic.setStatus(ProgressStatus.IN_PROGRESS);
                    }
                }
            }
            return epic;
        } else {
            return null;
        }
    }

    public Subtask updateStatusSubTask(int idEpic, int idSubTask, String newStatus) {
        Epic epic = epics.get(idEpic);

        if (epic == null) {
            return null;
        } else {
            ArrayList<Subtask> subtasks = epic.getSubTasks();
            if (subtasks != null) {
                for (Subtask subtask : subtasks) {
                    if (subtask.getId() == idSubTask) {
                        switch (newStatus) {
                            case "IN_PROGRESS":
                                subtask.setStatus(ProgressStatus.IN_PROGRESS);
                                break;
                            case "DONE":
                                subtask.setStatus(ProgressStatus.DONE);
                                break;
                            default:
                                subtask.setStatus(ProgressStatus.NEW);
                        }
                        return subtask;
                    }
                }
                return null;
            } else {
                return null;
            }
        }
    }
    /* end */

    // Получение задачи по идентификатору
    public Task getTask(int id) {
        if (tasks.get(id) == null) {
            return null;
        }
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        if (epics.get(id) == null) {
            return null;
        }
        return epics.get(id);
    }

    public Subtask getSubTask(int id) {
        if (subTasks.get(id) == null) {
            return null;
        }
        return subTasks.get(id);
    }

    // Получаем список подзадач определенного Эпика
    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        ArrayList<Subtask> tasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        for (int id : epic.getSubtaskIds()) {
            tasks.add(subTasks.get(id));
        }
        return tasks;
    }
    /* end */

    // Создание задачи
    public void createTask(Task newTask) {
        tasks.put(id, newTask);
    }

    public void createEpic(Epic newTask) {
        epics.put(id, newTask);
    }

    public void addSubtaskToEpic(int epicId, Subtask subtask) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.addSubtask(subtask);
            subTasks.put(subtask.getId(), subtask);
        } else {
            System.out.println("Эпик c id " + epicId + " не найден.");
        }
    }
    /* end */

    // Удаление по идентификатору
    public void deleteTask(int id) {
        if (tasks.get(id) == null) {
            System.out.println("Task c id " + id + " не найден!");
        }
        tasks.remove(id);
    }

    public void deleteEpic(int id) {
        final Epic epic = epics.remove(id);
        for (Integer subtaskId : epic.getSubtaskIds()) {
            subTasks.remove(subtaskId);
        }
    }

    public void deleteSubTask(int id) {
        if (subTasks.get(id) == null) {
            System.out.println("Подзадача c id " + id + " не найден!");
        }
        subTasks.remove(id);
    }

    public void deleteSubtask(int id) {
        Subtask subtask = subTasks.remove(id);
        if (subtask == null) {
            return;
        }
        Epic epic = epics.get(subtask.getEpicId());
        epic.removeSubtask(id);
        updateStatusEpic(epic.getId());
    }
    /* end */

    @Override
    public String toString() {
        return "TaskManager{" +
                "tasks=" + tasks +
                ", epics=" + epics +
                ", subTasks=" + subTasks +
                ", id=" + id +
                '}';
    }

    // Пришлось прописать сеттеры, так как редактор ругается :=(
    public void setTasks(HashMap<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    public void setEpics(HashMap<Integer, Epic> epics) {
        this.epics = epics;
    }

    public void setSubTasks(HashMap<Integer, Subtask> subTasks) {
        this.subTasks = subTasks;
    }
}
