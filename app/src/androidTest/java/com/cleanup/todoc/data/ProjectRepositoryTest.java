package com.cleanup.todoc.data;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.utils.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProjectRepositoryTest {

      private AppDatabase appDatabase;
      private ProjectRepository projectRepository;

      private final static Project project1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
      private final static Project project2 = new Project(2L, "Projet Lucidia", 0xFFB4CDBA);
      private final static Project project3 = new Project(3L, "Projet Circus", 0xFFA3CED2);


      @Rule
      public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

      @Before
      public void setUp() {
            Context context = ApplicationProvider.getApplicationContext();
            appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).allowMainThreadQueries().build();
            projectRepository = new ProjectRepository(appDatabase.getProjectDao());
            // Insert default projects
            appDatabase.getProjectDao().insertProject(project1);
            appDatabase.getProjectDao().insertProject(project2);
            appDatabase.getProjectDao().insertProject(project3);
      }

      @After
      public void closeDatabase()  { appDatabase.close(); }

      @Test
      public void getAllProjects() throws InterruptedException {
            // Get the LiveData<List> of all projects saved in database
            LiveData<List<Project>> projectLiveDataList = projectRepository.getAllProjects();
            // Get the value of the list
            List<Project> projectList = LiveDataTestUtils.getValue(projectLiveDataList);
            assertEquals(3, projectList.size());
      }
}