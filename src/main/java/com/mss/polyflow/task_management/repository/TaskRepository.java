package com.mss.polyflow.task_management.repository;

import com.mss.polyflow.task_management.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
