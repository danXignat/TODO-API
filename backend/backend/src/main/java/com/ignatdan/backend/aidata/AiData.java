package com.ignatdan.backend.aidata;

import com.ignatdan.backend.tasks.Tasks;
import jakarta.persistence.*;

@Entity
@Table(name = "aidata")
public class AiData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer aidataId;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Tasks task;

    @Column(name = "modl")
    private String modl;

    @Column(name = "name")
    private String name;

    @Column(name = "processed_data")
    private String processedData;

    // Getters and Setters
    public Integer getAidataId() {
        return aidataId;
    }

    public void setAidataId(Integer aidataId) {
        this.aidataId = aidataId;
    }

    public Tasks getTask() {
        return task;
    }

    public void setTask(Tasks task) {
        this.task = task;
    }

    public String getModl() {
        return modl;
    }

    public void setModl(String modl) {
        this.modl = modl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcessedData() {
        return processedData;
    }

    public void setProcessedData(String processedData) {
        this.processedData = processedData;
    }
}