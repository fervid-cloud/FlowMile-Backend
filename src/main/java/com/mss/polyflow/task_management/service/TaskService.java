package com.mss.polyflow.task_management.service;

import com.mss.polyflow.shared.exception.MiscellaneousException;
import com.mss.polyflow.task_management.dto.mapper.TaskMapper;
import com.mss.polyflow.task_management.dto.request.CreateTask;
import com.mss.polyflow.task_management.model.Task;
import com.mss.polyflow.task_management.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Object createTask(CreateTask createTask) {
        Task task = new Task()
                        .setName(createTask.getName())
                        .setDescription(createTask.getDescription());
        task = taskRepository.save(task);
        return TaskMapper.toTaskDetail(task);
    }

    public Object getAllTasks() {
        return TaskMapper.toTaskDetailList(taskRepository.findAll());
    }

    public Object getTaskDetail(Long taskId) {
        Task task = taskRepository.findById(taskId)
                   .orElseThrow(() -> new MiscellaneousException("No such task exists"));
        return TaskMapper.toTaskDetail(task);
    }

    public Object deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
        return null;
    }
}
