package com.mss.polyflow.task_management.service;

import com.mss.polyflow.shared.exception.MiscellaneousException;
import com.mss.polyflow.task_management.dto.request.CreateCategory;
import com.mss.polyflow.task_management.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryService(
        CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Object createCategory(CreateCategory category) {
        return null;
    }

    public Object getAllCategories() {
        return categoryRepository.findAll();
    }

    public Object getCategoryDetail(Long categoryId) {
        return categoryRepository.findById(categoryId)
                   .orElseThrow(() -> new MiscellaneousException("No such category exists"));
    }

    @Transactional
    public Object deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return null;
    }


}
