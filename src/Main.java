import tracker.controllers.TaskManager;
import tracker.controllers.Managers;
import tracker.model.Subtask;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        // Создаем две задачи
        int firstTaskId = manager.createTask("First Task", "Description of First Task");
        int secondTaskId = manager.createTask("Second Task", "Description of Second Task");

        // Создаем эпик с тремя подзадачами
        int firstEpicId = manager.createEpic("First Epic", "Description of First Epic");

        Subtask firstSubTask = new Subtask(Subtask.UNASSIGNED_ID, "Subtask 1 of First Epic", "Description of Subtask 1", firstEpicId);
        int subtask1Id = manager.addSubtaskToEpic(firstSubTask);

        Subtask secondSubTask = new Subtask(Subtask.UNASSIGNED_ID, "Subtask 2 of First Epic", "Description of Subtask 2", firstEpicId);
        int subtask2Id = manager.addSubtaskToEpic(secondSubTask);

        Subtask thirdSubTask = new Subtask(Subtask.UNASSIGNED_ID, "Subtask 3 of First Epic", "Description of Subtask 3", firstEpicId);
        int subtask3Id = manager.addSubtaskToEpic(thirdSubTask);

        // Создаем эпик без подзадач
        int secondEpicId = manager.createEpic("Second Epic", "Description of Second Epic");

        // Запрашиваем задачи несколько раз в разном порядке и добавляем их в историю
        manager.getTask(firstTaskId);
        manager.getEpic(secondEpicId);
        manager.getSubTask(subtask1Id);
        manager.getSubTask(subtask2Id);
        manager.getTask(secondTaskId);
        manager.getSubTask(subtask3Id);

        // Выводим историю и проверяем, что нет повторов
        System.out.println("История после добавления задач:");
        manager.getHistory().forEach(System.out::println);

        // Удаляем задачу, которая есть в истории, и снова выводим историю
        manager.deleteTask(firstTaskId);
        System.out.println("\nИстория после удаления Task 1:");
        manager.getHistory().forEach(System.out::println);

        // Удаляем эпик с тремя подзадачами
        manager.deleteEpic(firstEpicId);
        System.out.println("\nИстория после удаления Epic 1 и его подзадач:");
        manager.getHistory().forEach(System.out::println);
    }
}