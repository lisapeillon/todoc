package com.cleanup.todoc.repositories;

import com.cleanup.todoc.injection.DI;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.utils.LiveDataTestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

/**
 * Unit tests for tasks
 * @author Gaëtan HERFRAY
 */
public class TaskUnitTest {
    
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
    
    private TaskRepository repository;
    private LiveData<List<Task>> taskList;
    private List<Task> result;
    
    private final static Task task1 = new Task(1, "task 1", new Date().getTime());
    private final static Task task2 = new Task(2, "task 2", new Date().getTime());
    private final static Task task3 = new Task(3, "task 3", new Date().getTime());
    private final static Task task4 = new Task(4, "task 4", new Date().getTime());
    
    @Before
    public void setUp(){
        repository = DI.getNewInstanceTaskRepository();
        //Todo teester le dao
    }
    
    @Test
    public void getTasksWithSuccess() throws InterruptedException{
        taskList = repository.getAllTasks();
        result = LiveDataTestUtils.getValue(taskList);
        assertTrue(result.size() == 0);
        repository.insertTask(task1);
        repository.insertTask(task2);
        taskList = repository.getAllTasks();
        result = LiveDataTestUtils.getValue(taskList);
        assertTrue(result.size() == 2);
    }
    
    @Test
    public void insertTaskWithSuccess() throws InterruptedException{
        taskList = repository.getAllTasks();
        result = LiveDataTestUtils.getValue(taskList);
        assertTrue(result.size() == 0);
    }

    @Test
    public void test_az_comparator() {
        final Task task1 = new Task(1, "aaa", 123);
        final Task task2 = new Task(2, "zzz", 124);
        final Task task3 = new Task(3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        //Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        final Task task1 = new Task(1, "aaa", 123);
        final Task task2 = new Task(2, "zzz", 124);
        final Task task3 = new Task(3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
      //  Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        final Task task1 = new Task(1, "aaa", 123);
        final Task task2 = new Task(2, "zzz", 124);
        final Task task3 = new Task(3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
       // Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final Task task1 = new Task(1, "aaa", 123);
        final Task task2 = new Task(2, "zzz", 124);
        final Task task3 = new Task(3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
       // Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}