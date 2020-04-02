package com.geekbrains.cases.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"text_work", "check_work", "data", "time"})})
public class Cases {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "text_work")
    public String textWork;

    @ColumnInfo(name = "check_work")
    public int checkWork;

    @ColumnInfo(name = "data")
    public String data;

    @ColumnInfo(name = "time")
    public String time;
}
