package storage.activitystorage;

import model.ActivityEntry;
import model.Task;
import model.TaskAction;
import model.TimePeriod;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityLogStorage {
    public void logAction(TaskAction action, Task task, LocalDateTime localDateTime);
    public List<ActivityEntry> getLogs(TimePeriod timePeriod);
}
