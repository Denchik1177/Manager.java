package main.tasks;

import main.status.StatusEnum;
import main.status.TaskType;

public class Subtask extends Task {
    private Integer parentEpicID;

    public Subtask(String name, String description, StatusEnum status, Integer parentEpicID) {
        super(0, TaskType.SUBTASK, name, description, status);
        this.parentEpicID = parentEpicID;
    }

    public Integer getParentEpicID() {
        return parentEpicID;
    }

    public void setParentEpicID(Integer parentEpicID) {
        this.parentEpicID = parentEpicID;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", parentEpicID=" + parentEpicID +
                '}';
    }

    public String toCsv() {
        return getId() + "," +
                "SUBTASK," +
                getName() + "," +
                getStatus() + "," +
                getDescription() + "," +
                getParentEpicID() +
                "\n";
    }

    public static Subtask fromCsv(String csvLine) {
        String[] data = csvLine.split(",");
        Subtask subtask = new Subtask(data[2], data[4], StatusEnum.valueOf(data[3]), Integer.parseInt(data[5]));
        subtask.setId(Integer.parseInt(data[0]));
        return subtask;
    }
}
