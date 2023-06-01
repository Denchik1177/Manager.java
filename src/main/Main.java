package main;



import main.managers.InMemoryTaskManager;
import main.managers.TaskManager;
import main.status.StatusEnum;
import main.tasks.Epic;
import main.tasks.SimpleTask;
import main.tasks.Subtask;


public class Main {
    public static void main(String[] args) {
        // �������� ��������� ����� � �������
        TaskManager taskManager = new InMemoryTaskManager();

        // �������� ���� ������� �����
        SimpleTask task1 = new SimpleTask("������ 1", "�������� ������ 1", StatusEnum.NEW);
        SimpleTask task2 = new SimpleTask("������ 2", "�������� ������ 2", StatusEnum.IN_PROGRESS);

        // ���������� ����� � ��������
        Integer task1Id = taskManager.addSimpleTask(task1);
        Integer task2Id = taskManager.addSimpleTask(task2);

        // �������� ����� � ����� �����������
        Epic epic1 = new Epic("���� 1", "�������� ����� 1");
        Integer epic1Id = taskManager.addEpic(epic1);

        Subtask subtask1 = new Subtask("��������� 1", "�������� ��������� 1", StatusEnum.NEW, epic1Id);
        Subtask subtask2 = new Subtask("��������� 2", "�������� ��������� 2", StatusEnum.IN_PROGRESS, epic1Id);
        Subtask subtask3 = new Subtask("��������� 3", "�������� ��������� 3", StatusEnum.DONE, epic1Id);

        Integer subtask1Id = taskManager.addSubtask(subtask1);
        Integer subtask2Id = taskManager.addSubtask(subtask2);
        Integer subtask3Id = taskManager.addSubtask(subtask3);

        // �������� ����� ��� ��������
        Epic epic2 = new Epic("���� 2", "�������� ����� 2");
        Integer epic2Id = taskManager.addEpic(epic2);

        // ����� ��������� ����� � ������
        System.out.println("������ ������� �����:");
        System.out.println(taskManager.getListSimpleTask());
        System.out.println("������ ��������:");
        System.out.println(taskManager.getListSubtask());
        System.out.println("������ ������:");
        System.out.println(taskManager.getListEpic());

        // ������ ��������� ����� ��������� ��� � ������ �������
        System.out.println("������� ��������� �����:");
        System.out.println(taskManager.getTaskByID(task2Id));
        System.out.println(taskManager.getSubtaskByID(subtask1Id));
        System.out.println(taskManager.getEpicByID(epic1Id));
        System.out.println(taskManager.getEpicByID(epic2Id));
        System.out.println(taskManager.getSubtaskByID(subtask3Id));
        System.out.println(taskManager.getTaskByID(task1Id));
        System.out.println(taskManager.getSubtaskByID(subtask2Id));

        // ����� ������� ����� ������� �������
        System.out.println("������� ����� ������� �������:");
        System.out.println(taskManager.getHistory());

        // �������� ������, ������� ���� � �������
        System.out.println("�������� ������ �� �������:");
        taskManager.removeSimpleTask(task1Id);
        System.out.println(taskManager.getHistory());
        // �������� ����� � �����������
        System.out.println("�������� ����� � �����������:");
        taskManager.removeEpic(epic1Id);
        System.out.println("������ ������� ����� ����� �������� �����:");
        System.out.println(taskManager.getListSimpleTask());
        System.out.println("������ �������� ����� �������� �����:");
        System.out.println(taskManager.getListSubtask());
        System.out.println("������� ����� �������� �����:");
        System.out.println(taskManager.getHistory());
        // ����� ������� ����� ������� �������
        System.out.println("������� ����� ������� �������:");
        System.out.println(taskManager.getHistory());


        // ������ ������, ������� ��� ���� � �������
        System.out.println("������ ������, ������� ��� ���� � �������:");
        System.out.println(taskManager.getTaskByID(task2Id));

        // ����� ������� ����� ������� ������, ������� ��� ���� � �������
        System.out.println("������� ����� ������� ������, ������� ��� ���� � �������:");
        System.out.println(taskManager.getHistory());
        System.out.println("������ ���� �����: ");
        taskManager.removeAll();
        System.out.println("������� ����� �������� ���� �����: ");
        System.out.println(taskManager.getHistory());
    }
}