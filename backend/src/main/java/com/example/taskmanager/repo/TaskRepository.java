package com.example.taskmanager.repo;

import com.example.taskmanager.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByStatusIgnoreCase(String status);
}
