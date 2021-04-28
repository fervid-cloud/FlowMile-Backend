package com.mss.polyflow.task_management.repository.category;

import com.mss.polyflow.task_management.dto.request.SearchFilterQueryParameterDto;
import com.mss.polyflow.task_management.model.Category;
import java.util.List;

public interface CategoryCustomRepository {

    public long countTotalCategoriesByFilter(
        SearchFilterQueryParameterDto searchFilterQueryParameterDto, Long userId);

    public List<Category> findPaginatedTotalFilteredCategories(
        SearchFilterQueryParameterDto searchQueryParams, Long userId);
}
