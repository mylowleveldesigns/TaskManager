package taskmanager;

import exceptions.TaskAlreadyExistsException;
import exceptions.TaskNotFoundException;
import model.*;
import storage.activitystorage.ActivityLogStorage;
import storage.taskstorage.TaskStorage;
import taskutilities.TaskFilter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleTaskManager implements TaskManager {

    private final TaskStorage taskStorage;
    private final ActivityLogStorage history;

    public SimpleTaskManager(TaskStorage taskStorage, ActivityLogStorage activityLogStorage) {
        this.taskStorage = taskStorage;
        this.history = activityLogStorage;
    }

    @Override
    public synchronized void addTask(Task task) throws TaskAlreadyExistsException {
        taskStorage.saveTask(task);
        logActivity(TaskAction.ADD, task, LocalDateTime.now());
    }

    @Override
    public synchronized Task getTask(int taskId) throws TaskNotFoundException {
        return taskStorage.getTask(taskId).orElseThrow(() -> new TaskNotFoundException("Task with ID " + taskId + " not found."));
    }

    @Override
    public synchronized void modifyTask(Task task) throws TaskNotFoundException {
        taskStorage.updateTask(task);
        logActivity(TaskAction.MODIFY, task, LocalDateTime.now());
    }

    @Override
    public synchronized void removeTask(int taskId) throws TaskNotFoundException {
        Task task = getTask(taskId);
        taskStorage.deleteTask(taskId);
        logActivity(TaskAction.REMOVE, task, LocalDateTime.now());
    }

    @Override
    public List<Task> listTasks(TaskFilter filter) {
        return taskStorage.getAllTasks().stream()
                .filter(filter::matches)
                .collect(Collectors.toList());
    }

    @Override
    public TaskStatistics getStatistics(TimePeriod timePeriod) {
        List<ActivityEntry> logs = history.getLogs(timePeriod);


        long added = logs.stream()
                .filter(log -> log.getAction() == TaskAction.ADD)
                .count();

        long completed = logs.stream()
                .filter(log -> log.getAction() == TaskAction.COMPLETE)
                .count();

        long spilledOver = logs.stream()
                .filter(log -> taskStorage.getTask(log.getTaskId())
                        .map(Task::isOverdue)
                        .orElse(false))
                .count();


        return new TaskStatistics(added, completed, spilledOver);
    }

    @Override
    public List<ActivityEntry> getActivityLog(TimePeriod timePeriod) {
        //@Todo  Complete the funciton by iterating over Activity
        return new ArrayList<>();
    }

    private void logActivity(TaskAction action, Task task, LocalDateTime localDateTime) {
        history.logAction(action, task.id(), localDateTime);
    }
}

