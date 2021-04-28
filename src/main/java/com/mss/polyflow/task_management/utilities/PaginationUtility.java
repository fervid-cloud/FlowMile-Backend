package com.mss.polyflow.task_management.utilities;

import com.mss.polyflow.shared.exception.MiscellaneousException;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

public class PaginationUtility {

    public static final Long DEFAULT_PAGE_SIZE = 20l;

    public static final Long DEFAULT_PAGE_NUMBER = 1l;

    public static final String DEFAULT_PAGE_SIZE_S = "20";

    public static final String DEFAULT_PAGE_NUMBER_S = "1";

    public static final Long MAX_PAGE_SIZE = 30L;

    public static final Long MIN_PAGE_SIZE = 10L;


    public static PaginationWrapper toPaginationWrapper(Long pageSize, Long pageNumber,
        Long totalPages, Long totalCount, Object results) {
        return new PaginationWrapper()
                   .setPageSize(pageSize)
                   .setPageNumber(pageNumber)
                   .setTotalPages(totalPages)
                   .setTotalCount(totalCount)
                   .setResults(results);
    }

    public static void requiredPageInputValidation(Long pageSize, Long pageNumber) {

        if (pageSize < MIN_PAGE_SIZE || pageSize > MAX_PAGE_SIZE) {
            throw new MiscellaneousException(String.format(
                "Page Size should be between %s and %s", MIN_PAGE_SIZE, MAX_PAGE_SIZE));
        }

        if (pageNumber <= 0) {
            throw new MiscellaneousException(
                String.format("Page number should be greater than %s", 0));
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class PaginationWrapper {

        private long pageNumber = 1;
        private Object results = new ArrayList<>();
        private long totalPages;
        private long totalCount;
        private long pageSize;

        public PaginationWrapper(long pageNumber) {
            this.pageNumber = pageNumber;
        }
    }

}


