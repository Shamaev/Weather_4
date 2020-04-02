package com.geekbrains.cases.model;

import java.util.List;

public class EducationSource {
    private final EducationDao educationDao;

    // Буфер с данными: сюда будем подкачивать данные из БД
    private List<Cases> works;

    public EducationSource(EducationDao educationDao){
        this.educationDao = educationDao;
    }

    // Получить всех студентов
    public List<Cases> getCases(){
        // Если объекты еще не загружены, загружаем их.
        // Это сделано для того, чтобы не делать запросы к БД каждый раз
        if (works == null){
            LoadCases();
        }
        return works;
    }

    // Загружаем студентов в буфер
    public void LoadCases(){
        works = educationDao.getAllCases();
    }

    // Получаем количество записей
    public long getCountCases(){
        return educationDao.getCountCases();
    }

    // Добавляем студента
    public void addStudent(Cases student){
        educationDao.insertCases(student);
        // После изменения БД надо повторно прочесть данные из буфера
        LoadCases();
    }

    // Заменяем студента
    public void updateStudent(Cases student){
        educationDao.updateCases(student);
        LoadCases();
    }

    // Удаляем студента из базы
    public void removeStudent(long id){
        educationDao.deteleCasesById(id);
        LoadCases();
    }

    // Удаляем базу
    public void deleteStudent(){
        educationDao.deteleCases();
        LoadCases();
    }

}
