package com.example.taskmanager.task;

import com.example.taskmanager.domain.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// request records moved to their own files

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) { this.service = service; }

    @GetMapping
    public List<Task> list(@RequestParam(name = "status", required = false) String status) {
        return service.list(status);
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody CreateTaskRequest req) {
        return ResponseEntity.ok(service.create(req.title()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable UUID id, @RequestBody UpdateTaskRequest req) {
        return service.update(id, req.title(), req.status())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
