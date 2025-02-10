package com.ignatdan.backend.aidata;

import com.ignatdan.backend.tasks.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AiDataService {
    private final AiDataRepository aiDataRepository;

    @Autowired
    public AiDataService(AiDataRepository aiDataRepository) {
        this.aiDataRepository = aiDataRepository;
    }

    public List<AiData> getAllAiData() {
        return aiDataRepository.findAll();
    }

    public List<AiData> getAiDataByTaskId(Integer taskId) {
        return aiDataRepository.findByTask_TaskId(taskId);
    }

    public ResponseEntity<Object> getAiDataById(Integer aiDataId) {
        Optional<AiData> aiDataOptional = aiDataRepository.findById(aiDataId);
        if (aiDataOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AI Data not found");
        }
        return ResponseEntity.ok(aiDataOptional.get());
    }

    public ResponseEntity<Object> createAiData(AiData aiData, Tasks task) {
        aiData.setTask(task);
        aiDataRepository.save(aiData);
        return new ResponseEntity<>(aiData, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateAiData(Integer aiDataId, AiData updatedAiData) {
        Optional<AiData> aiDataOptional = aiDataRepository.findById(aiDataId);
        if (aiDataOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AI Data not found");
        }

        AiData existingAiData = aiDataOptional.get();
        existingAiData.setModl(updatedAiData.getModl());
        existingAiData.setName(updatedAiData.getName());
        existingAiData.setProcessedData(updatedAiData.getProcessedData());

        aiDataRepository.save(existingAiData);
        return ResponseEntity.ok(existingAiData);
    }

    public ResponseEntity<Object> deleteAiData(Integer aiDataId) {
        Optional<AiData> aiDataOptional = aiDataRepository.findById(aiDataId);
        if (aiDataOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AI Data not found");
        }
        aiDataRepository.deleteById(aiDataId);
        return ResponseEntity.ok().body("AI Data deleted successfully");
    }
}
