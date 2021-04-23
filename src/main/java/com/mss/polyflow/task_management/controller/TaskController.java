package com.mss.polyflow.task_management.controller;

import static com.mss.polyflow.shared.utilities.response.ResponseModel.sendResponse;

import com.mss.polyflow.shared.exception.MiscellaneousException;
import com.mss.polyflow.shared.exception.NotFoundException;
import com.mss.polyflow.task_management.dto.request.CreateTask;
import com.mss.polyflow.task_management.dto.request.EditTaskDto;
import com.mss.polyflow.task_management.service.TaskService;
import com.mss.polyflow.task_management.utilities.PaginationUtility;
import com.mss.polyflow.task_management.utilities.enum_constants.TaskStatus;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/task_manage/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/ping")
    private ResponseEntity<Object> pingTask() {
        return sendResponse(null, "task handler working successfully");
    }

    @PostMapping("/create")
    private ResponseEntity<Object> createTask(@RequestBody  @Valid CreateTask createTask) {
        Object createdTaskDetail = taskService.createTask(createTask);
        return sendResponse(createdTaskDetail, "task created successfully");
    }


    @PutMapping("/edit")
    private ResponseEntity<Object> editTask(@RequestBody  @Valid EditTaskDto editTaskDto) {
        Object editedTaskDetail = taskService.editTask(editTaskDto);
        return sendResponse(editedTaskDetail, "task created successfully");
    }




    @Validated
    @GetMapping("/all/{categoryId}")
    private Object getAllTasks (
        @PathVariable @Min(value = 1, message = "invalid categoryId value")  Long categoryId,
        @RequestParam(value="taskStatus", required = false) String taskStatus,
        @RequestParam(value = "pageSize", required = false, defaultValue = PaginationUtility.DEFAULT_PAGE_SIZE_S)  Long pageSize,
        @RequestParam( value = "pageNumber", required = false, defaultValue = PaginationUtility.DEFAULT_PAGE_NUMBER_S) Long pageNumber
    ) {
        if(categoryId < 1) {
            throw new NotFoundException("No Such category exists");
        }




        log.info("categoryId  is : {}", categoryId);
        log.info("taskStatus is : {}", taskStatus);
        log.info("page size is : {}", pageSize);
        log.info("page number is : {}", pageNumber);
        TaskStatus currentTaskStatus;
        if(taskStatus == null) {
//            taskStatus = TaskStatus.ANY.getValue();
            currentTaskStatus = TaskStatus.ANY;
        } else {
            if (!TaskStatus.isValidTaskStatus(taskStatus)) {
                throw new NotFoundException("No Such Task type is found");
            }
            currentTaskStatus = TaskStatus.valueOf(taskStatus);
        }

        PaginationUtility.requiredPageInputValidation(pageSize, pageNumber);
        Object response = taskService.getAllTasks(categoryId, pageSize, pageNumber, currentTaskStatus);
        return sendResponse(response, "tasks fetched successfully");

    }

    @RequestMapping(value = "/detail/{taskId}", method = RequestMethod.GET)
    private ResponseEntity<Object> getTaskDetail(@PathVariable("taskId") Long taskId) {
        return sendResponse(taskService.getTaskDetail(taskId), "task detail fetched successfully");
    }


    @DeleteMapping("/delete/{taskId}")
    private ResponseEntity<Object> deleteCategory(@PathVariable("taskId") Long taskId) {
        int result = taskService.deleteTask(taskId);
        return sendResponse(null , result == 0 ? "No such task exists" : "task deleted successfully");
    }

}
