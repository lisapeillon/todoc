package com.cleanup.todoc.data;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskAndProject;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.utils.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(AndroidJUnit4.class)
public class TaskRepositoryTest {

      @Rule
      public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

      private AppDatabase appDatabase;
      private TaskRepository taskRepository;
      private Executor executor;

      // Create new tasks
      private final Task task1 = new Task(2L, "ggg", 124);
      private final Task task2 = new Task(3L, "aaa", 123);
      private final Task task3 = new Task(1L, "zzz", 125);
      private final Task task4 = new Task(1L, "mmm", 127);

      @Before
      public void setUp() {
            Context context = ApplicationProvider.getApplicationContext();
            executor = Executors.newSingleThreadExecutor();
            appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).allowMainThreadQueries().build();
            taskRepository = new TaskRepository(appDatabase.getTaskDao());
            // Insert tasks in database
            taskRepository.insertTask(task1);
            taskRepository.insertTask(task2);
            taskRepository.insertTask(task3);
      }

      @After
      public void closeDatabase()  {
            appDatabase.close();
      }

      @Test
      public void insertAndGetTasks() throws InterruptedException {
            executor.execute(() -> taskRepository.insertTask(task4));
            // Get the LiveData<List> of all tasks saved in the database
            LiveData<List<TaskAndProject>> taskAndProjectLiveDataList = taskRepository.getAllTasksAndProjects();
            // Get the value of the list with the LiveDataTestUtils class
            List<TaskAndProject> taskAndProjectList = LiveDataTestUtils.getValue(taskAndProjectLiveDataList);
            assertEquals(4, taskAndProjectList.size());
      }

      @Test
      public void testAZSort() throws InterruptedException {
            // Get the LiveData<List> of all tasks saved in the database
            LiveData<List<TaskAndProject>> taskAndProjectLiveDataList = taskRepository.getTasksFromAToZ();
            // Get the value of the list with the LiveDataTestUtils class
            List<TaskAndProject> taskAndProjectList = LiveDataTestUtils.getValue(taskAndProjectLiveDataList);
            assertEquals(taskAndProjectList.get(0).getTask().getName(), "aaa");
            assertEquals(taskAndProjectList.get(1).getTask().getName(), "ggg");
            assertEquals(taskAndProjectList.get(2).getTask().getName(), "zzz");
      }

      @Test
      public void testZASort() throws InterruptedException{
            // Get the LiveData<List> of all tasks saved in database
            LiveData<List<TaskAndProject>> taskAndProjectLiveDataList = taskRepository.getTasksFromZToA();
            // Use the LiveDataTestUtils class to get the value of the list
            List<TaskAndProject> taskAndProjectList = LiveDataTestUtils.getValue(taskAndProjectLiveDataList);
            assertEquals(3, taskAndProjectList.size());
            assertEquals(taskAndProjectList.get(0).getTask().getName(), "zzz");
            assertEquals(taskAndProjectList.get(1).getTask().getName(), "ggg");
            assertEquals(taskAndProjectList.get(2).getTask().getName(), "aaa");
      }

      @Test
      public void testRecentOldSort() throws InterruptedException{
            // Get the LiveData<List> of all tasks saved in database
            LiveData<List<TaskAndProject>> taskAndProjectLiveDataList = taskRepository.getTasksFromRecentToOld();
            // Use the LiveDataTestUtils class to get the value of the list
            List<TaskAndProject> taskAndProjectList = LiveDataTestUtils.getValue(taskAndProjectLiveDataList);
            assertEquals(taskAndProjectList.get(0).getTask().getCreationTimestamp(), 125);
            assertEquals(taskAndProjectList.get(1).getTask().getCreationTimestamp(), 124);
            assertEquals(taskAndProjectList.get(2).getTask().getCreationTimestamp(), 123);
      }

      @Test
      public void testOldRecentSort() throws InterruptedException{
            // Get the livedata<list> of tasks from the database, sorted by timestamp from old to recent
            LiveData<List<TaskAndProject>> taskAndProjectLiveDataList = taskRepository.getTasksFromOldToRecent();
            // Use the LiveDataTestUtils class to get the value of the list
            List<TaskAndProject> taskAndProjectList = LiveDataTestUtils.getValue(taskAndProjectLiveDataList);
            assertEquals(taskAndProjectList.get(0).getTask().getCreationTimestamp(), 123);
            assertEquals(taskAndProjectList.get(1).getTask().getCreationTimestamp(), 124);
            assertEquals(taskAndProjectList.get(2).getTask().getCreationTimestamp(), 125);

      }

      @Test
      public void deleteTaskTest() throws InterruptedException{
            // Get the livedata<list> of all tasks from the database
            LiveData<List<TaskAndProject>> taskAndProjectLiveDataList = taskRepository.getAllTasksAndProjects();
            // Use the LiveDataTestUtils class to get the value of the list
            List<TaskAndProject> taskAndProjectList = LiveDataTestUtils.getValue(taskAndProjectLiveDataList);
            assertEquals(taskAndProjectList.get(0).getTask().getName(), "ggg");
            // Delete the first task of the list
            Task taskToDelete = taskAndProjectList.get(0).getTask();
            executor.execute(() -> taskRepository.deleteTask(taskToDelete));
            // Get the livedata<list> of all tasks from the database
            taskAndProjectLiveDataList = taskRepository.getAllTasksAndProjects();
            // Use the LiveDataTestUtils class to get the value of the list
            taskAndProjectList = LiveDataTestUtils.getValue(taskAndProjectLiveDataList);
            assertEquals(taskAndProjectList.get(0).getTask().getName(), "aaa");
      }
}