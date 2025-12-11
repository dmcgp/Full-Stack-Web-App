package com.example.taskmanager.demo;

import com.example.taskmanager.auth.AuthService;
import com.example.taskmanager.auth.Role;
import com.example.taskmanager.auth.UserRepository;
import com.example.taskmanager.repo.TaskRepository;
import com.example.taskmanager.task.CreateTaskRequest;
import com.example.taskmanager.task.TaskController;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final TaskController tasks;
    private final TaskRepository taskRepo;
    private final UserRepository userRepo;
    private final AuthService authService;

    public DemoController(TaskController tasks, TaskRepository taskRepo, UserRepository userRepo, AuthService authService) {
        this.tasks = tasks;
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.authService = authService;
    }

    @PostMapping("/seed")
    public ResponseEntity<String> seed() {
        tasks.create(new CreateTaskRequest("Buy coffee"));
        tasks.create(new CreateTaskRequest("Check emails"));
        tasks.create(new CreateTaskRequest("Plan today"));
        tasks.create(new CreateTaskRequest("Standup notes"));
        tasks.create(new CreateTaskRequest("Review PRs"));
        return ResponseEntity.ok("seeded");
    }

    @PostMapping("/reset")
    @Transactional
    public ResponseEntity<String> reset() {
        // Clear tasks
        taskRepo.deleteAll();
        // Ensure demo and admin users exist (idempotent)
        userRepo.findByEmail("demo@demo.test").orElseGet(() -> authService.register("demo@demo.test", "Demo123!", Role.USER));
        userRepo.findByEmail("admin@demo.test").orElseGet(() -> authService.register("admin@demo.test", "Admin123!", Role.ADMIN));
        // Reseed demo tasks
        tasks.create(new CreateTaskRequest("Buy coffee"));
        tasks.create(new CreateTaskRequest("Check emails"));
        tasks.create(new CreateTaskRequest("Plan today"));
        tasks.create(new CreateTaskRequest("Standup notes"));
        tasks.create(new CreateTaskRequest("Review PRs"));
        return ResponseEntity.ok("reset");
    }
}
