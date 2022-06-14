package com.cleanup.todoc.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

          private static final String DB_NAME = "todoc.db";

          // ---- SINGLETON ----
          private static volatile AppDatabase INSTANCE;

          // ---- DAO ----
          public abstract ProjectDao getProjectDao();
          public abstract TaskDao getTaskDao();

          // ---- INSTANCE ----
          public static AppDatabase getInstance(Context context){
                    if(INSTANCE == null){
                              synchronized (AppDatabase.class){
                                        if(INSTANCE == null){
                                                  INSTANCE = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                                                            .addCallback(new Callback() {
                                                                      @Override
                                                                      public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                                                                super.onCreate(db);
                                                                                Executor executor = Executors.newSingleThreadExecutor();
                                                                                executor.execute(() -> INSTANCE.getProjectDao().insertProject(project1));
                                                                                executor.execute(() -> INSTANCE.getProjectDao().insertProject(project2));
                                                                                executor.execute(() -> INSTANCE.getProjectDao().insertProject(project3));
                                                                      }
                                                            })
                                                            .build();
                                        }
                              }
                    }
                    return INSTANCE;
          }

          // ---- PREPOPULATE DATABASE ----
          private final static Project project1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
          private final static Project project2 = new Project(2L, "Projet Lucidia", 0xFFB4CDBA);
          private final static Project project3 = new Project(3L, "Projet Circus", 0xFFA3CED2);
}