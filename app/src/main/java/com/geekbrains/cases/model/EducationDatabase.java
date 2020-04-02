package com.geekbrains.cases.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Cases.class}, version = 1)
public abstract class EducationDatabase extends RoomDatabase {
    public abstract EducationDao getEducationDao();
}
