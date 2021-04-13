package com.mss.polyflow.shared.repository;

import com.mss.polyflow.shared.model.RoleServicePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleServicePermissionRepository extends JpaRepository<RoleServicePermission, Integer> {

}
