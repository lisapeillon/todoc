package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.TaskDao;
import com.cleanup.todoc.model.Task;

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
      public LiveData<List<Task>> getAllTasks(){ return taskDao.getAllTasks(); }
      public LiveData<List<Task>> getTasksFromAToZ() { return taskDao.getTasksFromAToZ(); }
      public LiveData<List<Task>> getTasksFromZToA() { return taskDao.getTasksFromZToA(); }
      public LiveData<List<Task>> getTasksFromRecentToOld() { return taskDao.getTasksFromRecentToOld(); }
      public LiveData<List<Task>> getTasksFromOldToRecent() { return taskDao.getTasksFromOldToRecent(); }
      
      // ---- UPDATE ----
      public void updateTask(Task task){
            taskDao.updateTask(task);
      }
      
      // ---- DELETE ----
      public void deleteTask(Task task){
            taskDao.deleteTask(task);
      }
}
