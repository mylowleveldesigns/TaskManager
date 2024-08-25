package taskutilities;

import model.Task;

public interface TaskFilter {
    public boolean matches(Task task);
}
