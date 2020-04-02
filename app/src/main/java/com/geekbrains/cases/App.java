package com.geekbrains.cases;

import android.app.Application;

import androidx.room.Room;

import com.geekbrains.cases.model.EducationDao;
import com.geekbrains.cases.model.EducationDatabase;

public class App extends Application {
    private static App instance;

    // База данных
    private EducationDatabase db;

    // Получаем объект приложения
    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Сохраняем объект приложения (для Singleton’а)
        instance = this;

        // Строим базу
        db = Room.databaseBuilder(
                getApplicationContext(),
                EducationDatabase.class,
                "education_database")
                .allowMainThreadQueries()
                .build();
    }

    // Получаем EducationDao для составления запросов
    public EducationDao getEducationDao() {
        return db.getEducationDao();
    }

}
