package com.cleanup.todoc.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
      @Query("SELECT * FROM Task")
      LiveData<List<Task>> getAllTasks();
      
      @Query("SELECT * FROM Task ORDER BY name ASC")
      LiveData<List<Task>> getTasksFromAToZ();
      
      @Query("SELECT * FROM Task ORDER BY name DESC")
      LiveData<List<Task>> getTasksFromZToA();
      
      @Query("SELECT * FROM Task ORDER BY creationTimestamp DESC")
      LiveData<List<Task>> getTasksFromRecentToOld();
      
      @Query("SELECT * FROM Task ORDER BY creationTimestamp ASC")
      LiveData<List<Task>> getTasksFromOldToRecent();
      
      @Update
      void updateTask(Task task);
      
      @Delete
      void deleteTask(Task task);
      
      @Insert(onConflict = OnConflictStrategy.REPLACE)
      void insertTask(Task task);
}
