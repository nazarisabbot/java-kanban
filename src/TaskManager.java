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

    // Получение задачи по идентификатору
    public String getTask(int id) {
        return tasks.toString();
    }

    public String getEpic(int id) {
        return epics.toString();
    }

    public String getSubTask(int id) {
        return subTasks.toString();
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
        tasks.remove(id);
    }

    public void deleteEpic(int id) {
        epics.remove(id);
    }

    public void deleteSubTask(int id) {
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

    // Получение списка всех подзадач определённого эпика.
    /* public ArrayList<Subtask> getListOfSubtask(int id) {
        return epics.getArrayOfSubtask();
    } */
    /* end */
}
