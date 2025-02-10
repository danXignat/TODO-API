package com.ignatdan.backend.projects;

import com.ignatdan.backend.users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectsService {
    private final ProjectsRepository projectsRepository;

    @Autowired
    public ProjectsService(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    public List<Projects> getAllProjects() {
        return projectsRepository.findAll();
    }

    public List<Projects> getProjectsByUserId(Integer userId) {
        return projectsRepository.findByUser_UserId(userId);
    }

    public ResponseEntity<Object> getProjectById(Integer projectId) {
        Optional<Projects> projectOptional = projectsRepository.findById(projectId);
        if (projectOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
        }
        return ResponseEntity.ok(projectOptional.get());
    }

    public ResponseEntity<Object> createProject(Projects project, Users user) {
        project.setUser(user);  // Associate project with user
        projectsRepository.save(project);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateProject(Integer projectId, Projects updatedProject) {
        Optional<Projects> projectOptional = projectsRepository.findById(projectId);
        if (projectOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
        }

        Projects existingProject = projectOptional.get();
        existingProject.setProjectName(updatedProject.getProjectName());
        existingProject.setDescription(updatedProject.getDescription());
        existingProject.setStartDate(updatedProject.getStartDate());
        existingProject.setEndDate(updatedProject.getEndDate());

        projectsRepository.save(existingProject);
        return ResponseEntity.ok(existingProject);
    }

    public ResponseEntity<Object> deleteProject(Integer projectId) {
        Optional<Projects> projectOptional = projectsRepository.findById(projectId);
        if (projectOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
        }
        projectsRepository.deleteById(projectId);
        return ResponseEntity.ok().body("Project deleted successfully");
    }
}
