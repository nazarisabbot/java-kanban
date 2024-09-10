package tracker.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void tasksAreEqualIfIdIsEqual() {
        Task task1 = new Task(1, "Task1", "Description1");
        Task task2 = new Task(1, "Task2", "Description2");

        assertEquals(task1, task2, "Task-и c одинаковым id должны быть равны");
    }

    @Test
    void taskInheritanceEquality() {
        Epic epic1 = new Epic(1, "Epic1", "Description1");
        Epic epic2 = new Epic(1, "Epic2", "Description3");

        assertEquals(epic1, epic2, "Наследники Task, должны быть равны, если у них есть одинаковый id");
    }
}