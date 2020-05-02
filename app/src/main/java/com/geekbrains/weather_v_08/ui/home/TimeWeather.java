package com.geekbrains.weather_v_08.ui.home;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeWeather {
    private static final int AbsoluteZero = -273;
    private String time;
    private Double temp;
    private int icon;

    public TimeWeather(String time, Double temp, int icon) {
        this.time = time;
        this.temp = temp;
        this.icon = icon;
    }

    public String getTime() {
        return time.substring(11, 16);
    }
    public String getTimeDay() {
        return dataFut(time.substring(0, 11));
    }

    private String dataFut(String date) {
        Date currentDate = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            currentDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("dataDay", " " + currentDate);

        DateFormat dateFormat2 = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat2.format(currentDate);

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK)-1;

        String[] names = {
                "Воскресенье",
                "Понедельник",
                "Вторник",
                "Среда",
                "Четверг",
                "Пятница",
                "Суббота"
        };

        return names[dayOfWeek] + ", " +  dateText;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemp() {
        long n = Math.round(temp) + AbsoluteZero;
        if (n > 0) {
            return "+" + n + "°";
        } else if (n < 0) {
            return "" + n + "°";
        } else {
            return String.valueOf(n);
        }
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
