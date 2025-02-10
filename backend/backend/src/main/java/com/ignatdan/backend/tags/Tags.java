package com.ignatdan.backend.tags;

import com.ignatdan.backend.projects.Projects;
import com.ignatdan.backend.tasks.Tasks;
import jakarta.persistence.*;

@Entity
@Table(name = "tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer tagId;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Projects project;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Tasks task;

    @Column(name = "notes")
    private String notes;

    @Column(name = "assigned_note")
    private String assignedNote;

    // Getters and Setters
    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Projects getProject() {
        return project;
    }

    public void setProject(Projects project) {
        this.project = project;
    }

    public Tasks getTask() {
        return task;
    }

    public void setTask(Tasks task) {
        this.task = task;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAssignedNote() {
        return assignedNote;
    }

    public void setAssignedNote(String assignedNote) {
        this.assignedNote = assignedNote;
    }
}