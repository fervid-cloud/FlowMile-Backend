package com.mss.polyflow.task_management.repository.category;

import com.mss.polyflow.shared.utilities.functionality.UtilService;
import com.mss.polyflow.task_management.dto.request.SearchFilterQueryParameterDto;
import com.mss.polyflow.task_management.model.Category;
import com.mss.polyflow.task_management.repository.CommonRepositoryUtility;
import com.mss.polyflow.task_management.repository.task.TaskCustomRepositoryImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.security.authentication.BadCredentialsException;

public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    Map<String, String> fieldColumnsMapping = new HashMap<>();
    {
        fieldColumnsMapping.put("creationTime", "creation_time");
        fieldColumnsMapping.put("modificationTime", "modification_time");
        fieldColumnsMapping.put("name", "name");
    }

    public long countTotalCategoriesByFilter(SearchFilterQueryParameterDto searchQueryParams,
        Long userId) {
        if(userId == null) {
            throw new BadCredentialsException("UnAuthorized user");
        }
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select count(*) from category as ct");
        Map<String, Object> queryParamsTracker = new HashMap<>();

        queryBuilder.append(" where ct.user_id = :userId");
        queryParamsTracker.put("userId", userId);

        buildRequiredCommonFilteringSortingQuery(true, searchQueryParams, queryBuilder, queryParamsTracker);

        Query createdNamedNativeQuery = entityManager.createNativeQuery(queryBuilder.toString());

        for (Map.Entry<String, Object> entry : queryParamsTracker.entrySet()) {
            createdNamedNativeQuery.setParameter(entry.getKey(), entry.getValue());
        }

        Number resultCount = (Number) createdNamedNativeQuery.getSingleResult();
        return resultCount.longValue();
    }

    @Override
    public List<Category> findPaginatedTotalFilteredCategories(

        SearchFilterQueryParameterDto searchQueryParams, Long userId) {
        if(userId == null) {
            throw new BadCredentialsException("UnAuthorized user");
        }
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select * from category as ct");
        Map<String, Object> queryParamsTracker = new HashMap<>();


        queryBuilder.append(" ");
        queryBuilder.append("where ct.user_id = :userId");
        queryParamsTracker.put("userId", userId);


        buildRequiredCommonFilteringSortingQuery(false, searchQueryParams, queryBuilder, queryParamsTracker);

        long offSet = (searchQueryParams.getPage() - 1) * searchQueryParams.getPageSize();
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


    private void buildRequiredCommonFilteringSortingQuery(boolean countQuery, SearchFilterQueryParameterDto searchQueryParams, StringBuilder queryBuilder, Map<String, Object> queryParamsTracker) {

        CommonRepositoryUtility.addName(searchQueryParams.getName(), queryBuilder, queryParamsTracker);

        if(countQuery) {
            return;
        }

        CommonRepositoryUtility
                .addOrdering(searchQueryParams.getSort(), queryBuilder, fieldColumnsMapping);
    }


}
