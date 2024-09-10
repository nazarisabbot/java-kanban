package tracker.controllers;

import tracker.model.Task;

import java.util.List;

public interface HistoryManager {
    void addTaskToHistory(Task task);
    List<Task> getHistory();
}
