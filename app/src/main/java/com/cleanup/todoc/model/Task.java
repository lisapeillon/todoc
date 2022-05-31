package com.cleanup.todoc.model;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;

/**
 * Model for the tasks of the application.
 * @author Gaëtan HERFRAY
 */
@Entity
public class Task {
    // The unique identifier of the task
    @PrimaryKey(autoGenerate = true)
    private long id;

    // The unique identifier of the project associated to the task
    @ColumnInfo
    private long projectId;

    // The name of the task
    // Suppress warning because setName is called in constructor
    @SuppressWarnings("NullableProblems")
    @NonNull
    @ColumnInfo
    private String name;

    //The timestamp when the task has been created
    @ColumnInfo
    private long creationTimestamp;

    /**
     * Instantiates a new Task.
     *
     * @param id                the unique identifier of the task to set
     * @param projectId         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    public Task(long id, long projectId, @NonNull String name, long creationTimestamp) {
        this.setId(id);
        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }

    /**
     * Returns the unique identifier of the task.
     *
     * @return the unique identifier of the task
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the task.
     *
     * @param id the unique identifier of the task to set
     */
    private void setId(long id) {
        this.id = id;
    }

    /**
     * Sets the unique identifier of the project associated to the task.
     * @param projectId the unique identifier of the project associated to the task to set
     */
    private void setProjectId(long projectId) {
        this.projectId = projectId;
    }
    
    /**
     * Get the unique identifier of the project associated to the task
     * @return projectId
     */
    public long getProjectId() { return projectId; }

    /**
     * Returns the project associated to the task.
     * @return the project associated to the task
     */
    @Nullable
    public Project getProject() {
        return Project.getProjectById(projectId);
    }

    /**
     * Returns the name of the task.
     * @return the name of the task
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task.
     * @param name the name of the task to set
     */
    private void setName(@NonNull String name) {
        this.name = name;
    }

    /**
     * Sets the timestamp when the task has been created.
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    private void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
    
    /**
     * Get the timestamp when the task has been created
     * @return creationTimestamp
     */
    public long getCreationTimestamp() { return creationTimestamp; }
}
