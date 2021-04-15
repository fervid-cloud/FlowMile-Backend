package com.mss.polyflow.task_management.repository;

import com.mss.polyflow.task_management.model.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByUserId(Long currentUserId);

    Optional<Category> findByIdAndUserId(Long categoryId, Long currentUserId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from category as ct where ct.id = :categoryId && ct.user_id = :currentUserId")
    int deleteCategory(@Param("categoryId") Long categoryId, @Param("currentUserId") Long currentUserId);

    @Query(nativeQuery = true, value = "select count(ct.id) from category as ct where ct.user_id = :currentUserId")
    long countTotalCategory(@Param("currentUserId") Long currentUserId);

    @Query(nativeQuery = true, value = "select * from category as ct where ct.user_id = :currentUserId limit :pageSize offset :offSet")
    List<Category> findCategories(@Param("pageSize") Long pageSize, @Param("offSet")  Long offSet, @Param("currentUserId")  Long currentUserId);
}
