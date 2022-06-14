package com.cleanup.todoc.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class TaskAndProject {
          @Embedded public Task task;
          @Relation(
                    parentColumn = "projectId",
                    entityColumn = "id"
          )
          public Project project;

          public Task getTask() { return  task; }
          public Project getProject() { return project; }
}
