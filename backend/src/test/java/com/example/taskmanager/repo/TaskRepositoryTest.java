package com.example.taskmanager.repo;

import com.example.taskmanager.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
class TaskRepositoryTest {
    @Autowired TaskRepository repo;

    @Test
    void savesAndFindsByStatus() {
        Task a = new Task(); a.setTitle("A"); a.setStatus("OPEN");
        Task b = new Task(); b.setTitle("B"); b.setStatus("DONE");
        repo.save(a); repo.save(b);

        List<Task> open = repo.findByStatusIgnoreCase("OPEN");
        List<Task> done = repo.findByStatusIgnoreCase("DONE");

        assertThat(open).extracting(Task::getTitle).contains("A");
        assertThat(done).extracting(Task::getTitle).contains("B");
    }
}
