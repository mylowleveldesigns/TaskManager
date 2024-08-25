package storage.taskstorage;

import exceptions.TaskAlreadyExistsException;
import exceptions.TaskNotFoundException;
import model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskStorage {
    void saveTask(Task task) throws TaskAlreadyExistsException;
    Optional<Task> getTask(int taskId);
    void updateTask(Task task) throws TaskNotFoundException;
    void deleteTask(int taskId) throws TaskNotFoundException;
    List<Task> getAllTasks();
}

