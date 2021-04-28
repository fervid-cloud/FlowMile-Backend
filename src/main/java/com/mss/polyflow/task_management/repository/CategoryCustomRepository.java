package com.mss.polyflow.task_management.repository;

import com.mss.polyflow.task_management.dto.request.SearchFilterQueryParameterDto;
import com.mss.polyflow.task_management.model.Category;
import java.util.List;

public interface CategoryCustomRepository {

    public List<Category> findPaginatedTotalFilteredCategories(
        SearchFilterQueryParameterDto searchQueryParams, Long userId);

    public long countTotalCategoriesByFilter(
        SearchFilterQueryParameterDto searchFilterQueryParameterDto, Long userId);
}
