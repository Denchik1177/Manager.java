package main.managers;


public class Managers {

    private Managers() {
        // приватный конструктор, чтобы запретить создание экземпл€ров класса
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistoryManager() {
        return new InMemoryHistoryManager();
    }

}
