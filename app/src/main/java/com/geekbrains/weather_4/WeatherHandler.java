package com.geekbrains.weather_4;

import com.geekbrains.weather_4.model.WeatherRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WeatherHandler {
    private Double temp, speed;

    public WeatherHandler(WeatherRequest weatherRequest) {
        temp = weatherRequest.getMain().getTemp();
        speed =  weatherRequest.getWind().getSpeed();
    }

    public String getTemp() {
        long n = Math.round(temp);
        return String.valueOf(n);
    }

    public String getSpeed() {
        BigDecimal bd = new BigDecimal(Double.toString(speed));
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return String.valueOf(bd.doubleValue());
    }

}

