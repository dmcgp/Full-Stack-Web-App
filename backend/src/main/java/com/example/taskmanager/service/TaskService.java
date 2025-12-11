package com.example.taskmanager.service;

import com.example.taskmanager.domain.Task;
import com.example.taskmanager.repo.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo) { this.repo = repo; }

    public List<Task> list(String status) {
        if (status == null) return repo.findAll();
        return repo.findByStatusIgnoreCase(status);
    }

    public Task create(String title) {
        Task t = new Task();
        t.setTitle(title);
        t.setStatus("OPEN");
        return repo.save(t);
    }

    public Optional<Task> update(UUID id, String title, String status) {
        return repo.findById(id).map(t -> {
            if (title != null) t.setTitle(title);
            if (status != null) t.setStatus(status);
            return repo.save(t);
        });
    }

    public void delete(UUID id) { repo.deleteById(id); }
}
