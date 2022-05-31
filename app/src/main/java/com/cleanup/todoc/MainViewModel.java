package com.cleanup.todoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {
      
      private final ProjectRepository projectRepository;
      private final TaskRepository taskRepository;
      private final Executor executor;
      
      
      // ---------------------
      // ---- CONSTRUCTOR ----
      // ---------------------
      
      public MainViewModel(ProjectRepository projectRepository, TaskRepository taskRepository, Executor executor){
            this.projectRepository = projectRepository;
            this.taskRepository = taskRepository;
            this.executor = executor;
      }
      
      
      // ---------------
      // ---- TASKS ----
      // ---------------
      
      // ---- CREATE ----
      public void insertTask(Task task){
            executor.execute(() -> taskRepository.insertTask(task));
      }
      
      // ---- READ ----
      public LiveData<List<Task>> getAllTasks(){ return taskRepository.getAllTasks(); }
      public LiveData<List<Task>> getTasksFromAToZ() { return taskRepository.getTasksFromAToZ(); }
      public LiveData<List<Task>> getTasksFromZToA() { return taskRepository.getTasksFromZToA(); }
      public LiveData<List<Task>> getTasksFromRecentToOld() { return taskRepository.getTasksFromRecentToOld(); }
      public LiveData<List<Task>> getTasksFromOldToRecent() { return taskRepository.getTasksFromOldToRecent(); }
      
      // ---- UPDATE ----
      public void updateTask(Task task) {
            executor.execute(() -> taskRepository.updateTask(task));
      }
      
      // ---- DELETE ----
      public void deleteTask(Task task){
            executor.execute(() -> taskRepository.deleteTask(task));
      }
      
      
      
      // ------------------
      // ---- PROJECTS ----
      // ------------------
      
      public LiveData<List<Project>> getAllProjects() { return projectRepository.getAllProjects(); }
      public Project getProjectById(long id){
            return projectRepository.getProjectById(id);
      }
}
