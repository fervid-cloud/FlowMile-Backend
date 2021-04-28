package com.mss.polyflow.task_management.repository.category;

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
public interface CategoryRepository extends JpaRepository<Category, Long> , CategoryCustomRepository {

    @Query(nativeQuery = true, value = "select ct.id from category as ct where ct.id = :categoryId && ct.user_id = :currentUserId")
    Long customFindByIdAndUserId(@Param("categoryId") Long categoryId, @Param("currentUserId") Long currentUserId);

    Optional<Category> findByIdAndUserId( Long categoryId,  Long currentUserId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from category as ct where ct.id = :categoryId && ct.user_id = :currentUserId")
    int deleteCategory(@Param("categoryId") Long categoryId, @Param("currentUserId") Long currentUserId);










    // this way also works with using like but problematic in using lowercase with hibernate as it gives error in that case
//    @Query(nativeQuery = true, value = "select * from category as ct where ct.user_id = :currentUserId and lower(ct.name) like %:givenCategoryName% order by ct.creation_time desc limit :pageSize offset :offSet")
    @Deprecated
    @Query(nativeQuery = true, value = "select * from category as ct where ct.user_id = :currentUserId and lower(ct.name) like  lower(concat('%',:givenCategoryName,'%')) order by ct.creation_time desc limit :pageSize offset :offSet")
    List<Category> filterCategoriesByName(@Param("givenCategoryName") String givenCategoryName,  @Param("pageSize") Long pageSize, @Param("offSet")  Long offSet, @Param("currentUserId")  Long currentUserId);


    //usually categories will be less than tasks so is using concat and lower function , but in case of task, already lowercase letter will be provided to avoid doing all the mentioned operations at database layer
    @Deprecated
    @Query(nativeQuery = true, value = "select count(ct.id) from category as ct where ct.user_id = :currentUserId and lower(ct.name) like lower(concat('%',:givenCategoryName,'%'))")
    long countTotalCategoryForFilter(@Param("givenCategoryName") String givenCategoryName, @Param("currentUserId") Long currentUserId);

    // basically this doesn't work to avoid sql injection, so whatever value we passed is not treated as some sql statement
    // but some value and so that is why it doesn't work
    @Deprecated
    @Query(nativeQuery = true, value = " select * from (:customQuery) as customResult")
    List<Category> countTest(@Param("customQuery") Object customQuery);
}
