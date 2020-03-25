package com.geekbrains.weather_4;

import android.os.Build;
import android.util.Log;

import com.geekbrains.weather_4.model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class WeatherProvider extends Thread {
    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=Ivanovo&units=metric&RU&appid=";
    private static final String WEATHER_API_KEY = "6bdcb2b7e6c07df82dc5d96dc7b9ab2e";
    public WeatherHandler weatherHandler;



    private String temp;
    private String wind;



    public void run() {
        try {
            final URL uri = new URL(WEATHER_URL + WEATHER_API_KEY);
//            final Handler handler = new Handler(); // Запоминаем основной поток
            HttpsURLConnection urlConnection = null;
            try {
                urlConnection = (HttpsURLConnection) uri.openConnection();
                urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
                urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
                String result = getLines(in);
                // преобразование данных запроса в модель
                Gson gson = new Gson();
                final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);

                displayWeather(weatherRequest);

                // Возвращаемся к основному потоку
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
////                             weatherHandler = new WeatherHandler(weatherRequest);
//                        displayWeather(weatherRequest);
//                    }
//                });
            } catch (Exception e) {
                Log.e(TAG, "Fail connection", e);
                e.printStackTrace();
            } finally {
                if (null != urlConnection) {
                    urlConnection.disconnect();
                }
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }
    }


    private String getLines(BufferedReader in) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return in.lines().collect(Collectors.joining("\n"));
        }
        return null;
    }

    private void displayWeather(WeatherRequest weatherRequest){
        Double d = weatherRequest.getMain().getTemp();
        long n = Math.round(d);
        temp = String.valueOf(n);
//        String strTemp = String.valueOf(n);

        Double speed = weatherRequest.getWind().getSpeed();
        BigDecimal bd = new BigDecimal(Double.toString(speed));
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        wind = String.valueOf(bd.doubleValue());
//        String strSpeed = String.valueOf(bd.doubleValue());

//        wind_info.setText(String.format("%d", weatherRequest.getWind().getSpeed()));

//            pressure.setText(String.format("%d", weatherRequest.getMain().getPressure()));
//            humidity.setText(String.format("%d", weatherRequest.getMain().getHumidity()));

    }
    public String getTemp() {
        return temp;
    }

    public String getWind() {
        return wind;
    }

}
