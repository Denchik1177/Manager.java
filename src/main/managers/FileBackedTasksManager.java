package main.managers;

import main.exceptions.ManagerSaveException;
import main.status.StatusEnum;
import main.tasks.Epic;
import main.tasks.SimpleTask;
import main.tasks.Subtask;
import main.tasks.Task;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }
    public void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            // ��������� ������ � CSV

            for (Task task : this.getHistory()) {
                writer.write(task.toCsv());
            }// ��������� ������� ��������� � ��������� ������
            String historyString = historyToString(historyManager);
            writer.write(historyString);
            writer.close();
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage(), e);
        }
    }

    @Override
    public Integer addSimpleTask(SimpleTask task) {
        Integer id = super.addSimpleTask(task);
        save();
        return id;
    }

    @Override
    public Integer addSubtask(Subtask task) {
        Integer id = super.addSubtask(task);
        save();
        return id;
    }

    @Override
    public Integer addEpic(Epic epic) {
        Integer id = super.addEpic(epic);
        save();
        return id;
    }

    @Override
    public void updateSimpleTask(SimpleTask newSimpleTask) {
        super.updateSimpleTask(newSimpleTask);
        save();
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        super.updateSubtask(newSubtask);
        save();
    }

    @Override
    public void updateEpic(Epic newEpic) {
        super.updateEpic(newEpic);
        save();
    }

    @Override
    public void removeSimpleTask(Integer id) {
        super.removeSimpleTask(id);
        save();
    }

    @Override
    public void removeSubtask(Integer id) {
        super.removeSubtask(id);
        save();
    }

    @Override
    public void removeEpic(Integer id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public void removeAllSimpleTasks() {
        super.removeAllSimpleTasks();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAll() {
        super.removeAll();
        save();
    }
    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            if (!lines.isEmpty()) {
                for (int i = 0; i < lines.size() - 1; i++) {
                    String line = lines.get(i);
                    String[] data = line.split(",");
                    switch (data[1]) {
                        case "TASK":
                            manager.addSimpleTask(SimpleTask.fromCsv(line));
                            break;
                        case "SUBTASK":
                            manager.addSubtask(Subtask.fromCsv(line));
                            break;
                        case "EPIC":
                            manager.addEpic(Epic.fromCsv(line));
                            break;
                    }
                }

                // �������������� ������� ���������
                String historyString = lines.get(lines.size() - 1); // �������� ������ � ��������
                List<Integer> history = historyFromString(historyString); // ����������� ������ � ������ ���������������
                for (Integer taskId : history) {
                    Task task = manager.getTaskByID(taskId);
                    if (task != null) {
                        manager.historyManager.add(task); // ��������� ������ � ������� ���������
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage(), e);
        }

        return manager;
    }
    public static String historyToString(HistoryManager manager) {
        List<Task> history = manager.getHistory();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < history.size(); i++) {
            builder.append(history.get(i));

            // ��������� ������� ����� ������� ��������, ����� ����������
            if (i < history.size() - 1) {
                builder.append(",");
            }
        }

        return builder.toString();
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> history = new ArrayList<>();

        // ��������� ������ �� ������ ���������, ����������� ��������
        String[] parts = value.split(",");

        for (String part : parts) {
            // ������ ������� ������������ ������� � Integer � ��������� � ������
            history.add(Integer.parseInt(part));
        }

        return history;
    }
    public static void main(String[] args) {
        File file = new File("data/tasks.csv");
        FileBackedTasksManager manager = new FileBackedTasksManager(file);

        // ��������� ������, ����� � ���������
        SimpleTask task1 = new SimpleTask( "Task 1", "Description 1", StatusEnum.NEW);
        SimpleTask task2 = new SimpleTask( "Task 2", "Description 2", StatusEnum.IN_PROGRESS);
        Epic epic1 = new Epic( "Epic 1", "Description 3");
        Subtask subtask1 = new Subtask( "Subtask 1", "Description 4", StatusEnum.DONE, 3);
        Subtask subtask2 = new Subtask( "Subtask 2", "Description 5", StatusEnum.NEW, 3);

        manager.addSimpleTask(task1);
        manager.addSimpleTask(task2);
        manager.addEpic(epic1);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        // ��������� ������ � ����
        manager.save();

        // ��������� ������ �� �����
        FileBackedTasksManager newManager = FileBackedTasksManager.loadFromFile(file);

        // ���������, ��� ������, �����, ��������� � ������� ��������� ���� ������������� ���������
        List<SimpleTask> restoredTasks = newManager.getListSimpleTask();
        List<Epic> restoredEpics = newManager.getListEpic();
        List<Subtask> restoredSubtasks = newManager.getListSubtask();
        List<Task> restoredHistory = newManager.getHistory();

        System.out.println("Restored Tasks:");
        for (SimpleTask task : restoredTasks) {
            System.out.println(task);
        }

        System.out.println("Restored Epics:");
        for (Epic epic : restoredEpics) {
            System.out.println(epic);
        }

        System.out.println("Restored Subtasks:");
        for (Subtask subtask : restoredSubtasks) {
            System.out.println(subtask);
        }

        System.out.println("Restored History:");
        for (Task task : restoredHistory) {
            System.out.println(task);
        }
    }

}