package com.geekbrains.weather_v_08.ui.home.modelWeather;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WeatherHandler {
    private String temperature;
    private String speed;

    private String humidity;
    private String feels;

    public String getTemp() {
        return temperature;
    }

    public void setTemp(Double temp) {
        long n = Math.round(temp);
        if (n > 0) {
            this.temperature = "+" + n + "°";
        } else if (n < 0) {
            this.temperature = " " + n + "°";
        } else {
            this.temperature = " " + n + "°";
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

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = String.valueOf(humidity) + " %";
    }

    public String getFeels() {
        return feels;
    }

    public void setFeels(Double feels) {
        long n = Math.round(feels);
        String msg = "Ощущается как";
        if (n > 0) {
            this.feels = msg + " +" + n + "°";
        } else if (n < 0) {
            this.feels = msg + " " + n + "°";
        } else {
            this.feels = msg + " " + n + "°";
        }
    }

}
