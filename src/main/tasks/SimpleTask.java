package main.tasks;

import main.status.StatusEnum;
import main.status.TaskType;

public class SimpleTask extends Task {
    public SimpleTask(String name, String description, StatusEnum status) {
        super(0, TaskType.TASK, name, description, status);
    }

    @Override
    public String toString() {
        return "SimpleTask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }

    public String toCsv() {
        return getId() + "," +
                "TASK," +
                getName() + "," +
                getStatus() + "," +
                getDescription() +
                "\n";
    }

    public static SimpleTask fromCsv(String csvLine) {
        String[] data = csvLine.split(",");
        SimpleTask simpleTask = new SimpleTask(data[2], data[4], StatusEnum.valueOf(data[3]));
        simpleTask.setId(Integer.parseInt(data[0]));
        return simpleTask;
    }
}
