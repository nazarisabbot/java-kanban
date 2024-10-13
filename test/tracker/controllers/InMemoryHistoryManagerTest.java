package tracker.controllers;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracker.model.Epic;
import tracker.model.Subtask;
import tracker.model.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private TaskManager taskManager;
    private HistoryManager historyManager;
    private Task firstTask;
    private Task secondTask;
    private Epic epic;
    private Subtask firstSubTask;
    private Subtask secondSubTask;

    @BeforeEach
    public void setUp() {
        taskManager = Managers.getDefault();
        historyManager = Managers.getDefaultHistory();

        firstTask = new Task(Task.UNASSIGNED_ID, "First name", "First description");
        taskManager.createTask(firstTask);

        secondTask = new Task(Task.UNASSIGNED_ID, "Second name", "Second description");
        taskManager.createTask(secondTask);

        epic = new Epic(Epic.UNASSIGNED_ID, "Epic name", "Epic description");
        int epicId = taskManager.createEpic(epic);

        firstSubTask = new Subtask(Subtask.UNASSIGNED_ID, "SubTask 1 name", "description 1", epicId);
        taskManager.addSubtaskToEpic(firstSubTask);

        secondSubTask = new Subtask(Subtask.UNASSIGNED_ID, "SubTask 2 name", "description 2", epicId);
        taskManager.addSubtaskToEpic(secondSubTask);
    }

    @Test
    void shouldAddTaskToHistory() {
        historyManager.addTaskToHistory(firstTask);
        assertTrue(historyManager.getHistory().contains(firstTask), "История должна содержать первую задачу");
        assertEquals(1, historyManager.getHistory().size(), "Размер истории должен быть равен 1");

        historyManager.addTaskToHistory(secondTask);
        assertTrue(historyManager.getHistory().contains(secondTask), "История должна содержать первую и вторую задачу");
        assertEquals(2, historyManager.getHistory().size(), "Размер истории должен быть равен 2");

        historyManager.addTaskToHistory(firstSubTask);
        assertTrue(historyManager.getHistory().contains(firstSubTask), "История должна содержать первую, вторую и одну подзадачу задачу");
        assertEquals(3, historyManager.getHistory().size(), "Размер истории должен быть равен 3");
    }

    @Test
    void shouldMaintainOrder() {
        historyManager.addTaskToHistory(firstTask);
        historyManager.addTaskToHistory(secondTask);
        historyManager.addTaskToHistory(secondSubTask);

        List<Task> history = historyManager.getHistory();
        assertEquals(3, history.size(), "Размер истории должен быть равен 3");
        assertEquals(firstTask, history.get(0), "firstTask должен быть первым в истории");
        assertEquals(secondTask, history.get(1), "secondTask должен быть вторым в истории");
        assertEquals(secondSubTask, history.get(2), "secondSubTask должен быть третьим в истории");
    }

    @Test
    void shouldNotContainDuplicates() {
        historyManager.addTaskToHistory(firstTask);
        historyManager.addTaskToHistory(secondTask);
        historyManager.addTaskToHistory(secondSubTask);

        historyManager.addTaskToHistory(firstTask);
        historyManager.addTaskToHistory(secondTask);

        List<Task> history = historyManager.getHistory();

        assertEquals(3, history.size(), "Размер истории должен быть равен 3, так как дубликаты не должны учитываться");

        assertTrue(history.contains(firstTask), "История должна содержать первую задачу");
        assertTrue(history.contains(secondTask), "История должна содержать вторую задачу");
        assertTrue(history.contains(secondSubTask), "История должна содержать подзадачу");
    }


    @Test
    void remove() {
        historyManager.addTaskToHistory(firstTask);
        historyManager.addTaskToHistory(secondTask);
        historyManager.addTaskToHistory(secondSubTask);

        List<Task> history = historyManager.getHistory();
        assertEquals(3, history.size(), "Размер истории должен быть равен 3");

        historyManager.remove(secondTask.getId());

        history = historyManager.getHistory(); // Повторно получить обновленный список
        assertEquals(2, history.size(), "Размер истории должен быть равен 2");
        assertFalse(history.contains(secondTask), "История не должна содержать вторую задачу после удаления");
    }
}