package com.cleanup.todoc.model;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * This class define the relationship between the Task class and the Project class
 * It's an intermediate dataclass which holds pairings between instances of embedded objects
 * Query methods can return instances of this objects but it is not saved in database
 */
public class TaskAndProject {
      @Embedded private Task task;
      @Relation(
            parentColumn = "projectId",
            entityColumn = "id"
      )
      private Project project;

      public Task getTask() { return  task; }
      public Project getProject() { return project; }

      public void setTask(Task task) { this.task = task; }
      public void setProject(Project project) { this.project = project; }
}
