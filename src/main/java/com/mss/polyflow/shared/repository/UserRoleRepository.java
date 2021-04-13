package com.mss.polyflow.shared.repository;

import com.mss.polyflow.shared.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

}
