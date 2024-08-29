import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Subtask> subTasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Subtask> getSubTasks() {
        return subTasks;
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

    public void addSubtask(Subtask subtask) {
        subTasks.add(subtask);
    }
}
