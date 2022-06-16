package com.cleanup.todoc.data;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.injection.DI;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.utils.LiveDataTestUtils;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProjectRepositoryTest extends TestCase {

          private AppDatabase appDatabase;
          private ProjectRepository projectRepository;

          @Before
          public void setUp() throws Exception {
                    super.setUp();
                    Context context = ApplicationProvider.getApplicationContext();
                    appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
                    projectRepository = new ProjectRepository(appDatabase.getProjectDao());
          }

          @After
          public void closeDatabase()  {
                    appDatabase.close();
          }

          @Test
          public void getAllProjects() throws InterruptedException {
                    LiveData<List<Project>> projectLiveDataList = projectRepository.getAllProjects();
                    List<Project> projectList = LiveDataTestUtils.getValue(projectLiveDataList);
                    assertEquals(3, projectList.size());
          }
}