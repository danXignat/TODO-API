package com.ignatdan.backend.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Integer> {
    @Query("SELECT t FROM Tags t WHERE t.project.projectId = :projectId")
    List<Tags> findByProject_ProjectId(@Param("projectId") Integer projectId);

    @Query("SELECT t FROM Tags t WHERE t.task.taskId = :taskId")
    List<Tags> findByTask_TaskId(@Param("taskId") Integer taskId);
}