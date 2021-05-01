package com.mss.polyflow.task_management.controller;


import static com.mss.polyflow.shared.utilities.response.ResponseModel.sendResponse;

import com.mss.polyflow.shared.utilities.response.ResponseModel;
import com.mss.polyflow.task_management.dto.request.CreateCategory;
import com.mss.polyflow.task_management.dto.request.EditCategoryDto;
import com.mss.polyflow.task_management.dto.request.SearchFilterQueryParameterDto;
import com.mss.polyflow.task_management.service.CategoryService;
import com.mss.polyflow.task_management.utilities.PaginationUtility;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/task_manage/category")
public class CategoryController {

    private static final Object SUCCESS = 1;
    private final CategoryService categoryService;

    public CategoryController(
        CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/ping")
    private ResponseEntity<Object> pingCategory() {
        return sendResponse(null, "category handler working successfully");
    }

    @PostMapping("/create")
    private ResponseEntity<Object> createCategory(@RequestBody @Valid CreateCategory category) {
        Object createdCategory = categoryService.createCategory(category);
        return sendResponse(createdCategory, "category created successfully");
    }

    @PutMapping("/edit")
    private ResponseEntity<Object> editCategory(@RequestBody @Valid EditCategoryDto editCategoryDto) {
        Object createdCategory = categoryService.editCategory(editCategoryDto);
        return sendResponse(createdCategory, "category updated successfully");
    }

    @GetMapping("/all")
    private ResponseEntity<Object> getAllPaginatedCategories(@Valid SearchFilterQueryParameterDto searchQueryParams) {
        return sendResponse(categoryService.getAllPaginatedFilteredCategories(searchQueryParams), "categories fetched successfully");

    }

    @RequestMapping(value = "/detail/{categoryId}", method = RequestMethod.GET)
    private ResponseEntity<Object> getCategoryDetail(@PathVariable("categoryId") Long categoryId) {
        return sendResponse(categoryService.getCategoryDetail(categoryId), "category detail fetched successfully");
    }


    @DeleteMapping("/delete/{categoryId}")
    private ResponseEntity<Object> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        int result = categoryService.deleteCategory(categoryId);
        return sendResponse(null , result == 0 ? "No such category exists" : "category deleted successfully");
    }


}
