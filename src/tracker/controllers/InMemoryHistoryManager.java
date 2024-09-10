package tracker.controllers;

import tracker.model.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> taskHistory = new ArrayList<>(10);

    @Override
    public void addTaskToHistory(Task task) {
        if (taskHistory.contains(task)) {
            taskHistory.remove(task);
        }
        taskHistory.add(task);

        if (taskHistory.size() > 10) {
            taskHistory.removeFirst();
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(taskHistory);
    }
}