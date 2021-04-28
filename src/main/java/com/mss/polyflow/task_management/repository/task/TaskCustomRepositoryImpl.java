package com.mss.polyflow.task_management.repository.task;

import com.mss.polyflow.task_management.dto.request.SearchFilterQueryParameterDto;
import com.mss.polyflow.task_management.model.Category;
import com.mss.polyflow.task_management.model.Task;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class TaskCustomRepositoryImpl implements TaskCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public long countTotalTasksByFilter(
        SearchFilterQueryParameterDto searchQueryParams,
        Long categoryId
    ) {

            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("select count(*) from task as ct");
            Map<String, Object> queryParamsTracker = new HashMap<>();

            queryBuilder.append(" where ct.category_id = :categoryId");
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
            queryBuilder.append("select * from task as ct");
            Map<String, Object> queryParamsTracker = new HashMap<>();

            queryBuilder.append(" where ct.category_id = :categoryId");
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

        String taskType = searchQueryParams.getType();
        if(taskType != null && taskType.length() > 0) {
            taskType = taskType.trim().toLowerCase();

            if(taskType.equals("pending") || taskType.equals("done")) {
                int requiredStatus = (searchQueryParams.getType().equals("pending")) ? 0 : 1;
                queryBuilder.append(" task_status = :requiredStatus");
                queryParamsTracker.put("taskStatus", requiredStatus);
            }
        }

        String requiredName = searchQueryParams.getName();
        if(requiredName != null && requiredName.trim().length() > 0) {
            requiredName = requiredName.trim().toLowerCase();
            queryBuilder.append(" and");
            // here use of prepared statement is correct as we are comparing the column value with our comparing input value
            // so basically column value is in picture, see some code lines below for more explanation
            queryBuilder.append(" lower(name) like lower(concat('%', :requiredName, '%'))");
            queryParamsTracker.put("requiredName", requiredName);
        }

        if(countQuery) {
            return;
        }

        String requiredOrdering = searchQueryParams.getSort();
        if(requiredOrdering != null && requiredOrdering.trim().length() > 0) {
            requiredOrdering = requiredOrdering.trim().toLowerCase();
            String [] orderingDetails = requiredOrdering.split(":");

            String orderCriteria = orderingDetails[0];
            if(orderCriteria.equals("creation_time") || orderCriteria.equals("modification_time") || orderCriteria.equals("name")) {
                String orderingDirection = "asc";
                if (orderingDetails.length > 1 && orderingDetails[1].equals("desc")) {
                    orderingDirection = "desc";
                }
                /*
                    https://stackoverflow.com/questions/12430208/using-a-prepared-statement-and-variable-bind-order-by-in-java-with-jdbc-driver
                    Placeholders ? can only be used for parameter values but not with column and sort order directions. So the standard way
                    to do this as is pointed e.g. here is to use String#format() or something similar to append your column name and order
                    value to your query.

                    First, the PreparedStatement placeholders (those ? things) are for column values only, not for table names,
                    column names, SQL functions/clauses, etcetera. Better use String#format() instead.
                    https://stackoverflow.com/questions/2857164/cannot-use-a-like-query-in-a-jdbc-preparedstatement/2857417#2857417
                */
                queryBuilder.append(String.format(" order by %s %s", orderCriteria, orderingDirection));
//                queryParamsTracker.put("orderCriteria", orderCriteria);
//                queryBuilder.append(String.format(" %s", orderingDirection));
//                queryParamsTracker.put("orderingDirection", orderingDirection);
            }
        }

    }
}
