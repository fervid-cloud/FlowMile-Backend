package com.example.demo.repository;

import com.example.demo.model.RoleServicePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleServicePermissionRepository extends JpaRepository<RoleServicePermission, Integer> {

}
