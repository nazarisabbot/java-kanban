import java.util.ArrayList;

public class Epic {
    private final int id;
    private final ArrayList<Subtask> subTasks = new ArrayList<>();

    public Epic(int id) {
        this.id = id;
    }

    public int getId(int id) {
        return this.id;
    }

    public ArrayList<Subtask> getArrayOfSubtask() {
     return subTasks;
    }
}
