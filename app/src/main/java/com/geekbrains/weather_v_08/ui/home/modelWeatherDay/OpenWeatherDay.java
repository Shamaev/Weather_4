package com.geekbrains.weather_v_08.ui.home.modelWeatherDay;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherDay {
    @GET("data/2.5/forecast")
    Call<WeatherRequestDay> loadWeather(@Query("q") String cityCountry, @Query("appid") String keyApi);
}
