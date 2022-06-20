package com.cleanup.todoc.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.MainViewModel;
import com.cleanup.todoc.MyApplication;
import com.cleanup.todoc.R;
import com.cleanup.todoc.data.AppDatabase;
import com.cleanup.todoc.databinding.ActivityMainBinding;
import com.cleanup.todoc.injection.ViewModelFactory;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskAndProject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Home activity of the application which is displayed when the user opens the app.
 * Displays the list of tasks.
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {

      private ActivityMainBinding binding;
      private MainViewModel viewModel;

      // List of all projects available
      private List<Project> allProjects;

      // List of all current tasks of the application
      @NonNull
      private final ArrayList<TaskAndProject> taskAndProjects = new ArrayList<>();

      // The adapter which handles the list of tasks
      private TasksAdapter adapter;

      // The sort method to be used to display tasks
      @NonNull
      private SortMethod sortMethod = SortMethod.NONE;

      // Dialog to create a new task
      @Nullable
      public AlertDialog dialog = null;

      // EditText that allows user to set the name of a task
      @Nullable
      private EditText dialogEditText = null;

      // Spinner that allows the user to associate a project to a task
      @Nullable
      private Spinner dialogSpinner = null;

      // The RecyclerView which displays the list of tasks
      // Suppress warning is safe because variable is initialized in onCreate
      @SuppressWarnings("NullableProblems")
      @NonNull
      private RecyclerView listTasks;

      //The TextView displaying the empty state
      // Suppress warning is safe because variable is initialized in onCreate
      @SuppressWarnings("NullableProblems")
      @NonNull
      private TextView lblNoTasks;

      @Override
      public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            getDatabaseInstance();
            configureViewModel();

            lblNoTasks = binding.lblNoTask;
            listTasks = binding.listTasks;

            configureRecyclerView();

            binding.fabAddTask.setOnClickListener(view -> showAddTaskDialog());

            getAllProjects();
            getAllTasks();
      }

      private void getDatabaseInstance(){
            MyApplication myApplication =MyApplication.getMyApplicationInstance();
            AppDatabase appDatabase = myApplication.getDatabaseInstance();
      }

      private void configureViewModel() {
            viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);
      }

      private void getAllProjects(){
            viewModel.getAllProjects().observe(this, projects -> allProjects = projects);
      }

      private void getAllTasks(){
            viewModel.getAllTasksAndProjects().observe(this, this::updateTasks);
      }

      private void configureRecyclerView(){
            adapter = new TasksAdapter(taskAndProjects, this);
            listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            listTasks.setAdapter(adapter);
      }


      @Override
      public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.actions, menu);
            return true;
      }

      @Override
      public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.filter_alphabetical) {
                  sortMethod = SortMethod.ALPHABETICAL;
            } else if (id == R.id.filter_alphabetical_inverted) {
                  sortMethod = SortMethod.ALPHABETICAL_INVERTED;
            } else if (id == R.id.filter_oldest_first) {
                  sortMethod = SortMethod.OLD_FIRST;
            } else if (id == R.id.filter_recent_first) {
                  sortMethod = SortMethod.RECENT_FIRST;
            }

            getAllTasks();

            return super.onOptionsItemSelected(item);
      }

      @Override
      public void onDeleteTask(Task task) {
            viewModel.deleteTask(task);
            Toast.makeText(this, "La tâche a été supprimée", Toast.LENGTH_SHORT).show();
      }

      /**
       * Called when the user clicks on the positive button of the Create Task Dialog.
       * @param dialogInterface the current displayed dialog
       */
      private void onPositiveButtonClick(DialogInterface dialogInterface) {
            // If dialog is open
            if (dialogEditText != null && dialogSpinner != null) {
                  // Get the name of the task
                  String taskName = dialogEditText.getText().toString();

                  // Get the selected project to be associated to the task
                  Project taskProject = null;
                  if (dialogSpinner.getSelectedItem() instanceof Project) {
                        taskProject = (Project) dialogSpinner.getSelectedItem();
                  }

                  // If a name has not been set
                  if (taskName.trim().isEmpty()) {
                        dialogEditText.setError(getString(R.string.empty_task_name));
                  }
                  // If both project and name of the task have been set
                  else if (taskProject != null) {
                        Task task = new Task(
                              taskProject.getId(),
                              taskName,
                              new Date().getTime()
                        );

                        addTask(task);

                        dialogInterface.dismiss();
                  }
                  // If name has been set, but project has not been set (this should never occur)
                  else{
                        dialogInterface.dismiss();
                  }
            }
            // If dialog is already closed
            else {
                  dialogInterface.dismiss();
            }
      }

      // Shows the Dialog for adding a Task
      private void showAddTaskDialog() {
            final AlertDialog dialog = getAddTaskDialog();

            dialog.show();

            dialogEditText = dialog.findViewById(R.id.txt_task_name);
            dialogSpinner = dialog.findViewById(R.id.project_spinner);

            populateDialogSpinner();
      }

      /**
       * Adds the given task to the list of created tasks.
       * @param task the task to be added to the list
       */
      private void addTask(@NonNull Task task) {
            viewModel.insertTask(task);
            Toast.makeText(this, "La tâche a été ajoutée", Toast.LENGTH_SHORT).show();
      }

      // Updates the list of tasks in the UI
      private void updateTasks(List<TaskAndProject> taskAndProjects) {
            if (taskAndProjects.size() == 0) {
                  lblNoTasks.setVisibility(View.VISIBLE);
                  listTasks.setVisibility(View.GONE);
            } else {
                  lblNoTasks.setVisibility(View.GONE);
                  listTasks.setVisibility(View.VISIBLE);
                  switch (sortMethod) {
                        case ALPHABETICAL:
                              viewModel.getTasksFromAToZ().observe(this, this::updateTasks);
                              break;
                        case ALPHABETICAL_INVERTED:
                              viewModel.getTasksFromZToA().observe(this, this::updateTasks);
                              break;
                        case RECENT_FIRST:
                              viewModel.getTasksFromRecentToOld().observe(this, this::updateTasks);
                              break;
                        case OLD_FIRST:
                              viewModel.getTasksFromOldToRecent().observe(this, this::updateTasks);
                              break;

                  }
                  adapter.updateTasks(taskAndProjects);
            }
      }

      /**
       * Returns the dialog allowing the user to create a new task.
       * @return the dialog allowing the user to create a new task
       */
      @NonNull
      private AlertDialog getAddTaskDialog() {
            final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

            alertBuilder.setTitle(R.string.add_task);
            alertBuilder.setView(R.layout.dialog_add_task);
            alertBuilder.setPositiveButton(R.string.add, null);
            alertBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                  @Override
                  public void onDismiss(DialogInterface dialogInterface) {
                        dialogEditText = null;
                        dialogSpinner = null;
                        dialog = null;
                  }
            });

            dialog = alertBuilder.create();

            // This instead of listener to positive button in order to avoid automatic dismiss
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                  @Override
                  public void onShow(DialogInterface dialogInterface) {
                        Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(view -> onPositiveButtonClick(dialog));
                  }
            });

            return dialog;
      }

      //Sets the data of the Spinner with projects to associate to a new task
      private void populateDialogSpinner() {
            final ArrayAdapter<Project> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allProjects);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            if (dialogSpinner != null) {
                  dialogSpinner.setAdapter(adapter);
            }
      }

      // List of all possible sort methods for task
      private enum SortMethod {
            // Sort alphabetical by name
            ALPHABETICAL,
            // Inverted sort alphabetical by name
            ALPHABETICAL_INVERTED,
            // Lastly created first
            RECENT_FIRST,
            //First created first
            OLD_FIRST,
            // No sort
            NONE
      }
}
