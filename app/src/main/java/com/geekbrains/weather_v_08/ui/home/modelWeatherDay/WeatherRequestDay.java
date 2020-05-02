package com.geekbrains.weather_v_08.ui.home.modelWeatherDay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherRequestDay {
    @SerializedName("list")
    @Expose
    private ArrayList<ListItem> list;

    public ArrayList<ListItem> getList() {
        return list;
    }

    public void setList(ArrayList<ListItem> list) {
        this.list = list;
    }
}
