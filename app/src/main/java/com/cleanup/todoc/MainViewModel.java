package com.cleanup.todoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskAndProject;
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
      public LiveData<List<TaskAndProject>> getAllTasksAndProjects(){ return taskRepository.getAllTasksAndProjects(); }
      public LiveData<List<TaskAndProject>> getTasksFromAToZ() { return taskRepository.getTasksFromAToZ(); }
      public LiveData<List<TaskAndProject>> getTasksFromZToA() { return taskRepository.getTasksFromZToA(); }
      public LiveData<List<TaskAndProject>> getTasksFromRecentToOld() { return taskRepository.getTasksFromRecentToOld(); }
      public LiveData<List<TaskAndProject>> getTasksFromOldToRecent() { return taskRepository.getTasksFromOldToRecent(); }
      
      // ---- DELETE ----
      public void deleteTask(Task task){
            executor.execute(() -> taskRepository.deleteTask(task));
      }
      
      
      
      // ------------------
      // ---- PROJECTS ----
      // ------------------
      
      public LiveData<List<Project>> getAllProjects() { return projectRepository.getAllProjects(); }
}
