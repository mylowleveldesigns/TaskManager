package storage.taskstorage;

import exceptions.TaskAlreadyExistsException;
import exceptions.TaskNotFoundException;
import model.Task;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ConcurrentTaskStorage implements TaskStorage {

    private final ConcurrentHashMap<Integer, Task> tasks = new ConcurrentHashMap<>();

    @Override
    public void saveTask(Task task) throws TaskAlreadyExistsException {
        if (tasks.containsKey(task.id())) {
            throw new TaskAlreadyExistsException("Task with ID " + task.id() + " already exists.");
        }
        tasks.put(task.id(), task);
    }

    @Override
    public Optional<Task> getTask(int taskId) {
        return Optional.ofNullable(tasks.get(taskId));
    }

    @Override
    public void updateTask(Task task) throws TaskNotFoundException {
        if (!tasks.containsKey(task.id())) {
            throw new TaskNotFoundException("Task with ID " + task.id() + " not found.");
        }
        tasks.put(task.id(), task);
    }

    @Override
    public void deleteTask(int taskId) throws TaskNotFoundException {
        if (tasks.remove(taskId) == null) {
            throw new TaskNotFoundException("Task with ID " + taskId + " not found.");
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return tasks.values().stream().collect(Collectors.toList());
    }
}

