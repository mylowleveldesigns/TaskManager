package model;

import java.time.LocalDateTime;

public class ActivityEntry {
    private final TaskAction action;

    private final Task task;
    private final LocalDateTime timestamp;

    public ActivityEntry(TaskAction action, Task task, LocalDateTime timestamp) {
        this.action = action;
        this.task = task;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ActivityEntry{" +
                "action=" + action +
                ", task=" + task +
                ", timestamp=" + timestamp +
                '}';
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TaskAction getAction() {
        return action;
    }

    public Task getTask() {
        return task;
    }
}
