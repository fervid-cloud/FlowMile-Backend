package com.mss.polyflow.task_management.service;

import com.mss.polyflow.shared.exception.MiscellaneousException;
import com.mss.polyflow.shared.exception.NotFoundException;
import com.mss.polyflow.shared.utilities.functionality.CurrentUserManager;
import com.mss.polyflow.task_management.dto.mapper.TaskMapper;
import com.mss.polyflow.task_management.dto.request.CreateTask;
import com.mss.polyflow.task_management.model.Category;
import com.mss.polyflow.task_management.model.Task;
import com.mss.polyflow.task_management.repository.CategoryRepository;
import com.mss.polyflow.task_management.repository.TaskRepository;
import com.mss.polyflow.task_management.utilities.PaginationUtility;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final CategoryRepository categoryRepository;
    public TaskService(TaskRepository taskRepository,
        CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    public Object createTask(CreateTask createTask) {
        Category category = categoryRepository.findByIdAndUserId(createTask.getCategoryId(), CurrentUserManager.getCurrentUserId())
                                .orElseThrow(() -> new NotFoundException("No Such Category Exists"));
        Task task = new Task()
                        .setId(0l) // for cases if someone from outside the server tries to persist a task of particular id, we dont' want to give outside people the control
                        .setName(createTask.getName())
                        .setCategoryId(category.getId())
                        .setDescription(createTask.getDescription());
        task = taskRepository.save(task);
        return TaskMapper.toTaskDetail(task);
    }

    public Object getAllTasks(Long categoryId, Long pageSize, Long pageNumber) {
        categoryRepository.findByIdAndUserId(categoryId, CurrentUserManager.getCurrentUserId())
                                .orElseThrow(() -> new NotFoundException("No Such Category Exists"));
        long offSet = (pageNumber - 1) * pageSize;
        long totalCount = taskRepository.countTotalTasks(categoryId);
        if(offSet >= totalCount) {
            throw new MiscellaneousException("Invalid page number, this page number doesn't exists yet");
        }
        List<Task> tasks = taskRepository.findTasks(categoryId, pageSize, offSet);
        long totalPages = (long) Math.ceil(totalCount/pageSize);
        return PaginationUtility.toPaginationWrapper(
            pageSize,
            pageNumber,
            totalPages,
            totalCount,
            TaskMapper.toTaskDetailList(tasks)
        );
    }

    public Object getTaskDetail(Long taskId) {
        Task task = Optional.ofNullable(taskRepository.findTask(taskId, CurrentUserManager.getCurrentUserId()))
                   .orElseThrow(() -> new NotFoundException("No such task exists"));
        return TaskMapper.toTaskDetail(task);
    }

    public int deleteTask(Long taskId) {
        return taskRepository.deleteTask(taskId, CurrentUserManager.getCurrentUserId());
    }
}
