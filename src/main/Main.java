package main;



import main.managers.InMemoryTaskManager;
import main.managers.TaskManager;
import main.status.StatusEnum;
import main.tasks.Epic;
import main.tasks.SimpleTask;
import main.tasks.Subtask;


public class Main {
    public static void main(String[] args) {
        // Создание менеджера задач и истории
        TaskManager taskManager = new InMemoryTaskManager();

        // Создание двух простых задач
        SimpleTask task1 = new SimpleTask("Задача 1", "Описание задачи 1", StatusEnum.NEW);
        SimpleTask task2 = new SimpleTask("Задача 2", "Описание задачи 2", StatusEnum.IN_PROGRESS);

        // Добавление задач в менеджер
        Integer task1Id = taskManager.addSimpleTask(task1);
        Integer task2Id = taskManager.addSimpleTask(task2);

        // Создание эпика с тремя подзадачами
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Integer epic1Id = taskManager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", StatusEnum.NEW, epic1Id);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", StatusEnum.IN_PROGRESS, epic1Id);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", StatusEnum.DONE, epic1Id);

        Integer subtask1Id = taskManager.addSubtask(subtask1);
        Integer subtask2Id = taskManager.addSubtask(subtask2);
        Integer subtask3Id = taskManager.addSubtask(subtask3);

        // Создание эпика без подзадач
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        Integer epic2Id = taskManager.addEpic(epic2);

        // Вывод созданных задач и эпиков
        System.out.println("Список простых задач:");
        System.out.println(taskManager.getListSimpleTask());
        System.out.println("Список подзадач:");
        System.out.println(taskManager.getListSubtask());
        System.out.println("Список эпиков:");
        System.out.println(taskManager.getListEpic());

        // Запрос созданных задач несколько раз в разном порядке
        System.out.println("Запросы созданных задач:");
        System.out.println(taskManager.getTaskByID(task2Id));
        System.out.println(taskManager.getSubtaskByID(subtask1Id));
        System.out.println(taskManager.getEpicByID(epic1Id));
        System.out.println(taskManager.getEpicByID(epic2Id));
        System.out.println(taskManager.getSubtaskByID(subtask3Id));
        System.out.println(taskManager.getTaskByID(task1Id));
        System.out.println(taskManager.getSubtaskByID(subtask2Id));

        // Вывод истории после каждого запроса
        System.out.println("История после каждого запроса:");
        System.out.println(taskManager.getHistory());

        // Удаление задачи, которая есть в истории
        System.out.println("Удаление задачи из истории:");
        taskManager.removeSimpleTask(task1Id);
        System.out.println(taskManager.getHistory());
        // Удаление эпика с подзадачами
        System.out.println("Удаление эпика с подзадачами:");
        taskManager.removeEpic(epic1Id);
        System.out.println("Список простых задач после удаления эпика:");
        System.out.println(taskManager.getListSimpleTask());
        System.out.println("Список подзадач после удаления эпика:");
        System.out.println(taskManager.getListSubtask());
        System.out.println("История после удаления эпика:");
        System.out.println(taskManager.getHistory());
        // Вывод истории после каждого запроса
        System.out.println("История после каждого запроса:");
        System.out.println(taskManager.getHistory());


        // Запрос задачи, которая уже есть в истории
        System.out.println("Запрос задачи, которая уже есть в истории:");
        System.out.println(taskManager.getTaskByID(task2Id));

        // Вывод истории после запроса задачи, которая уже есть в истории
        System.out.println("История после запроса задачи, которая уже есть в истории:");
        System.out.println(taskManager.getHistory());
        System.out.println("Удалим всех задач: ");
        taskManager.removeAll();
        System.out.println("История после удаления всех задач: ");
        System.out.println(taskManager.getHistory());
    }
}