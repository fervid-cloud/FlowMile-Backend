//package com.mss.polyflow.task_management.repository;
//
//import com.mss.polyflow.task_management.model.Category;
//import java.util.List;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//
//public class CategoryTestImpl implements CategoryCustomRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    public List<Category> findTestAllCategoryByFilters(String customQuery) {
//        Query query =  entityManager.createNativeQuery("select * from category where user_id = 1", Category.class);
//        List<Category> categories = query.getResultList();
//        return categories;
//    }
//
//}