package storage.activitystorage;

import model.ActivityEntry;
import model.Task;
import model.TaskAction;
import model.TimePeriod;
import taskutilities.Helper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ListBasedActivityLogStorage implements ActivityLogStorage {
    private final List<ActivityEntry> logEntries = new CopyOnWriteArrayList<>();
    private final Helper helper = new Helper();

    public void logAction(TaskAction action, Task task, LocalDateTime localDateTime) {
        logEntries.add(new ActivityEntry(action, task, localDateTime));
    }

    public List<ActivityEntry> getLogs(TimePeriod timePeriod) {
        return logEntries.stream()
                .filter(entry -> helper.isTimeWithinPeriod(entry.getTimestamp(), timePeriod))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return logEntries.stream()
                .map(ActivityEntry::toString)
                .collect(Collectors.joining("\n"));
    }
}
