package com.mss.polyflow.shared.repository;

import com.mss.polyflow.shared.model.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

}