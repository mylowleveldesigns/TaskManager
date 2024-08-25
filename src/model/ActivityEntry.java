package model;

import java.time.LocalDateTime;

public class ActivityEntry {
    private final TaskAction action;

    private final int taskId;
    private final LocalDateTime timestamp;

    public ActivityEntry(TaskAction action, int taskId, LocalDateTime timestamp) {
        this.action = action;
        this.taskId = taskId;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ActivityEntry{" +
                "action=" + action +
                ", task=" + taskId +
                ", timestamp=" + timestamp +
                '}';
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TaskAction getAction() {
        return action;
    }

    public int getTaskId() {
        return taskId;
    }
}
