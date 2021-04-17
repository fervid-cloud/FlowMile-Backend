package com.mss.polyflow.task_management.utilities.enum_constants;

import lombok.ToString;

public enum TaskStatus {

    ANY("any"), PENDING("pending"), DONE("done");

    private final String value;

/*    Although it's illegal to use the new operator for an enum, we can pass constructor arguments in the declaration list.*/
    TaskStatus(String taskTypeName) {
        value = taskTypeName;
    }

    public final String getValue() {
        return this.value;
    }

/*    By default, Enum.toString() returns the same value as Enum.name().
      I have tried to follow the same conventions here for custom variable value
    */
    @Override
    public String toString() {
        return this.value;
    }

    public static boolean isValidTaskStatus(String taskStatus) {
        for(TaskStatus availableTaskStatus : TaskStatus.values()) {
            if(availableTaskStatus.equals(taskStatus)) {
                return true;
            }
        }
        return false;
    }
}
