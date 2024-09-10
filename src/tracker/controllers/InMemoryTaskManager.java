package tracker.controllers;

import tracker.model.Epic;
import tracker.model.ProgressStatus;
import tracker.model.Subtask;
import tracker.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    static int id = 0;
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subTasks = new HashMap<>();
    private final List<Task> taskHistory = new ArrayList<>(10) {
    };

    // Получение списков задач
    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }
    /* end */

    // Удаление всех задач из map
    @Override
    public void deleteListOfTask() {
        tasks.clear();
    }

    @Override
    public void deleteListOfEpics() {
        epics.clear();
        subTasks.clear();
    }
    /* end */

    // Удаление подзадач у Epic
    @Override
    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds();
            updateStatusEpic(epic.getId());
        }
        subTasks.clear();
    }
    /* end */

    // Обновление задачи
    // Я вот тут не понял если честно:-) как именно это протестировать,
    // но я так понял, что это задача на вторую итерацию
    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
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
    @Override
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

    @Override
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

    @Override
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
    @Override
    public Task getTask(int id) {
        if (tasks.get(id) != null) {
            historyManager.addTaskToHistory(tasks.get(id));
        }
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        if (epics.get(id) != null) {
            historyManager.addTaskToHistory(epics.get(id));
        }
        return epics.get(id);
    }

    @Override
    public Subtask getSubTask(int id) {
        if (subTasks.get(id) != null) {
            historyManager.addTaskToHistory(subTasks.get(id));
        }
        return subTasks.get(id);
    }

    // Получаем список подзадач определенного Эпика
    @Override
    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        ArrayList<Subtask> tasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return tasks;
        }
        for (Integer id : epic.getSubtaskIds()) {
            Subtask subtask = subTasks.get(id);
            if (subtask != null) {
                tasks.add(subtask);
            }
        }
        return tasks;
    }
    /* end */

    // Создание задачи
    @Override
    public int createTask(String name, String description) {
        int newId = increaseId();
        Task newTask = new Task(newId, name, description);
        tasks.put(id, newTask);
        return newId;
    }

    @Override
    public int createEpic(String name, String description) {
        int newId = increaseId();
        Epic newEpic = new Epic(newId, name, description);
        epics.put(id, newEpic);
        return newId;
    }

    @Override
    public int addSubtaskToEpic(int epicId, String name, String description) {
        int newId = increaseId();
        Subtask newSubtask = new Subtask(newId, name, description, epicId);
        subTasks.put(newId, newSubtask);

        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.addSubtask(newSubtask);
        } else {
            System.out.println("Эпик c id " + epicId + " не найден.");
        }
        return newId; // Возвращаем id новой подзадачи
    }
    /* end */

    // Удаление по идентификатору
    @Override
    public void deleteTask(int id) {
        if (tasks.get(id) == null) {
            System.out.println("Task c id " + id + " не найден!");
        }
        tasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        final Epic epic = epics.remove(id);
        for (Integer subtaskId : epic.getSubtaskIds()) {
            subTasks.remove(subtaskId);
        }
    }

    @Override
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

    // Отображение последних 10 задач
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
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
}
