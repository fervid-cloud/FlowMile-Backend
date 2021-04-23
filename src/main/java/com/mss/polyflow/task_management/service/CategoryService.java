package com.mss.polyflow.task_management.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.mss.polyflow.shared.exception.MiscellaneousException;
import com.mss.polyflow.shared.exception.NotFoundException;
import com.mss.polyflow.shared.security.authentication.CurrentUserDetail;
import com.mss.polyflow.shared.utilities.functionality.CurrentUserManager;
import com.mss.polyflow.task_management.dto.mapper.CategoryMapper;
import com.mss.polyflow.task_management.dto.mapper.TaskMapper;
import com.mss.polyflow.task_management.dto.request.CreateCategory;
import com.mss.polyflow.task_management.dto.request.EditCategoryDto;
import com.mss.polyflow.task_management.model.Category;
import com.mss.polyflow.task_management.repository.CategoryRepository;
import com.mss.polyflow.task_management.utilities.PaginationUtility;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
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
        /*
        By default Spring Data JPA inspects the identifier property of the given entity. If the identifier property
        is null, then the entity will be assumed as new, otherwise as not new.And so if one of your entity has an ID field not null,
        Spring will make Hibernate do an update (and so a SELECT before).
        */
        Category category = new Category()
                                .setId(null) // for cases if someone from outside the server tries to persist a category of particular id, we dont' want to give outside people the control
                                .setName(createCategory.getName())
                                .setUserId(CurrentUserManager.getCurrentUserId())
                                .setDescription(createCategory.getDescription());
        category = categoryRepository.save(category);
        return CategoryMapper.toCategoryDetail(category);
    }

    public Object getAllCategories(Long pageSize, Long pageNumber) {
        long offSet = (pageNumber - 1) * pageSize;
        long totalCount = categoryRepository.countTotalCategory(CurrentUserManager.getCurrentUserId());
        if(offSet >= totalCount) {
            throw new MiscellaneousException("Invalid page number, this page number doesn't exists yet");
        }
        List<Category> categories = categoryRepository.findCategories(pageSize, offSet, CurrentUserManager.getCurrentUserId());
        long totalPages = (long) Math.ceil((double)totalCount/pageSize);

        return PaginationUtility.toPaginationWrapper(
            pageSize,
            pageNumber,
            totalPages,
            totalCount,
            CategoryMapper.toCategoryDetailList(categories)
        );
    }

    public Object getCategoryDetail(Long categoryId) {
        Category category = categoryRepository.findByIdAndUserId(categoryId, CurrentUserManager.getCurrentUserId())
                   .orElseThrow(() -> new NotFoundException("No such category exists"));
        return CategoryMapper.toCategoryDetail(category);
    }

    @Transactional
    public int deleteCategory(Long categoryId) {
        // basically if there exist such category for this user, 1 row will be affected, thus returning 1  else 0 row affected, so 0 value will be returned
        return categoryRepository.deleteCategory(categoryId, CurrentUserManager.getCurrentUserId());
    }


    public Object editCategory(EditCategoryDto editCategoryDto) {

        Category category = this.categoryRepository.findByIdAndUserId(editCategoryDto.getId(), CurrentUserManager.getCurrentUserId()).orElseThrow(() -> new MiscellaneousException("Invalid edit request, no scuh category exists"));

        BeanUtils.copyProperties(editCategoryDto, category);
        return CategoryMapper.toCategoryDetail(this.categoryRepository.save(category));
    }
}
