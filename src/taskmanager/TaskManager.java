package taskmanager;

import exceptions.TaskAlreadyExistsException;
import exceptions.TaskNotFoundException;
import model.ActivityEntry;
import model.Task;
import model.TaskStatistics;
import model.TimePeriod;
import taskutilities.TaskFilter;

import java.util.List;

public interface TaskManager {
    void addTask(Task task) throws TaskAlreadyExistsException;
    Task getTask(int taskId) throws TaskNotFoundException;
    void modifyTask(Task task) throws TaskNotFoundException;
    void removeTask(int taskId) throws TaskNotFoundException;
    List<Task> listTasks(TaskFilter filter);
    TaskStatistics getStatistics(TimePeriod timePeriod);
    List<ActivityEntry> getActivityLog(TimePeriod timePeriod);
}

