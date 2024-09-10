package tracker.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracker.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private InMemoryTaskManager taskManager;
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void addNewTask() {
        final int taskId = taskManager.createTask("Test", "Test description");
        final Task task = taskManager.getTask(taskId);
        assertNotNull(task, "Task не найден");

        final int epicId = taskManager.createEpic("Test", "Test description");
        final Task epic = taskManager.getEpic(epicId);
        assertNotNull(epic, "Epic не найден");
    }

    @Test
    void historyMaintainsPastVersions() {
        int taskId = taskManager.createTask("Task", "Description");
        taskManager.getTask(taskId);

        List<Task> historyTasks = historyManager.getHistory();

        assertNotNull(historyTasks, "Массив с историей пуст");
    }
}