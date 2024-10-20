package tracker.model;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Subtask> subTasks = new ArrayList<>();

    public Epic(int id, String name, String description) {
        super(id, name, description);
    }

    public ArrayList<Subtask> getSubTasks() {
        return subTasks;
    }

    public void addSubtask(Subtask subtask) {
        subTasks.add(subtask);
    }

    public void cleanSubtaskIds() {
        subTasks.clear();
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    public ArrayList<Integer> getSubtaskIds() {
        ArrayList<Integer> listOfIds = new ArrayList<>();
        for (Subtask task : subTasks) {
            listOfIds.add(task.getId());
        }
        return  listOfIds;
    }

    public void removeSubtask(int id) {
        for (int i = 0; i < subTasks.size(); i++) {
            if (subTasks.get(i).getId() == id) {
                subTasks.remove(i);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                ", subTasks=" + subTasks +
                '}';
    }
}
