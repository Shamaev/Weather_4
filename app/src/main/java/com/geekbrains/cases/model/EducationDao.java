package com.geekbrains.cases.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EducationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCases(Cases cases);

    // Метод для замены данных студента
    @Update
    void updateCases(Cases cases);

    // Удаляем данные студента
    @Delete
    void deleteCases(Cases cases);

    // Удаляем данные студента, зная ключ
    @Query("DELETE FROM Cases WHERE id = :id")
    void deteleCasesById(long id);

    // Удаляем данные студента, зная ключ
    @Query("DELETE FROM Cases")
    void deteleCases();

    // Забираем данные по всем студентам
    @Query("SELECT * FROM Cases")
    List<Cases> getAllCases();

    // Получаем данные одного студента по id
    @Query("SELECT * FROM Cases WHERE id = :id")
    Cases getCasesById(long id);

    //Получаем количество записей в таблице
    @Query("SELECT COUNT() FROM Cases")
    long getCountCases();
}
