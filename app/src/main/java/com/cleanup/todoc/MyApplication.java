package com.cleanup.todoc;

import android.app.Application;

import com.cleanup.todoc.data.AppDatabase;

public class MyApplication extends Application {
      
      AppDatabase appDatabase;
      private static MyApplication myApplicationInstance;
      
      public static MyApplication getMyApplicationInstance(){
            return myApplicationInstance;
      }
      
      @Override
      public void onCreate() {
            super.onCreate();
            appDatabase = AppDatabase.getInstance(this);
            myApplicationInstance = this;
      }
      
      public AppDatabase getDatabaseInstance(){
            return appDatabase;
      }
}