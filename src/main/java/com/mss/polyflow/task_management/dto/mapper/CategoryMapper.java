package com.mss.polyflow.task_management.dto.mapper;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.mss.polyflow.task_management.dto.response.CategoryDetail;
import com.mss.polyflow.task_management.model.Category;
import java.util.List;
import java.util.stream.Collectors;


public final class CategoryMapper {

    public static Object toCategoryDetail(Category category) {
        return new CategoryDetail()
                   .setId(category.getId())
                   .setName(category.getName())
                   .setDescription(category.getDescription())
                   .setCreationTime(category.getCreationTime())
                   .setModificationTime(category.getModificationTime());
    }

    public static Object toCategoryDetailList(List<Category> all) {
        return all.stream()
                   .map(CategoryMapper :: toCategoryDetail)
                   .collect(Collectors.toList());
    }
}
