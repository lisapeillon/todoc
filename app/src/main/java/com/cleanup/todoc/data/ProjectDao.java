package com.cleanup.todoc.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {
      @Query("SELECT * FROM Project")
      LiveData<List<Project>> getAllProjects();
      
      @Insert
      void insertProject(Project project);
}
