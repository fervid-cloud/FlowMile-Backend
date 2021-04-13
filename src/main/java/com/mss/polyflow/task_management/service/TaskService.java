package com.mss.polyflow.task_management.service;

import com.mss.polyflow.task_management.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}
