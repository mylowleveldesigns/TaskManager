package storage.activitystorage;

import model.ActivityEntry;
import model.TaskAction;
import model.TimePeriod;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListBasedActivityLogStorage implements ActivityLogStorage {
    private final List<ActivityEntry> logEntries = new ArrayList<>();

    public void logAction(TaskAction action, int taskId, LocalDateTime localDateTime) {
        logEntries.add(new ActivityEntry(action, taskId, localDateTime));
    }

    public List<ActivityEntry> getLogs(TimePeriod timePeriod) {
        return logEntries.stream()
                .filter(entry -> timePeriod.isWithinPeriod(entry.getTimestamp()))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return logEntries.stream()
                .map(ActivityEntry::toString)
                .collect(Collectors.joining("\n"));
    }
}
