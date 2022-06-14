package com.cleanup.todoc.model;


import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Models for project in which tasks are included.
 * @author Gaëtan HERFRAY
 */
@Entity
public class Project {
      // The unique identifier of the project
      @PrimaryKey(autoGenerate = true)
      private final long id;
      
      //The name of the project
      @NonNull
      @ColumnInfo
      private final String name;
      
      //The hex (ARGB) code of the color associated to the project
      @ColorInt
      @ColumnInfo
      private final int color;
      
      
      // -----------------------
      // ---- CONSTRUCTOR ----
      // -----------------------
      
      /**
       * Instantiates a new Project.
       * @param id    the unique identifier of the project to set
       * @param name  the name of the project to set
       * @param color the hex (ARGB) code of the color associated to the project to set
       */
      public Project(long id, @NonNull String name, @ColorInt int color) {
            this.id = id;
            this.name = name;
            this.color = color;
      }
      

      // -----------------
      // ---- GETTERS ----
      // -----------------
      
      /**
       * Returns the unique identifier of the project.
       * @return the unique identifier of the project
       */
      public long getId() { return id; }
      
      /**
       * Returns the name of the project.
       * @return the name of the project
       */
      @NonNull
      public String getName() { return name; }
      
      /**
       * Returns the hex (ARGB) code of the color associated to the project.
       * @return the hex (ARGB) code of the color associated to the project
       */
      @ColorInt
      public int getColor() { return color; }
      
      @Override
      @NonNull
      public String toString() { return getName(); }
}
