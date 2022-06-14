package com.cleanup.todoc.repositories;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.injection.DI;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class ProjectUnitTest {
      
      @Rule
      public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
      
      private ProjectRepository repository;
      private LiveData<List<Project>> projectsList;
      private List<Task> result;
      
      @Before
      public void setUp(){
            repository = DI.getNewInstanceProjectRepository();
      }
      
      @Test
      public void getProjectsNamesWithSuccess() {
            /*assertEquals("Projet Tartampion", task1.getProject().getName());
            assertEquals("Projet Lucidia", task2.getProject().getName());
            assertEquals("Projet Circus", task3.getProject().getName());
            assertNull(task4.getProject());*/
      }
}
