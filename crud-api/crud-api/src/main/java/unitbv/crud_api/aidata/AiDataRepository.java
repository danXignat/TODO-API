package unitbv.crud_api.aidata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AiDataRepository extends JpaRepository<AiData, Integer> {
    @Query("SELECT a FROM AiData a WHERE a.task.taskId = :taskId")
    List<AiData> findByTask_TaskId(@Param("taskId") Integer taskId);
}