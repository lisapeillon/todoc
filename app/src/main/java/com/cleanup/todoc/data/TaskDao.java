package com.cleanup.todoc.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskAndProject;

import java.util.List;

@Dao
public interface TaskDao {
      @Transaction
      @Query("SELECT * FROM Task")
      LiveData<List<TaskAndProject>> getAllTasksAndProjects();

      @Transaction
      @Query("SELECT * FROM Task ORDER BY name ASC")
      LiveData<List<TaskAndProject>> getTasksFromAToZ();

      @Transaction
      @Query("SELECT * FROM Task ORDER BY name DESC")
      LiveData<List<TaskAndProject>> getTasksFromZToA();

      @Transaction
      @Query("SELECT * FROM Task ORDER BY creationTimestamp DESC")
      LiveData<List<TaskAndProject>> getTasksFromRecentToOld();

      @Transaction
      @Query("SELECT * FROM Task ORDER BY creationTimestamp ASC")
      LiveData<List<TaskAndProject>> getTasksFromOldToRecent();
      
      @Delete
      void deleteTask(Task task);
      
      @Insert(onConflict = OnConflictStrategy.REPLACE)
      void insertTask(Task task);
}
