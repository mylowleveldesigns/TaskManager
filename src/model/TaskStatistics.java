package model;

public record TaskStatistics(long tasksAdded, long tasksCompleted, long tasksSpilledOverDeadline ) {


    @Override
    public String toString() {
        return "TaskStatistics{" +
                "tasksAdded=" + tasksAdded +
                ", tasksCompleted=" + tasksCompleted +
                ", tasksSpilledOverDeadline=" + tasksSpilledOverDeadline +
                '}';
    }
}

