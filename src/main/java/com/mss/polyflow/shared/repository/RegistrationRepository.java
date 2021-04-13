package com.mss.polyflow.shared.repository;

import com.mss.polyflow.shared.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<User, Integer> {

}
