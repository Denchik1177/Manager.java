package main.managers;

import main.tasks.Epic;
import main.tasks.SimpleTask;
import main.tasks.Subtask;
import main.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.status.StatusEnum.*;

public class InMemoryTaskManager implements TaskManager {
    private Integer nextID;
    private final Map<Integer, SimpleTask> simpleTasks;
    private final Map<Integer, Subtask> subtasks;
    private final Map<Integer, Epic> epics;
    protected final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.nextID = 1;
        this.simpleTasks = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.historyManager = Managers.getDefaultHistoryManager();
    }


    private void updateEpicStatus(Integer epicID) {

        Integer countOfNEW = 0;
        Integer countOfDONE = 0;
        Integer countOfSubtask = epics.get(epicID).getSubtaskIDs().size();

        for (Integer subtaskID : epics.get(epicID).getSubtaskIDs()) {
            if (subtasks.get(subtaskID).getStatus() == IN_PROGRESS) {
                Epic epic = epics.get(epicID);
                epic.setStatus(IN_PROGRESS);
                return;
            } else if (subtasks.get(subtaskID).getStatus() == NEW) {
                countOfNEW++;
            } else if (subtasks.get(subtaskID).getStatus() == DONE) {
                countOfDONE++;
            }
        }

        if (countOfNEW.equals(countOfSubtask) || (countOfSubtask == 0)) {
            Epic epic = epics.get(epicID);
            epic.setStatus(NEW);
        } else if (countOfDONE.equals(countOfSubtask)) {
            Epic epic = epics.get(epicID);
            epic.setStatus(DONE);
        }
    }

    @Override
    public List<SimpleTask> getListSimpleTask() {
        List<SimpleTask> list = new ArrayList<>(simpleTasks.values());
        return list;
    }

    @Override
    public List<Subtask> getListSubtask() {
        List<Subtask> list = new ArrayList<>(subtasks.values());
        return list;
    }

    @Override
    public List<Epic> getListEpic() {
        List<Epic> list = new ArrayList<>(epics.values());
        return list;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public Integer addSimpleTask(SimpleTask task) {
        task.setId(nextID++);

        simpleTasks.put(task.getId(), task);
        return task.getId();
    }


    @Override
    public Integer addSubtask(Subtask task) {
        task.setId(nextID++);
        subtasks.put(task.getId(), task);
        Integer epicID = task.getParentEpicID();
        Epic epic = epics.get(epicID);
        epic.getSubtaskIDs().add(task.getId());
        updateEpicStatus(epicID);
        return task.getId();
    }

    @Override
    public Integer addEpic(Epic epic) {
        epic.setId(nextID++);
        epic.setStatus(NEW);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public void updateSimpleTask(SimpleTask newSimpleTask) {
        SimpleTask task = simpleTasks.get(newSimpleTask.getId());
        task.setDescription(newSimpleTask.getDescription());
        task.setName(newSimpleTask.getName());
        task.setStatus(newSimpleTask.getStatus());
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        Subtask subtask = subtasks.get(newSubtask.getId());
        subtask.setName(newSubtask.getName());
        subtask.setStatus(newSubtask.getStatus());
        subtask.setDescription(newSubtask.getDescription());
        updateEpicStatus(newSubtask.getParentEpicID());
    }

    @Override
    public void updateEpic(Epic newEpic) {
        Epic epic = epics.get(newEpic.getId());
        epic.setName(newEpic.getName());
        epic.setDescription(newEpic.getDescription());
    }

    @Override
    public void removeAllSimpleTasks() {
        for (Integer taskId : simpleTasks.keySet()) {
            historyManager.remove(taskId);
        }
        simpleTasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        for (Epic epic : epics.values()) {
            for (Integer subtaskId : epic.getSubtaskIDs()) {
                historyManager.remove(subtaskId); // Удаление подзадачи из истории просмотров
            }
            epic.clearSubtaskIds();
            updateEpicStatus(epic.getId());
        }
        subtasks.clear();
    }

    @Override
    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            for (Integer subtaskId : epic.getSubtaskIDs()) {
                historyManager.remove(subtaskId); // Удаление подзадачи из истории просмотров
            }
            historyManager.remove(epic.getId()); // Удаление эпической задачи из истории просмотров
        }
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void removeAll() {
        simpleTasks.clear();
        subtasks.clear();
        epics.clear();
        historyManager.clearHistory();
    }

    @Override
    public void removeSubtask(Integer id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Integer epicID = subtask.getParentEpicID();
            Epic epic = epics.get(epicID);
            epic.removeSubtask(id);
            updateEpicStatus(epicID);
            historyManager.remove(id);
        }
    }

    @Override
    public void removeEpic(Integer id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Integer subtaskID : epic.getSubtaskIDs()) {
                subtasks.remove(subtaskID);
                historyManager.remove(subtaskID);
            }
            historyManager.remove(id);
        }
    }

    @Override
    public void removeSimpleTask(Integer id) {
        simpleTasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public List<Subtask> getSubtaskListByEpicID(Integer id) {
        List<Subtask> currentList = new ArrayList<>();
        for (Integer currentSubtask : epics.get(id).getSubtaskIDs()) {
            currentList.add(subtasks.get(currentSubtask));
        }
        return currentList;

    }


    @Override
    public SimpleTask getTaskByID(Integer id) {
        SimpleTask task = simpleTasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Subtask getSubtaskByID(Integer id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public Epic getEpicByID(Integer id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }
}