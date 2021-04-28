package com.mss.polyflow.task_management.repository.task;

import com.mss.polyflow.task_management.dto.request.SearchFilterQueryParameterDto;
import com.mss.polyflow.task_management.model.Category;
import com.mss.polyflow.task_management.model.Task;
import java.util.List;

public interface TaskCustomRepository {

    public long countTotalTasksByFilter(
        SearchFilterQueryParameterDto searchQueryParams, Long categoryId);

    public List<Task> findPaginatedTotalFilteredTasks(
        SearchFilterQueryParameterDto searchQueryParams, Long categoryId);
}
