package com.mss.polyflow.task_management.controller;

import static com.mss.polyflow.shared.utilities.response.ResponseModel.sendResponse;

import com.mss.polyflow.shared.exception.MiscellaneousException;
import com.mss.polyflow.shared.exception.NotFoundException;
import com.mss.polyflow.task_management.dto.request.CreateTask;
import com.mss.polyflow.task_management.dto.request.EditTaskDto;
import com.mss.polyflow.task_management.dto.request.SearchFilterQueryParameterDto;
import com.mss.polyflow.task_management.service.TaskService;
import com.mss.polyflow.task_management.utilities.PaginationUtility;
import com.mss.polyflow.task_management.utilities.enum_constants.TaskStatus;
import java.util.Map;
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


    @GetMapping("all/{categoryId}")
    private ResponseEntity<Object> filterCategoriesByQueryParameters(
        @PathVariable("categoryId") Long categoryId,
        @Valid SearchFilterQueryParameterDto searchFilterQueryParameterDto
    ) {
        return sendResponse(taskService.getAllPaginatedFilteredTasks(categoryId, searchFilterQueryParameterDto), "filtered categories fetched successfully");
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
