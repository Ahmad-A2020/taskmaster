package com.example.taskmaster;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TaskM.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
//    public abstract TaskDao taskDao();
}
