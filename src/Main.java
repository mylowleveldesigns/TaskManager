import model.*;
import storage.activitystorage.ListBasedActivityLogStorage;
import storage.taskstorage.ConcurrentTaskStorage;
import taskmanager.SimpleTaskManager;
import taskmanager.TaskManager;
import taskutilities.DefaultTaskFilter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try {

            TaskManager taskManager = new SimpleTaskManager(
                    new ConcurrentTaskStorage(),
                    new ListBasedActivityLogStorage()
            );

            Set<String> tags1 = new HashSet<>();
            tags1.add("urgent");
            tags1.add("work");
            Task task1 = new Task(1, "Send Emails",  LocalDateTime.now().plusDays(1), tags1, false);

            Set<String> tags2 = new HashSet<>();
            tags2.add("personal");
            Task task2 = new Task(2, "Bring Grocerries", LocalDateTime.now().minusDays(1), tags2, false);

            Set<String> tags3 = new HashSet<>();
            tags3.add("work");
            tags3.add("important");
            Task task3 = new Task(3, "Write Code for project", LocalDateTime.now().plusDays(2), tags3, false);

            taskManager.addTask(task1);
            taskManager.addTask(task2);
            taskManager.addTask(task3);

            // modify a task with taskId = 1
            Set<String> newTags = new HashSet<>();
            newTags.add("work");
            newTags.add("updated");
            Task modifiedTask = new Task(1, "Updated Task 1", LocalDateTime.now().plusDays(3), newTags, false);
            taskManager.modifyTask(modifiedTask);

            // remove a task with taskId = 2
            taskManager.removeTask(2);


            // Get and print statistics
            TimePeriod timePeriodDayMinus1To1 = new TimePeriod(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
            TaskStatistics statistics = taskManager.getStatistics(timePeriodDayMinus1To1);
            System.out.println("Task Statistics: " + statistics);

            // print activity log
            List<ActivityEntry> activityLog = taskManager.getActivityLog(timePeriodDayMinus1To1);
            System.out.println("Activity Log: ");
            activityLog.forEach(System.out::println);

            // Filter tasks by tag
            DefaultTaskFilter tagFilter = new DefaultTaskFilter("work", false, false);
            List<Task> filteredTasks = taskManager.listTasks(tagFilter);
            System.out.println("Filtered Tasks (tag 'work'): ");
            filteredTasks.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}