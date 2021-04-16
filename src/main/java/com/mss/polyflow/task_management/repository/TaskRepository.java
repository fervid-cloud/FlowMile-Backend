package com.mss.polyflow.task_management.repository;

import com.mss.polyflow.task_management.model.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(nativeQuery = true, value = "select * from task as ts where ts.id = :taskId and ts.category_id = (select ct.id from category as ct where ct.id = ts.category_id and ct.user_id = :currentUserId)")
    Task findTask( @Param("taskId") Long taskId, @Param("currentUserId") Long currentUserId);


    // it is deprecated because it was returning empty list, which is not enough to determine if the categoryId exist for the user or not so basically it is not deprecated for efficiency reason just for user friendly reason
    @Deprecated
    @Query(nativeQuery = true, value = "select ts.* from task as ts join category as ct on ct.id = :categoryId and ct.user_id = :currentUserId")
    List<Task> findAllTasks( @Param("categoryId")  Long categoryId,  @Param("currentUserId") Long currentUserId);


/*  Persisting and deleting objects in JPA requires a transaction, that's why we should use a @Transactional
    annotation when using these derived delete queries, to make sure a transaction is running.
*/
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from task as ts where ts.id = :taskId and ts.category_id = (select ct.id from category as ct where ct.id = ts.category_id and ct.user_id = :currentUserId)")
    int deleteTask( @Param("taskId") Long taskId,  @Param("currentUserId") Long currentUserId);

    @Query(nativeQuery = true, value = "select count(ts.category_id) from task as ts where ts.category_id = :categoryId")
    long countTotalTasks(@Param("categoryId") Long categoryId);

    @Query(nativeQuery = true, value = "select * from task as ts where ts.category_id = :categoryId order by ts.creation_time desc limit :pageSize offset :offSet")
    List<Task> findTasks(@Param("categoryId") Long categoryId, @Param("pageSize") Long pageSize, @Param("offSet") Long offSet);
}
