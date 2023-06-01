package main.managers;

import main.tasks.Epic;
import main.tasks.SimpleTask;
import main.tasks.Subtask;
import main.tasks.Task;

import java.util.List;

public interface TaskManager {

    List<SimpleTask> getListSimpleTask();

    List<Subtask> getListSubtask();

    List<Epic> getListEpic();
    List<Task> getHistory();

    Integer addSimpleTask(SimpleTask task);

    Integer addSubtask(Subtask task);

    Integer addEpic(Epic epic);

    void updateSimpleTask(SimpleTask newSimpleTask);

    void updateSubtask(Subtask newSubtask);

     void updateEpic(Epic newEpic);

     void removeAllSimpleTasks() ;

    void removeAllSubtasks();

    void removeAllEpics();

    void removeAll();

   void  removeSubtask(Integer id);

   void removeEpic(Integer id);

    void removeSimpleTask(Integer id);

    List<Subtask> getSubtaskListByEpicID(Integer id);

    SimpleTask getTaskByID(Integer id);

    Subtask getSubtaskByID(Integer id);

    Epic getEpicByID(Integer id);
}
