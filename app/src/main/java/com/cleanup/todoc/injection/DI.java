package com.cleanup.todoc.injection;

import com.cleanup.todoc.MyApplication;
import com.cleanup.todoc.data.ProjectDao;
import com.cleanup.todoc.data.TaskDao;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

public class DI {
      private static ProjectRepository sProjectRepository = new ProjectRepository(MyApplication.getMyApplicationInstance().getDatabaseInstance().getProjectDao());
      public static ProjectRepository getProjectRepository() { return sProjectRepository; }
      public static ProjectRepository getNewInstanceProjectRepository() { return new ProjectRepository(MyApplication.getMyApplicationInstance().getDatabaseInstance().getProjectDao()); }
      
      private static TaskRepository sTaskRepository = new TaskRepository(MyApplication.getMyApplicationInstance().getDatabaseInstance().getTaskDao());
      public static TaskRepository getTaskRepository() { return sTaskRepository; }
      public static TaskRepository getNewInstanceTaskRepository() { return new TaskRepository(MyApplication.getMyApplicationInstance().getDatabaseInstance().getTaskDao()); }
}
