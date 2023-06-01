package main.tasks;

import main.status.StatusEnum;
import main.status.TaskType;


public class Task {
    private Integer id;
    private TaskType type;
    private String name;
    private String description;
    private StatusEnum status;

    public Task(Integer id, TaskType type, String name, String description, StatusEnum status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public TaskType getType() {
        return type;
    }


    public void setType(TaskType type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public String toCsv() {
        // Здесь возвращается строка CSV, представляющая ваш класс Task
        return id + "," + type + "," + name + "," + status + "," + description;
    }

    public static Task fromString(String csvLine) {
        // Здесь преобразуется строка CSV обратно в объект Task
        try {
            String[] data = csvLine.split(",");
            Integer id = Integer.parseInt(data[0]);
            TaskType type = TaskType.valueOf(data[1]);
            String name = data[2];
            StatusEnum status = StatusEnum.valueOf(data[3]);
            String description = data[4];

            return new Task(id, type, name, description, status);
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            // Вывод сообщения об ошибке и возврат null или выброс исключения,
            // которое вам нужно обрабатывать в другом месте
            System.err.println("Invalid CSV line: " + csvLine);
            return null;
        }
    }
}
