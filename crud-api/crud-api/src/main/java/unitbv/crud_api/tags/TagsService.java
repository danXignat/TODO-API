package unitbv.crud_api.tags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import unitbv.crud_api.projects.Projects;
import unitbv.crud_api.tasks.Tasks;

import java.util.List;
import java.util.Optional;

@Service
public class TagsService {
    private final TagsRepository tagsRepository;

    @Autowired
    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public List<Tags> getAllTags() {
        return tagsRepository.findAll();
    }

    public List<Tags> getTagsByProjectId(Integer projectId) {
        return tagsRepository.findByProject_ProjectId(projectId);
    }

    public List<Tags> getTagsByTaskId(Integer taskId) {
        return tagsRepository.findByTask_TaskId(taskId);
    }

    public ResponseEntity<Object> getTagById(Integer tagId) {
        Optional<Tags> tagOptional = tagsRepository.findById(tagId);
        if (tagOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag not found");
        }
        return ResponseEntity.ok(tagOptional.get());
    }

    public ResponseEntity<Object> createTag(Tags tag, Projects project, Tasks task) {
        tag.setProject(project);
        tag.setTask(task);
        tagsRepository.save(tag);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateTag(Integer tagId, Tags updatedTag) {
        Optional<Tags> tagOptional = tagsRepository.findById(tagId);
        if (tagOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag not found");
        }

        Tags existingTag = tagOptional.get();
        existingTag.setNotes(updatedTag.getNotes());
        existingTag.setAssignedNote(updatedTag.getAssignedNote());

        tagsRepository.save(existingTag);
        return ResponseEntity.ok(existingTag);
    }

    public ResponseEntity<Object> deleteTag(Integer tagId) {
        Optional<Tags> tagOptional = tagsRepository.findById(tagId);
        if (tagOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag not found");
        }
        tagsRepository.deleteById(tagId);
        return ResponseEntity.ok().body("Tag deleted successfully");
    }
}
