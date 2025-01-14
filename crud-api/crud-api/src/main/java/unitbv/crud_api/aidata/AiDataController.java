package unitbv.crud_api.aidata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unitbv.crud_api.tasks.Tasks;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aidata")
public class AiDataController {
    private final AiDataService aiDataService;

    @Autowired
    public AiDataController(AiDataService aiDataService) {
        this.aiDataService = aiDataService;
    }

    @GetMapping
    public List<AiData> getAllAiData() {
        return aiDataService.getAllAiData();
    }

    @GetMapping("/task/{taskId}")
    public List<AiData> getAiDataByTaskId(@PathVariable Integer taskId) {
        return aiDataService.getAiDataByTaskId(taskId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAiDataById(@PathVariable Integer id) {
        return aiDataService.getAiDataById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createAiData(@RequestBody AiData aiData, @RequestParam Integer taskId) {
        Tasks task = new Tasks();
        task.setTaskId(taskId);
        return aiDataService.createAiData(aiData, task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAiData(@PathVariable Integer id, @RequestBody AiData updatedAiData) {
        return aiDataService.updateAiData(id, updatedAiData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAiData(@PathVariable Integer id) {
        return aiDataService.deleteAiData(id);
    }
}