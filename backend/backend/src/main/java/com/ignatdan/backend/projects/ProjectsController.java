package com.ignatdan.backend.projects;

import com.ignatdan.backend.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectsController {
    private final ProjectsService projectsService;

    @Autowired
    public ProjectsController(ProjectsService projectsService) {
        this.projectsService = projectsService;
    }

    @GetMapping
    public List<Projects> getAllProjects() {
        return projectsService.getAllProjects();
    }

    @GetMapping("/user/{userId}")
    public List<Projects> getProjectsByUserId(@PathVariable Integer userId) {
        return projectsService.getProjectsByUserId(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProjectById(@PathVariable Integer id) {
        return projectsService.getProjectById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createProject(@RequestBody Projects project, @RequestParam Integer userId) {
        try {
            Users user = new Users();
            user.setUserId(userId);
            return projectsService.createProject(project, user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating project: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProject(@PathVariable Integer id, @RequestBody Projects updatedProject) {
        return projectsService.updateProject(id, updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProject(@PathVariable Integer id) {
        return projectsService.deleteProject(id);
    }
}
