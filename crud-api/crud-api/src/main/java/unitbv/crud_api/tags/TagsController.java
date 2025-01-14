package unitbv.crud_api.tags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unitbv.crud_api.projects.Projects;
import unitbv.crud_api.tasks.Tasks;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagsController {
    private final TagsService tagsService;

    @Autowired
    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping
    public List<Tags> getAllTags() {
        return tagsService.getAllTags();
    }

    @GetMapping("/project/{projectId}")
    public List<Tags> getTagsByProjectId(@PathVariable Integer projectId) {
        return tagsService.getTagsByProjectId(projectId);
    }

    @GetMapping("/task/{taskId}")
    public List<Tags> getTagsByTaskId(@PathVariable Integer taskId) {
        return tagsService.getTagsByTaskId(taskId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTagById(@PathVariable Integer id) {
        return tagsService.getTagById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createTag(@RequestBody Tags tag,
                                            @RequestParam Integer projectId,
                                            @RequestParam Integer taskId) {
        Projects project = new Projects();
        project.setProjectId(projectId);

        Tasks task = new Tasks();
        task.setTaskId(taskId);

        return tagsService.createTag(tag, project, task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTag(@PathVariable Integer id, @RequestBody Tags updatedTag) {
        return tagsService.updateTag(id, updatedTag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable Integer id) {
        return tagsService.deleteTag(id);
    }
}