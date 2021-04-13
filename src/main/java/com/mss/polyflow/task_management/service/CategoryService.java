package com.mss.polyflow.task_management.service;

import com.mss.polyflow.shared.exception.MiscellaneousException;
import com.mss.polyflow.task_management.dto.mapper.CategoryMapper;
import com.mss.polyflow.task_management.dto.request.CreateCategory;
import com.mss.polyflow.task_management.model.Category;
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
    public Object createCategory(CreateCategory createCategory) {
        Category category = new Category()
                                .setName(createCategory.getName())
                                .setDescription(createCategory.getDescription());
        category = categoryRepository.save(category);
        return CategoryMapper.toCategoryDetail(category);
    }

    public Object getAllCategories() {
        return CategoryMapper.toCategoryDetailList(categoryRepository.findAll());
    }

    public Object getCategoryDetail(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                   .orElseThrow(() -> new MiscellaneousException("No such category exists"));
        return CategoryMapper.toCategoryDetail(category);
    }

    @Transactional
    public Object deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return null;
    }


}
