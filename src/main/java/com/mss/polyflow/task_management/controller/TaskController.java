package com.mss.polyflow.task_management.controller;

import static com.mss.polyflow.shared.utilities.response.ResponseModel.sendResponse;

import com.mss.polyflow.task_management.dto.request.CreateCategory;
import com.mss.polyflow.task_management.dto.request.CreateTask;
import com.mss.polyflow.task_management.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    private ResponseEntity<Object> createTask(@RequestBody CreateTask createTask) {
        Object createdTask = taskService.createTask(createTask);
        return sendResponse(createdTask, "task created successfully");
    }

    @GetMapping("/all")
    private ResponseEntity<Object> getAllTasks() {
        return sendResponse(taskService.getAllTasks(), "tasks fetched successfully");
    }

    @RequestMapping(value = "/detail/{taskId}", method = RequestMethod.GET)
    private ResponseEntity<Object> getTaskDetail(@PathVariable("taskId") Long taskId) {
        return sendResponse(taskService.getTaskDetail(taskId), "task detail fetched successfully");
    }


    @DeleteMapping("/delete/{taskId}")
    private ResponseEntity<Object> deleteCategory(@PathVariable("taskId") Long taskId) {
        return sendResponse(taskService.deleteTask(taskId), "task deleted successfully");
    }

}
