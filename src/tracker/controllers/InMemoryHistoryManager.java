package tracker.controllers;

import tracker.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final HashMap<Integer, Node> taskHistory = new HashMap<>();
    Node head = null;
    Node tail = null;

    public void linkLast(Task task) {
        Node newHistoryElem = new Node(task);
        int taskId = task.getId();

        if (tail == null) {
            tail = head = newHistoryElem;
        } else {
            tail.next = newHistoryElem;
            newHistoryElem.prev = tail;
            tail = newHistoryElem;
        }
        taskHistory.put(taskId, newHistoryElem);
    }

    private void removeNode(Node nodeToRemove) {
        if (nodeToRemove.prev != null) {
            nodeToRemove.prev.next = nodeToRemove.next;
        } else {
            head = nodeToRemove.next;
        }

        if (nodeToRemove.next != null) {
            nodeToRemove.next.prev = nodeToRemove.prev;
        } else {
            tail = nodeToRemove.prev;
        }
    }


    @Override
    public void addTaskToHistory(Task task) {
        int taskId = task.getId();
        if (taskHistory.containsKey(taskId)) {
            remove(taskId);
        }
        linkLast(task);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node current = head;
        while (current != null) {
            history.add(current.task);
            current = current.next;
        }
        return history;
    }

    @Override
    public void remove(int taskId) {
        Node nodeToRemove = taskHistory.get(taskId);
        if (nodeToRemove != null) {
            removeNode(nodeToRemove);
            taskHistory.remove(taskId);
        }
    }
}