package com.cleanup.todoc.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.MainViewModel;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {
      private final ProjectRepository projectRepository;
      private final TaskRepository taskRepository;
      private final Executor executor;
      private static ViewModelFactory factory;
      
      private ViewModelFactory(){
            this.projectRepository = DI.getProjectRepository();
            this.taskRepository = DI.getTaskRepository();
            this.executor = Executors.newSingleThreadExecutor();
      }
      
      public static ViewModelFactory getInstance(){
            if(factory == null){
                  synchronized (ViewModelFactory.class){
                        if(factory == null){
                              factory = new ViewModelFactory();
                        }
                  }
            }
            return factory;
      }
      
      @Override
      @NonNull
      public <T extends ViewModel>  T create(Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MainViewModel.class)) {
                  return (T) new MainViewModel(projectRepository, taskRepository, executor);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
      }
}
