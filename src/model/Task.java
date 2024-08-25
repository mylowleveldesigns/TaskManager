package model;

import java.time.LocalDateTime;
import java.util.Set;

public record Task(int id, String description, LocalDateTime deadline, Set<String> tags, boolean isCompleted) {
    public boolean isOverdue() {
        return !isCompleted && deadline.isBefore(LocalDateTime.now());
    }
}

