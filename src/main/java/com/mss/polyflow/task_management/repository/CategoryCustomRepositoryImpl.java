package com.mss.polyflow.task_management.repository;

import com.mss.polyflow.task_management.dto.request.SearchFilterQueryParameterDto;
import com.mss.polyflow.task_management.model.Category;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Category> findTestAllCategoryByFilters(
        SearchFilterQueryParameterDto searchQueryParams, Long userId) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select * from category as ct");
        Map<String, Object> queryParamsTracker = new HashMap<>();

        if (userId != null) {
            queryBuilder.append(" ");
            queryBuilder.append("where ct.user_id = :userId");
            queryParamsTracker.put("userId", userId);
        }

        if (searchQueryParams.getName() != null) {
            queryBuilder.append(" ");
            queryBuilder.append("and lower(ct.name) like lower(concat('%',:name,'%'))");
            queryParamsTracker.put("name", searchQueryParams.getName());
        }

        long offSet = (searchQueryParams.getPageNumber() - 1) * searchQueryParams.getPageSize();
        queryBuilder.append(" ");
        queryBuilder.append("limit :pageSize");
        queryParamsTracker.put("pageSize", searchQueryParams.getPageSize());

        queryBuilder.append(" ");
        queryBuilder.append("offset :offSet");
        queryParamsTracker.put("offSet", offSet);

        Query createdNamedNativeQuery = entityManager.createNativeQuery(queryBuilder.toString(),
            Category.class);

        for (Map.Entry<String, Object> entry : queryParamsTracker.entrySet()) {
            createdNamedNativeQuery.setParameter(entry.getKey(), entry.getValue());
        }
        List<Category> categories = createdNamedNativeQuery.getResultList();
        return categories;
    }

    public long countTotalCategoriesByFilter(SearchFilterQueryParameterDto searchQueryParams,
        Long userId) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select count(*) from category as ct");
        Map<String, Object> queryParamsTracker = new HashMap<>();
        if (userId != null) {
            queryBuilder.append(" ");
            queryBuilder.append("where ct.user_id = :userId");
            queryParamsTracker.put("userId", userId);
        }

        long offSet = (searchQueryParams.getPageNumber() - 1) * searchQueryParams.getPageSize();
        queryBuilder.append(" ");
        queryBuilder.append("limit :pageSize");
        queryParamsTracker.put("pageSize", searchQueryParams.getPageSize());

        queryBuilder.append(" ");
        queryBuilder.append("offset :offSet");
        queryParamsTracker.put("offSet", offSet);

        Query createdNamedNativeQuery = entityManager.createNativeQuery(queryBuilder.toString());

        for (Map.Entry<String, Object> entry : queryParamsTracker.entrySet()) {
            createdNamedNativeQuery.setParameter(entry.getKey(), entry.getValue());
        }

        Number resultCount = (Number) createdNamedNativeQuery.getSingleResult();
        return resultCount.longValue();
    }

}
