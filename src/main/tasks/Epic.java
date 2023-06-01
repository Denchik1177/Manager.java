package main.tasks;

import main.status.StatusEnum;
import main.status.TaskType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class Epic extends Task {
    private Set<Integer> subtaskIDs;

    public Epic(String name, String description) {
        super(0, TaskType.EPIC, name, description, StatusEnum.NEW);
        subtaskIDs = new HashSet<>();
    }

    public Set<Integer> getSubtaskIDs() {
        return subtaskIDs;
    }

    public void setSubtaskIDs(Set<Integer> subtaskIDs) {
        this.subtaskIDs = subtaskIDs;
    }

    public void clearSubtaskIds() {
        subtaskIDs.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + "'" +
                ", subtaskIDs=" + subtaskIDs +
                ", status='" + getStatus() + "'" +
                '}';
    }

    public String toCsv() {
        return getId() + "," +
                "EPIC," +
                getName() + "," +
                getStatus() + "," +
                getDescription() + "," +
                subtaskIDs.stream().map(Object::toString).collect(Collectors.joining(" ")) +
                "\n";
    }

    public static Epic fromCsv(String csvLine) {
        String[] data = csvLine.split(",");
        Epic epic = new Epic(data[2], data[4]);
        epic.setId(Integer.parseInt(data[0]));
        epic.setStatus(StatusEnum.valueOf(data[3]));
        Set<Integer> subtaskIDs = Arrays.stream(data[5].split(" ")).map(Integer::parseInt).collect(Collectors.toSet());
        epic.setSubtaskIDs(subtaskIDs);
        return epic;
    }

    public void removeSubtask(Integer id) {
        subtaskIDs.remove(id);
    }
}
