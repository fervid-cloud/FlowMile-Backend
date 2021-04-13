package com.mss.polyflow.task_management.controller;


import static com.mss.polyflow.shared.utilities.response.ResponseModel.sendResponse;

import com.mss.polyflow.shared.utilities.response.ResponseModel;
import com.mss.polyflow.task_management.dto.request.CreateCategory;
import com.mss.polyflow.task_management.service.CategoryService;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/task_manage/category")
public class CategoriesController {

    private static final Object SUCCESS = 1;
    private final CategoryService categoryService;

    public CategoriesController(
        CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    private ResponseEntity<Object> createCategory(@RequestBody CreateCategory category) {
        Object createdCategory = categoryService.createCategory(category);
        return sendResponse(createdCategory, "category created successfully");
    }

    @GetMapping("/all")
    private ResponseEntity<Object> getAllCategories() {
        return sendResponse(categoryService.getAllCategories(), "categories fetched successfully");
    }

    @RequestMapping(value = "/detail/{categoryId}", method = RequestMethod.GET)
    private ResponseEntity<Object> getCategoryDetail(@PathVariable("categoryId") Long categoryId) {
        return sendResponse(categoryService.getCategoryDetail(categoryId), "category detail fetched successfully");
    }


    @DeleteMapping("/delete/{categoryId}")
    private ResponseEntity<Object> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        return sendResponse(categoryService.deleteCategory(categoryId), "category deleted successfully");
    }


}
