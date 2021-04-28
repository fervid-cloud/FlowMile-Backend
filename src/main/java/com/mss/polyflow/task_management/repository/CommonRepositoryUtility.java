package com.mss.polyflow.task_management.repository;

import java.util.Map;

public class CommonRepositoryUtility {


    public static void addOrdering(String sort,  StringBuilder queryBuilder, Map<String, String> fieldColumnsMapping) {
        String requiredOrdering = sort;
        if(requiredOrdering != null && requiredOrdering.trim().length() > 0) {
            requiredOrdering = requiredOrdering.trim();
            String[] orderingDetails = requiredOrdering.split(":");
            String orderCriteria = orderingDetails[0];
            if (fieldColumnsMapping.containsKey(orderCriteria)) {
                String orderingDirection = "asc";
                orderCriteria = fieldColumnsMapping.get(orderCriteria);
                if (orderingDetails.length > 1 && orderingDetails[1].equals("desc")) {
                    orderingDirection = "desc";
                }

                queryBuilder.append(String.format(" order by %s %s", orderCriteria, orderingDirection));
//                queryParamsTracker.put("orderCriteria", orderCriteria);
//                queryBuilder.append(String.format(" %s", orderingDirection));
//                queryParamsTracker.put("orderingDirection", orderingDirection);
            }
        }
    }

    public static void addName(String name, StringBuilder queryBuilder, Map<String, Object> queryParamsTracker) {
        String requiredName = name;
        if(requiredName != null && requiredName.trim().length() > 0) {
            requiredName = requiredName.trim().toLowerCase();
            queryBuilder.append(" and");
            // here use of prepared statement is correct as we are comparing the column value with our comparing input value
            // so basically column value is in picture, see some code lines below for more explanation
            queryBuilder.append(" lower(name) like lower(concat('%', :requiredName, '%'))");
            queryParamsTracker.put("requiredName", requiredName);
        }

    }

    public static void addType(String taskType, StringBuilder queryBuilder, Map<String, Object> queryParamsTracker) {
        if(taskType != null && taskType.length() > 0) {
            taskType = taskType.trim().toLowerCase();

            if(taskType.equals("pending") || taskType.equals("done")) {
                queryBuilder.append(" and");
                int requiredStatus = taskType.equals("pending") ? 0 : 1;
                queryBuilder.append(" task_status = :taskStatus");
                queryParamsTracker.put("taskStatus", requiredStatus);
            }
        }
    }
}


/*
NOTES

For avoiding sql injection threats you firstly need to remove appending parameters to your query.
When you're appending parameters in your app, the attacker can hijack your sql code (with
apostrophes and other means for example)

For example:

If your query is "select * from table where name="+id

The attacker can pass to the field values such as: 'John' or 1=1; ->sees all your records in
this table

Or even 'John' or 1=1;Delete all from users;' -> deleting all entries from users table.

Hijacking queries can be avoided via mechanisms such as input sanitization, input
whitelisting/blacklisting(removing unwanted characters from the input/ defining a list of
allowed or unnalowed characters). Most modern framerowks such as JDBC/JPA/Hibernate can offer
protection from this threat.

 */

