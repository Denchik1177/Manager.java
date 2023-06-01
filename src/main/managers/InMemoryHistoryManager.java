package main.managers;

import main.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private CustomLinkedList<Task> historyList = new CustomLinkedList<>();
    private HashMap<Integer, CustomLinkedList.Node<Task>> nodeMap = new HashMap<>();

    @Override
    public void add(Task task) {
        CustomLinkedList.Node<Task> node = nodeMap.get(task.getId());

        if (node != null) {
            historyList.removeNode(node);
        }
        CustomLinkedList.Node<Task> newNode = historyList.linkLast(task);
        nodeMap.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        CustomLinkedList.Node<Task> node = nodeMap.get(id);
        if (node != null) {
            historyList.removeNode(node);
            nodeMap.remove(id);
        }
    }

    @Override
    public void clearHistory() {
        historyList = new CustomLinkedList<>();
        nodeMap.clear();
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        historyList.getTasks(history);
        return history;
    }

    private static class CustomLinkedList<T> {
        private Node<T> head;
        private Node<T> tail;
        private int size;

        private Node<T> linkLast(T data) {
            Node<T> newNode = new Node<>(data);
            if (tail == null) {
                head = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
            }
            tail = newNode;
            size++;
            return newNode;
        }

        private void removeNode(Node<T> node) {
            if (node == null) {
                return;
            }
            Node<T> prevNode = node.prev;
            Node<T> nextNode = node.next;
            if (prevNode == null) {
                head = nextNode;
            } else {
                prevNode.next = nextNode;
                node.prev = null;
            }
            if (nextNode == null) {
                tail = prevNode;
            } else {
                nextNode.prev = prevNode;
                node.next = null;
            }
            size--;
        }

        private void getTasks(List<T> taskList) {
            Node<T> currentNode = head;
            while (currentNode != null) {
                taskList.add(currentNode.data);
                currentNode = currentNode.next;
            }
        }

        private int size() {
            return size;
        }

        private static class Node<T> {
            private T data;
            private Node<T> prev;
            private Node<T> next;

            private Node(T data) {
                this.data = data;
            }

            private T getData() {
                return data;
            }
        }
    }
}