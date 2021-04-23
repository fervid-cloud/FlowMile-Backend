package com.mss.polyflow.task_management.dto.mapper;

import com.mss.polyflow.task_management.dto.response.TaskDetail;
import com.mss.polyflow.task_management.model.Task;
import java.util.List;
import java.util.stream.Collectors;


public final class TaskMapper {
    public static Object toTaskDetail(Task task) {
        return new TaskDetail()
                   .setId(task.getId())
                   .setName(task.getName())
                   .setDescription(task.getDescription())
                   .setCategoryId(task.getCategoryId())
                   .setTaskStatus(task.getTaskStatus())
                   .setCreationTime(task.getCreationTime())
                   .setModificationTime(task.getModificationTime());
    }

    public static Object toTaskDetailList(List<Task> all) {
        return all.stream()
                   .map(TaskMapper :: toTaskDetail)
                   .collect(Collectors.toList());
    }
}
