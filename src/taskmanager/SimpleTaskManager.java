package taskmanager;

import exceptions.TaskAlreadyExistsException;
import exceptions.TaskNotFoundException;
import model.*;
import storage.activitystorage.ActivityLogStorage;
import storage.taskstorage.TaskStorage;
import taskutilities.Helper;
import taskutilities.TaskFilter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimpleTaskManager implements TaskManager {

    private final TaskStorage taskStorage;
    private final ActivityLogStorage history;

    private final Helper helper;

    public SimpleTaskManager(TaskStorage taskStorage, ActivityLogStorage activityLogStorage) {
        this.taskStorage = taskStorage;
        this.history = activityLogStorage;
        this.helper = new Helper();
    }

    @Override
    public void addTask(Task task) throws TaskAlreadyExistsException {
        taskStorage.saveTask(task);
        logActivity(TaskAction.ADD, task, LocalDateTime.now());
    }

    @Override
    public Task getTask(int taskId) throws TaskNotFoundException {
        return taskStorage.getTask(taskId).orElseThrow(() -> new TaskNotFoundException("Task with ID " + taskId + " not found."));
    }

    @Override
    public void modifyTask(Task task) throws TaskNotFoundException {
        taskStorage.updateTask(task);
        logActivity(TaskAction.MODIFY, task, LocalDateTime.now());
    }

    @Override
    public void removeTask(int taskId) throws TaskNotFoundException {
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

        long spilledOver = getSpilledOverTaskCount(logs, timePeriod);

        return new TaskStatistics(added, completed, spilledOver);
    }

    private long getSpilledOverTaskCount(List<ActivityEntry> logs, TimePeriod timePeriod) {
        // Get all logs where log.timestamp is before timePeriod.endTime
        // Group logs by taskId
        // Iterate over the groups and filter those tasks where deadline is between the timeperiod and log with action = complete is after deadline or not found


        // Step 1: Filter logs where log.timestamp is before timePeriod.endTime
        Map<Integer, List<ActivityEntry>> groupedLogs = logs.stream()
                .filter(log -> log.getTimestamp().isBefore(timePeriod.getEndTime()))
                .collect(Collectors.groupingBy(log -> log.getTask().id()));

        // Step 2: Iterate over the groups and filter tasks based on the correct logic
        return groupedLogs.values().stream()
                .filter(taskLogs -> {
                    Task task = taskLogs.get(0).getTask();
                    boolean isDeadlineInPeriod = helper.isTimeWithinPeriod(task.deadline(), timePeriod);


                    if (isDeadlineInPeriod) {
                        boolean hasNotCompletedYet = taskLogs.stream().noneMatch(log -> log.getAction() == TaskAction.COMPLETE);

                        boolean hasCompletionAfterDeadline = taskLogs.stream()
                                .filter(log -> log.getAction() == TaskAction.COMPLETE)
                                .allMatch(log -> log.getTimestamp().isAfter(task.deadline()));



                        return hasCompletionAfterDeadline || hasNotCompletedYet;
                    }
                    return false;
                })
                .count();
    }

    @Override
    public List<ActivityEntry> getActivityLog(TimePeriod timePeriod) {
        return history.getLogs(timePeriod)
                .stream()
                .filter( log -> helper.isTimeWithinPeriod(log.getTimestamp(), timePeriod))
                .collect(Collectors.toList());
    }

    private void logActivity(TaskAction action, Task task, LocalDateTime localDateTime) {
        history.logAction(action, task, localDateTime);
    }
}

