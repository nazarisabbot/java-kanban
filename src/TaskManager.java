import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    static int id = 0;

    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subTasks = new HashMap<>();

    // Получение списков задач
    public String getListOfTask() {
        return tasks.toString();
    }

    public String getListOfEpics() {
        return epics.toString();
    }

    public String getListOfSubTasks() {
        return subTasks.toString();
    }
    /* end */

    // Удаление всех задач из map
    public void deleteListOfTask() {
        tasks.clear();
    }

    public void deleteListOfEpics() {
        epics.clear();
    }

    public void deleteListOfSubTasks() {
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
    }
    /* end */

    // Обновление статуса задачи
    public void updateStatusTask(int id, String newStatus) {
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

        } else {
            System.out.println("Task c id " + id + " не найден!");
        }
    }

    public void updateStatusEpic(int id) {
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
        } else {
            System.out.println("Epic c id " + id + " не найден!");
        }
    }

    public void updateStatusSubTask(int idEpic, int idSubTask, String newStatus) {
        Epic epic = epics.get(idEpic);

        if (epic == null) {
            System.out.println("Эпик с id " + idEpic + " не найден!");
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
                    }
                }
            }
        }
    }
    /* end */

    // Получение задачи по идентификатору
    public String getTask(int id) {
        if (tasks.get(id) == null) {
            return "Task c id " + id + " не найден!";
        }
        return tasks.get(id).toString();
    }

    public String getEpic(int id) {
        if (epics.get(id) == null) {
            return "Epic c id " + id + " не найден!";
        }
        return epics.get(id).toString();
    }

    public String getSubTask(int id) {
        if (subTasks.get(id) == null) {
            return "Подзадача c id " + id + " не найдена!";
        }
        return subTasks.get(id).toString();
    }
    /* end */

    // Создание задачи
    public void createTask(Task newTask) {
        id++;
        newTask.setId(id);
        tasks.put(id, newTask);
    }

    public void createEpic(Epic newTask) {
        id++;
        newTask.setId(id);
        epics.put(id, newTask);
    }

    public void addSubtaskToEpic(int epicId, Subtask subtask) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            id++;
            subtask.setId(id);
            subTasks.put(id, subtask);
            epic.addSubtask(subtask);
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
        if (epics.get(id) == null) {
            System.out.println("Epic c id " + id + " не найден!");
        }
        epics.remove(id);
    }

    public void deleteSubTask(int id) {
        if (subTasks.get(id) == null) {
            System.out.println("Подзадача c id " + id + " не найден!");
        }
        subTasks.remove(id);
    }

    @Override
    public String toString() {
        return "TaskManager{" +
                "tasks=" + tasks +
                ", epics=" + epics +
                ", subTasks=" + subTasks +
                ", id=" + id +
                '}';
    }
    /* end */
}
