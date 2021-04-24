package com.mss.polyflow.task_management.repository;

import com.mss.polyflow.task_management.dto.request.SearchFilterQueryParameterDto;
import com.mss.polyflow.task_management.model.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryCustomRepository {

    public List<Category> findTestAllCategoryByFilters(SearchFilterQueryParameterDto searchQueryParams, Long userId);

    public long countTotalCategoriesByFilter(SearchFilterQueryParameterDto searchFilterQueryParameterDto, Long userId);
}
