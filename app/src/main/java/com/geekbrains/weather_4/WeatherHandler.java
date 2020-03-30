package com.geekbrains.weather_4;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WeatherHandler {
    private String temp;
    private String speed;


    public String getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        long n = Math.round(temp);
        if (n > 0) {
            this.temp = "+" + n + "°";
        } else if (n < 0) {
            this.temp = "-" + n + "°";
        } else {
            this.temp = String.valueOf(n);
        }
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        BigDecimal bd = new BigDecimal(Double.toString(speed));
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        this.speed = bd.doubleValue() + " м/с";
    }

}

