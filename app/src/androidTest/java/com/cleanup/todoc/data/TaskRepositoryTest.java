package com.cleanup.todoc.data;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskAndProject;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.utils.LiveDataTestUtils;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RunWith(AndroidJUnit4.class)
public class TaskRepositoryTest extends TestCase {

          private static AppDatabase appDatabase;
          private static TaskRepository taskRepository;
          private Executor executor;

          @Before
          public void setUp() {
                    Context context = ApplicationProvider.getApplicationContext();
                    appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
                    taskRepository = new TaskRepository(appDatabase.getTaskDao());
                    executor = Executors.newSingleThreadExecutor();
          }

          @After
          public void closeDatabase()  {
                    appDatabase.close();
          }

          @Test
          public void insertAndGetTasks() throws InterruptedException {
                    Task task1 = new Task(2L, "Aspirateur", new Date().getTime());
                    Task task2 = new Task(1L, "Nettoyage des vitres", new Date().getTime());
                    executor.execute(() -> taskRepository.insertTask(task1));
                    executor.execute(() -> taskRepository.insertTask(task2));
                    LiveData<List<TaskAndProject>> taskAndProjectLiveDataList = taskRepository.getAllTasksAndProjects();
                    List<TaskAndProject> taskAndProjectList = LiveDataTestUtils.getValue(taskAndProjectLiveDataList);
                    assertEquals(2, taskAndProjectList.size());
          }

          @Test
          public void testAZSort() throws InterruptedException {
                    Task task1 = new Task(2L, "ggg", new Date().getTime());
                    Task task2 = new Task(3L, "aaa", new Date().getTime());
                    Task task3 = new Task(1L, "zzz", new Date().getTime());
                    executor.execute(() -> taskRepository.insertTask(task1));
                    executor.execute(() -> taskRepository.insertTask(task2));
                    executor.execute(() -> taskRepository.insertTask(task3));
                    LiveData<List<TaskAndProject>> taskAndProjectLiveDataList = taskRepository.getTasksFromAToZ();
                    List<TaskAndProject> taskAndProjectList = LiveDataTestUtils.getValue(taskAndProjectLiveDataList);
                    assertSame(task2, taskAndProjectList.get(0));
                    assertSame(task1, taskAndProjectList.get(1));
                    assertSame(task3, taskAndProjectList.get(2));
          }

          @Test
          public void testZASort() throws InterruptedException{
                    Task task1 = new Task(2L, "ggg", new Date().getTime());
                    Task task2 = new Task(3L, "aaa", new Date().getTime());
                    Task task3 = new Task(1L, "zzz", new Date().getTime());
                    executor.execute(() -> taskRepository.insertTask(task1));
                    executor.execute(() -> taskRepository.insertTask(task2));
                    executor.execute(() -> taskRepository.insertTask(task3));
                    LiveData<List<TaskAndProject>> taskAndProjectLiveDataList = taskRepository.getTasksFromZToA();
                    List<TaskAndProject> taskAndProjectList = LiveDataTestUtils.getValue(taskAndProjectLiveDataList);
                    assertSame(task3, taskAndProjectList.get(0));
                    assertSame(task1, taskAndProjectList.get(1));
                    assertSame(task2, taskAndProjectList.get(2));
          }

          @Test
          public void testRecentOldSort() throws InterruptedException{
                    Task task1 = new Task(2L, "ggg", 129);
                    Task task2 = new Task(3L, "aaa", 123);
                    Task task3 = new Task(1L, "zzz", 125);
                    executor.execute(() -> taskRepository.insertTask(task1));
                    executor.execute(() -> taskRepository.insertTask(task2));
                    executor.execute(() -> taskRepository.insertTask(task3));
                    LiveData<List<TaskAndProject>> taskAndProjectLiveDataList = taskRepository.getTasksFromRecentToOld();
                    List<TaskAndProject> taskAndProjectList = LiveDataTestUtils.getValue(taskAndProjectLiveDataList);
                    assertSame(task2, taskAndProjectList.get(0));
                    assertSame(task3, taskAndProjectList.get(1));
                    assertSame(task1, taskAndProjectList.get(2));
          }

          @Test
          public void testOldRecentSort() throws InterruptedException{
                    Task task1 = new Task(2L, "ggg", 129);
                    Task task2 = new Task(3L, "aaa", 123);
                    Task task3 = new Task(1L, "zzz", 125);
                    executor.execute(() -> taskRepository.insertTask(task1));
                    executor.execute(() -> taskRepository.insertTask(task2));
                    executor.execute(() -> taskRepository.insertTask(task3));
                    LiveData<List<TaskAndProject>> taskAndProjectLiveDataList = taskRepository.getTasksFromOldToRecent();
                    List<TaskAndProject> taskAndProjectList = LiveDataTestUtils.getValue(taskAndProjectLiveDataList);
                    assertSame(task1, taskAndProjectList.get(0));
                    assertSame(task3, taskAndProjectList.get(1));
                    assertSame(task2, taskAndProjectList.get(2));
          }

          @Test
          public void deleteTaskTest() throws InterruptedException{
                    Task task1 = new Task(2L, "ggg", 129);
                    Task task2 = new Task(3L, "aaa", 123);
                    Task task3 = new Task(1L, "zzz", 125);
                    executor.execute(() -> taskRepository.insertTask(task1));
                    executor.execute(() -> taskRepository.insertTask(task2));
                    executor.execute(() -> taskRepository.insertTask(task3));
                    LiveData<List<TaskAndProject>> taskAndProjectLiveDataList = taskRepository.getAllTasksAndProjects();
                    List<TaskAndProject> taskAndProjectList = LiveDataTestUtils.getValue(taskAndProjectLiveDataList);
                    assertSame(task1, taskAndProjectList.get(0));
                    executor.execute(() -> taskRepository.deleteTask(task1));
                    taskAndProjectLiveDataList = taskRepository.getAllTasksAndProjects();
                    taskAndProjectList = LiveDataTestUtils.getValue(taskAndProjectLiveDataList);
                    assertSame(task2, taskAndProjectList.get(0));
          }
}