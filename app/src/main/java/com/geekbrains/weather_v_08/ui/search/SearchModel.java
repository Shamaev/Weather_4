package com.geekbrains.weather_v_08.ui.search;

import java.util.ArrayList;
import java.util.List;

public class SearchModel {
    private final String city;
    private List<SearchModel> cityList;

    public SearchModel(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
