package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.TaskDao;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskAndProject;

import java.util.List;

public class TaskRepository {
      
      private TaskDao taskDao;
      
      // ---- CONSTRUCTOR ----
      public TaskRepository(TaskDao taskDao){
            this.taskDao = taskDao;
      }
      
      // ---- CREATE ----
      public void insertTask(Task task){
            taskDao.insertTask(task);
      }
      
      // ---- READ ----
      public LiveData<List<TaskAndProject>> getAllTasksAndProjects(){ return taskDao.getAllTasksAndProjects(); }
      public LiveData<List<TaskAndProject>> getTasksFromAToZ() { return taskDao.getTasksFromAToZ(); }
      public LiveData<List<TaskAndProject>> getTasksFromZToA() { return taskDao.getTasksFromZToA(); }
      public LiveData<List<TaskAndProject>> getTasksFromRecentToOld() { return taskDao.getTasksFromRecentToOld(); }
      public LiveData<List<TaskAndProject>> getTasksFromOldToRecent() { return taskDao.getTasksFromOldToRecent(); }

      
      // ---- DELETE ----
      public void deleteTask(Task task){
            taskDao.deleteTask(task);
      }
}
