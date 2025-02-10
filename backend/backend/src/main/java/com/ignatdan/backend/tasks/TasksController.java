package com.ignatdan.backend.tasks;

import com.ignatdan.backend.projects.Projects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TasksController {
    private final TasksService tasksService;

    @Autowired
    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping
    public List<Tasks> getAllTasks() {
        return tasksService.getAllTasks();
    }

    @GetMapping("/project/{projectId}")
    public List<Tasks> getTasksByProjectId(@PathVariable Integer projectId) {
        return tasksService.getTasksByProjectId(projectId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTaskById(@PathVariable Integer id) {
        return tasksService.getTaskById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody Tasks task, @RequestParam Integer projectId) {
        Projects project = new Projects();
        project.setProjectId(projectId);
        return tasksService.createTask(task, project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable Integer id, @RequestBody Tasks updatedTask) {
        return tasksService.updateTask(id, updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Integer id) {
        return tasksService.deleteTask(id);
    }
}
