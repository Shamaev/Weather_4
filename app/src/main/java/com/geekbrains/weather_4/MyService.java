package com.geekbrains.weather_4;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.geekbrains.weather_4.model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class MyService extends Service {
    private final IBinder binder = new ServiceBinder();

    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather?q=Ivanovo&units=metric&RU&appid=";
    private static final String WEATHER_API_KEY = "6bdcb2b7e6c07df82dc5d96dc7b9ab2e";


    ArrayList<String> al2;


    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    class ServiceBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
        ArrayList<String> getInfo(){
            return getService().getInfo();
        }
    }

//    private void initArray(ArrayList<String> al2) {
//        this.al2 = al2;
//    }

//    private ArrayList<String> putInfo() {
//        getInfo();
//        return al2;
//    }

    private ArrayList<String> getInfo() {
        final ArrayList<String> al = new ArrayList<>();
        try {


            final URL uri = new URL(WEATHER_URL + WEATHER_API_KEY);
            final Handler handler = new Handler(); // Запоминаем основной поток

            new Thread(new Runnable() {
            public void run() {

                HttpsURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpsURLConnection) uri.openConnection();
                    urlConnection.setRequestMethod("GET"); // установка метода получения данных - GET
                    urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
                    String result = getLines(in);
                    // преобразование данных запроса в модель
                    Gson gson = new Gson();
                    final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);

//                    initArray(displayWeather(weatherRequest));

                    // Возвращаемся к основному потоку
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        al.addAll(displayWeather(weatherRequest));
                    }
                });

                } catch (Exception e) {
                    Log.e(TAG, "Fail connection", e);
                    e.printStackTrace();
                } finally {
                    if (null != urlConnection) {
                        urlConnection.disconnect();
                    }

                }

            }

        }).start();
        } catch (MalformedURLException e) {
            Log.e(TAG, "Fail URI", e);
            e.printStackTrace();
        }
        return al;
//        try {
//            final URL uri = new URL(WEATHER_URL + WEATHER_API_KEY);
//            HttpsURLConnection urlConnection = null;
//            try {
//                urlConnection = (HttpsURLConnection) uri.openConnection();
//                urlConnection.setRequestMethod("GET"); // установка метода получения данных - GET
//                urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд
//                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
//                String result = getLines(in);
//                // преобразование данных запроса в модель
//                Gson gson = new Gson();
//                final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
//
//                initArray(displayWeather(weatherRequest));
//
//                // Возвращаемся к основному потоку
////                handler.post(new Runnable() {
////                    @Override
////                    public void run() {
//////                             weatherHandler = new WeatherHandler(weatherRequest);
////                        displayWeather(weatherRequest);
////                    }
////                });
//            } catch (Exception e) {
//                Log.e(TAG, "Fail connection", e);
//                e.printStackTrace();
//            } finally {
//                if (null != urlConnection) {
//                    urlConnection.disconnect();
//                }
//            }
//        } catch (MalformedURLException e) {
//            Log.e(TAG, "Fail URI", e);
//            e.printStackTrace();
//        }
    }

    private String getLines(BufferedReader in) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return in.lines().collect(Collectors.joining("\n"));
        }
        return null;
    }

    private ArrayList<String> displayWeather(WeatherRequest weatherRequest){
        ArrayList<String> al = new ArrayList<>();
        Double d = weatherRequest.getMain().getTemp();
        long n = Math.round(d);
        String temp = String.valueOf(n);

        Double speed = weatherRequest.getWind().getSpeed();
        BigDecimal bd = new BigDecimal(Double.toString(speed));
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        String wind = String.valueOf(bd.doubleValue());

        al.add(0, "temp");
        al.add(1, "wind");

        return al;
    }

}
