package com.ignatdan.backend.projects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectsRepository extends JpaRepository<Projects, Integer> {
    @Query("SELECT p FROM Projects p WHERE p.user.userId = :userId")
    List<Projects> findByUser_UserId(@Param("userId") Integer userId);
}