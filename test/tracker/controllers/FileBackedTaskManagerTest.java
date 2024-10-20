package tracker.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import tracker.model.Epic;
import tracker.model.Subtask;
import tracker.model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    private FileBackedTaskManager manager;
    private File tempFile;

    private Task firstTask;
    private Task secondTask;
    private Epic epic;
    private Subtask firstSubTask;
    private Subtask secondSubTask;

    @BeforeEach
    public void setUp() throws IOException {
        tempFile = File.createTempFile("tasks", ".csv");
        manager = new FileBackedTaskManager(tempFile);
    }

    @Test
    public void testSaveAndLoadEmptyFile() {
        manager.save();
        manager.loadFromFile(tempFile);

        assertTrue(manager.getTasks().isEmpty());
        assertTrue(manager.getEpics().isEmpty());
        assertTrue(manager.getSubTasks().isEmpty());
    }

    @Test
    public void testSaveAndLoadMultipleTasks() throws IOException {
        firstTask = new Task(Task.UNASSIGNED_ID, "First name", "First description");
        manager.createTask(firstTask);

        secondTask = new Task(Task.UNASSIGNED_ID, "Second name", "Second description");
        manager.createTask(secondTask);

        // Вывод содержимого временного файла после записи
        List<String> fileContent = Files.readAllLines(tempFile.toPath());
        System.out.println("Содержимое временного файла после записи:");
        for (String line : fileContent) {
            System.out.println(line);
        }

        FileBackedTaskManager loadedManager = new FileBackedTaskManager(tempFile);
        loadedManager.loadFromFile(tempFile);

        List<Task> tasks = loadedManager.getTasks();

        assertEquals(2, tasks.size());
        assertEquals(firstTask, tasks.get(0));
        assertEquals(secondTask, tasks.get(1));
    }

}