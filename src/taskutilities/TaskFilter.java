package taskutilities;


import model.Task;


public class TaskFilter {

    private final String tagFilter;
    private final boolean filterCompleted;
    private final boolean filterOverdue;

    //@Todo - we can also add sortorders

    public TaskFilter(String tagFilter, boolean filterCompleted, boolean filterOverdue) {
        this.tagFilter = tagFilter;
        this.filterCompleted = filterCompleted;
        this.filterOverdue = filterOverdue;
    }

    public boolean matches(Task task) {
        if (tagFilter != null && !task.tags().contains(tagFilter)) {
            return false;
        }
        if (filterCompleted && task.isCompleted()) {
            return false;
        }
        if (filterOverdue && !task.isOverdue()) {
            return false;
        }
        return true;
    }
}

