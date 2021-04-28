package com.mss.polyflow.task_management.repository.task;

import com.mss.polyflow.shared.utilities.functionality.UtilService;
import com.mss.polyflow.task_management.dto.request.SearchFilterQueryParameterDto;
import com.mss.polyflow.task_management.model.Category;
import com.mss.polyflow.task_management.model.Task;
import com.mss.polyflow.task_management.repository.CommonRepositoryUtility;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class TaskCustomRepositoryImpl implements TaskCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    Map<String, String> fieldColumnsMapping = new HashMap<>();
    {
        fieldColumnsMapping.put("creationTime", "creation_time");
        fieldColumnsMapping.put("modificationTime", "modification_time");
        fieldColumnsMapping.put("name", "name");
    }

    @Override
    public long countTotalTasksByFilter(
        SearchFilterQueryParameterDto searchQueryParams,
        Long categoryId
    ) {

            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("select count(*) from task as ts");
            Map<String, Object> queryParamsTracker = new HashMap<>();

            queryBuilder.append(" where ts.category_id = :categoryId");
            queryParamsTracker.put("categoryId", categoryId);

            buildRequiredCommonFilteringSortingQuery(true, searchQueryParams, queryBuilder, queryParamsTracker);

            Query createdNamedNativeQuery = entityManager.createNativeQuery(queryBuilder.toString());

            for (Map.Entry<String, Object> entry : queryParamsTracker.entrySet()) {
                createdNamedNativeQuery.setParameter(entry.getKey(), entry.getValue());
            }

            Number resultCount = (Number) createdNamedNativeQuery.getSingleResult();
            return resultCount.longValue();
    }

    @Override
    public List<Task> findPaginatedTotalFilteredTasks(
        SearchFilterQueryParameterDto searchQueryParams,
        Long categoryId
    ) {

            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("select * from task as ts");
            Map<String, Object> queryParamsTracker = new HashMap<>();

            queryBuilder.append(" where category_id = :categoryId");
            queryParamsTracker.put("categoryId", categoryId);


            buildRequiredCommonFilteringSortingQuery(false, searchQueryParams, queryBuilder, queryParamsTracker);

            long offSet = (searchQueryParams.getPage() - 1) * searchQueryParams.getPageSize();
            queryBuilder.append(" ");
            queryBuilder.append("limit :pageSize");
            queryParamsTracker.put("pageSize", searchQueryParams.getPageSize());

            queryBuilder.append(" ");
            queryBuilder.append("offset :offSet");
            queryParamsTracker.put("offSet", offSet);

            Query createdNamedNativeQuery = entityManager.createNativeQuery(queryBuilder.toString(),
                Task.class);

            for (Map.Entry<String, Object> entry : queryParamsTracker.entrySet()) {
                createdNamedNativeQuery.setParameter(entry.getKey(), entry.getValue());
            }

            List<Task> tasks = createdNamedNativeQuery.getResultList();
            return tasks;
    }

    private void buildRequiredCommonFilteringSortingQuery(boolean countQuery, SearchFilterQueryParameterDto searchQueryParams, StringBuilder queryBuilder, Map<String, Object> queryParamsTracker) {

        CommonRepositoryUtility.addType(searchQueryParams.getType(), queryBuilder, queryParamsTracker);

        CommonRepositoryUtility.addName(searchQueryParams.getName(), queryBuilder, queryParamsTracker);

        if(countQuery) {
            return;
        }

       CommonRepositoryUtility.addOrdering(searchQueryParams.getSort(), queryBuilder, fieldColumnsMapping);

    }


}

/**
 * Notes
 *
 *  https://stackoverflow.com/questions/12430208/using-a-prepared-statement-and-variable-bind-order-by-in-java-with-jdbc-driver
 *  Placeholders ? can only be used for parameter values but not with column and sort order directions. So the standard way
 *  to do this as is pointed e.g. here is to use String#format() or something similar to append your column name and order
 *  value to your query.
 *
 *  First, the PreparedStatement placeholders (those ? things) are for column values only, not for table names,
 *  column names, SQL functions/clauses, etcetera. Better use String#format() instead.
 *  https://stackoverflow.com/questions/2857164/cannot-use-a-like-query-in-a-jdbc-preparedstatement/2857417#2857417
 *
 *
 *
 */
