package tracker.controllers;

import tracker.model.Epic;
import tracker.model.ProgressStatus;
import tracker.model.Subtask;
import tracker.model.Task;
import tracker.model.TaskType;

import java.io.*;
import java.util.ArrayList;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("id,type,name,status,description,epic\n");

            for (Task task : getTasks()) {
                writer.write(taskToString(task));
            }

            for (Epic task : getEpics()) {
                writer.write(taskToString(task));
            }

            for (Subtask task : getSubTasks()) {
                writer.write(taskToString(task));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ManagerSaveException e) {
            throw new ManagerSaveException("Error saving tasks to file");
        }
    }

    public String taskToString(Task task) {
        String epicId = "";

        if (task instanceof Subtask) {
            epicId = String.valueOf(((Subtask) task).getEpicId());
        }

        return String.format("%d, %s, %s, %s, %s, %s\n",
                task.getId(),
                task.getType(),
                task.getName(),
                task.getStatus(),
                task.getDescription(),
                epicId);
    }

    public void loadFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // Читаем заголовок и пропускаем его
            while ((line = reader.readLine()) != null) {
                Task task = fromString(line);
                switch (task.getType()) {
                    case TASK:
                        updateTask(task);
                        break;
                    case EPIC:
                        updateEpic((Epic) task);
                        break;
                    case SUBTASK:
                        updateSubTask((Subtask) task);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Task fromString(String value) {
        String[] fields = value.split(",");

        int id;
        try {
            id = Integer.parseInt(fields[0].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid Task ID: \"" + fields[0] + "\"", e);
        }

        TaskType type;
        try {
            type = TaskType.valueOf(fields[1].trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid TaskType: \"" + fields[1] + "\"", e);
        }

        String name;
        try {
            name = fields[2].trim();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Name: \"" + fields[2] + "\"", e);
        }

        ProgressStatus status;
        try {
            status = ProgressStatus.valueOf(fields[3].trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid TaskType: \"" + fields[3] + "\"", e);
        }

        String description;
        try {
            description = fields[4].trim();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid description: \"" + fields[4] + "\"", e);
        }

        switch (type) {
            case TASK:
                Task newTask = new Task(id, name, description);
                newTask.setStatus(status);
                return newTask;
            case EPIC:
                Epic newEpic = new Epic(id, name, description);
                newEpic.setStatus(status);
                return newEpic;
            case SUBTASK:
                int epicId = Integer.parseInt(fields[5]);
                Subtask newSubTask = new Subtask(id, name, description, epicId);
                newSubTask.setStatus(status);
                return newSubTask;
            default:
                throw new IllegalArgumentException("Unknown task type");
        }
    }

    // Создание задач
    @Override
    public int createTask(Task newTask) {
        int newId = super.createTask(newTask);
        save();
        return newId;
    }

    @Override
    public int createEpic(Epic newEpic) {
        int newId = super.createEpic(newEpic);
        save();
        return newId;
    }

    @Override
    public int addSubtaskToEpic(Subtask newSubtask) {
        int newId = super.addSubtaskToEpic(newSubtask);
        save();
        return newId;
    }
    // end

    // Удаление всех задач из map
    @Override
    public void deleteListOfTask() {
        super.deleteListOfTask();
        save();
    }

    @Override
    public void deleteListOfEpics() {
        super.deleteListOfEpics();
        save();
    }
    /* end */

    // Удаление подзадач у Epic
    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }
    /* end */

    // Обновление задачи
    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        super.updateSubTask(subtask);
        save();
    }

    // Обновление статуса у Epic
    @Override
    public Epic updateStatusEpic(int id) {
        Epic epic = super.updateStatusEpic(id);
        save();
        return epic;
    }
    /* end */

    // Удаление по идентификатору
    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }
    /* end */

}
