package com.ignatdan.backend.tasks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Integer> {
    @Query("SELECT t FROM Tasks t WHERE t.project.projectId = :projectId")
    List<Tasks> findByProject_ProjectId(@Param("projectId") Integer projectId);
//    List<Tasks> findByUser_UserId(Integer userId);
}