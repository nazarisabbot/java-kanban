package tracker.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracker.model.Epic;
import tracker.model.ProgressStatus;
import tracker.model.Subtask;
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
        Task newTask = new Task(Task.UNASSIGNED_ID, "Test", "Test description");
        final int taskId = taskManager.createTask(newTask);
        final Task task = taskManager.getTask(taskId);
        assertNotNull(task, "Task не найден");

        Epic newEpic = new Epic(Epic.UNASSIGNED_ID, "Test", "Test description");
        final int epicId = taskManager.createEpic(newEpic);
        final Task epic = taskManager.getEpic(epicId);
        assertNotNull(epic, "Epic не найден");
    }

    @Test
    void historyMaintainsPastVersions() {
        Task newTask = new Task(Task.UNASSIGNED_ID, "Task", "Description");
        int taskId = taskManager.createTask(newTask);
        taskManager.getTask(taskId);

        List<Task> historyTasks = historyManager.getHistory();

        assertNotNull(historyTasks, "Массив с историей пуст");
    }

    @Test
    void deleteSubtaskShouldRemoveSubtaskAndUpdateEpic() {
        // Создание эпика с подзадачей
        Epic newEpic = new Epic(Epic.UNASSIGNED_ID, "Test", "Test that id not exist");
        final int epicId = taskManager.createEpic(newEpic);
        Subtask firstSubTask = new Subtask(Subtask.UNASSIGNED_ID, "First subTask", "Description", epicId);
        taskManager.addSubtaskToEpic(firstSubTask);

        // Проверка что эпик и подзадача существуют
        assertNotNull(taskManager.getEpic(epicId), "Эпик не создался!");
        assertNotNull(firstSubTask, "Подзадача не создалась!");

        // Проверка стартового статуса эпика
        ProgressStatus status = taskManager.getEpic(epicId).getStatus();
        assertEquals(ProgressStatus.NEW, status, "Статус эпика должен быть NEW");

        // Проверка обновления статуса эпика, после обновление статуса подзадачи
        firstSubTask.setStatus(ProgressStatus.IN_PROGRESS);
        taskManager.updateStatusEpic(epicId);
        ProgressStatus newStatus = taskManager.getEpic(epicId).getStatus();
        assertEquals(ProgressStatus.IN_PROGRESS, newStatus, "Статус эпика должен быть IN_PROGRESS");

        // Удаление подзадачи
        taskManager.deleteSubtask(firstSubTask.getId());

        // Проверка, что подзадача действительно удалена
        assertNull(taskManager.getSubTask(firstSubTask.getId()), "Подзадача не была удалена!");

        // Обновленный статус эпика после удаления подзадачи
        taskManager.updateStatusEpic(epicId);
        ProgressStatus updatedStatus = taskManager.getEpic(epicId).getStatus();
        assertEquals(ProgressStatus.NEW, updatedStatus, "Статус эпика после удаления подзадачи должен быть NEW");

        // Проверка, что идентификатор подзадачи удален из списка подзадач эпика
        Epic epic = taskManager.getEpic(epicId);
        assertFalse(epic.getSubtaskIds().contains(firstSubTask.getId()), "Эпик все еще содержит идентификатор удаленной подзадачи!");
    }
}