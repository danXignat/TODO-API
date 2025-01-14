package unitbv.crud_api.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import unitbv.crud_api.projects.Projects;

import java.util.List;
import java.util.Optional;

@Service
public class TasksService {
    private final TasksRepository tasksRepository;

    @Autowired
    public TasksService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public List<Tasks> getAllTasks() {
        return tasksRepository.findAll();
    }

    public List<Tasks> getTasksByProjectId(Integer projectId) {
        return tasksRepository.findByProject_ProjectId(projectId);
    }

    public ResponseEntity<Object> getTaskById(Integer taskId) {
        Optional<Tasks> taskOptional = tasksRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        return ResponseEntity.ok(taskOptional.get());
    }

    public ResponseEntity<Object> createTask(Tasks task, Projects project) {
        task.setProject(project);
        tasksRepository.save(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateTask(Integer taskId, Tasks updatedTask) {
        Optional<Tasks> taskOptional = tasksRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        Tasks existingTask = taskOptional.get();
        existingTask.setTaskName(updatedTask.getTaskName());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setEndDate(updatedTask.getEndDate());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setPriority(updatedTask.getPriority());

        tasksRepository.save(existingTask);
        return ResponseEntity.ok(existingTask);
    }

    public ResponseEntity<Object> deleteTask(Integer taskId) {
        Optional<Tasks> taskOptional = tasksRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        tasksRepository.deleteById(taskId);
        return ResponseEntity.ok().body("Task deleted successfully");
    }
}