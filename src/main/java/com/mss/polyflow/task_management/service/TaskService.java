package com.mss.polyflow.task_management.service;

import com.mss.polyflow.shared.exception.NotFoundException;
import com.mss.polyflow.shared.utilities.functionality.CurrentUserManager;
import com.mss.polyflow.task_management.dto.mapper.TaskMapper;
import com.mss.polyflow.task_management.dto.request.CreateTask;
import com.mss.polyflow.task_management.dto.request.EditTaskDto;
import com.mss.polyflow.task_management.dto.request.SearchFilterQueryParameterDto;
import com.mss.polyflow.task_management.model.Category;
import com.mss.polyflow.task_management.model.Task;
import com.mss.polyflow.task_management.repository.category.CategoryRepository;
import com.mss.polyflow.task_management.repository.task.TaskRepository;
import com.mss.polyflow.task_management.utilities.PaginationUtility;
import com.mss.polyflow.task_management.utilities.PaginationUtility.PaginationWrapper;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final CategoryRepository categoryRepository;
    public TaskService(TaskRepository taskRepository,
        CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Object createTask(CreateTask createTask) {
        Category category = categoryRepository.findByIdAndUserId(createTask.getCategoryId(), CurrentUserManager.getCurrentUserId())
                                .orElseThrow(() -> new NotFoundException("No Such Category Exists"));
        /*
        By default Spring Data JPA inspects the identifier property of the given entity. If the identifier property
        is null, then the entity will be assumed as new, otherwise as not new.And so if one of your entity has an ID field not null,
        Spring will make Hibernate do an update (and so a SELECT before).
        */

        Task task = new Task()
                        .setId(null) // for cases if someone from outside the server tries to persist a task of particular id, we dont' want to give outside people the control. and it has been set to null instead of 0 because if one of your entity has an ID field not null, Spring will make Hibernate do an update (and so a SELECT before insert).
                        .setName(createTask.getName())
                        .setCategoryId(category.getId())
                        .setDescription(createTask.getDescription());
        task = taskRepository.save(task);
        return TaskMapper.toTaskDetail(task);
    }


    public Object getTaskDetail(Long taskId) {
        Task task = Optional.ofNullable(taskRepository.findTask(taskId, CurrentUserManager.getCurrentUserId()))
                        .orElseThrow(() -> new NotFoundException("No such task exists"));
        return TaskMapper.toTaskDetail(task);
    }


    public Object getAllPaginatedFilteredTasks(Long categoryId, SearchFilterQueryParameterDto searchFilterQueryParameterDto) {
        long pageNumber = searchFilterQueryParameterDto.getPage();
        long pageSize = searchFilterQueryParameterDto.getPageSize();
        Long existingCategoryId = categoryRepository.customFindByIdAndUserId(categoryId, CurrentUserManager.getCurrentUserId());

        if(existingCategoryId == null) {
            return new PaginationWrapper();
        }

        long offSet = (pageNumber - 1) * pageSize;
        long totalCount = taskRepository.countTotalTasksByFilter(searchFilterQueryParameterDto, categoryId);

        if(offSet >= totalCount) {
            return new PaginationWrapper();
//            throw new MiscellaneousException("Invalid page number, this page number doesn't exists yet");
        }

        List<Task> tasks = taskRepository.findPaginatedTotalFilteredTasks(searchFilterQueryParameterDto, categoryId);
        long totalPages = (long) Math.ceil((double)totalCount/pageSize);
        return PaginationUtility.toPaginationWrapper(
            pageSize,
            pageNumber,
            totalPages,
            totalCount,
            TaskMapper.toTaskDetailList(tasks)
        );
    }


    @Transactional
    public Object editTask(EditTaskDto editTaskDto) {
        Task task = Optional.ofNullable(taskRepository.findTask(editTaskDto.getId(), CurrentUserManager.getCurrentUserId()))
                   .orElseThrow(() -> new NotFoundException("No such task exists"));
        BeanUtils.copyProperties(editTaskDto, task);
        return TaskMapper.toTaskDetail(taskRepository.save(task));
    }

    public int deleteTask(Long taskId) {
        return taskRepository.deleteTask(taskId, CurrentUserManager.getCurrentUserId());
    }

}
