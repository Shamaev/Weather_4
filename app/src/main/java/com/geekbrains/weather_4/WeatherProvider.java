package com.geekbrains.weather_4;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherProvider extends Thread {
//    private static final String TAG = "WEATHER";
//    private static final String WEATHER_URL =
//            "https://api.openweathermap.org/data/2.5/weather?q=Ivanovo&units=metric&RU&appid=";
//    private static final String WEATHER_API_KEY = "6bdcb2b7e6c07df82dc5d96dc7b9ab2e";
//    private WeatherHandler weatherHandler = new WeatherHandler();
    ArrayList<String> al;
    OpenWeather openWeather;
    private static final int AbsoluteZero = -273;
    String temperature, speed;
    private WeatherHandler weatherHandler;

//    public void getInfo() {
//        try {
//            final URL uri = new URL(WEATHER_URL + WEATHER_API_KEY);
//            Thread t = new Thread(new Runnable() {
//                public void run() {
//                    HttpsURLConnection urlConnection = null;
//                    try {
//                        urlConnection = (HttpsURLConnection) uri.openConnection();
//                        urlConnection.setRequestMethod("GET"); // установка метода получения данных - GET
//                        urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд
//                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
//                        String result = getLines(in);
//                        // преобразование данных запроса в модель
//                        Gson gson = new Gson();
//                        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
//                        initData(weatherRequest);
//                    } catch (Exception e) {
//                        Log.e(TAG, "Fail connection", e);
//                        e.printStackTrace();
//                    } finally {
//                        if (null != urlConnection) {
//                            urlConnection.disconnect();
//                        }
//                    }
//                }
//
//            });
//            t.start();
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        } catch (MalformedURLException e) {
//            Log.e(TAG, "Fail URI", e);
//            e.printStackTrace();
//        }
//    }
//
//    private void initData(WeatherRequest weatherRequest){
//        weatherHandler.setTemp(weatherRequest.getMain().getTemp());
//        weatherHandler.setSpeed(weatherRequest.getWind().getSpeed());
//    }

    public ArrayList<String> getData(String city) {
        weatherHandler = new WeatherHandler();
        initRetorfit();
        requestRetrofit(city, "6bdcb2b7e6c07df82dc5d96dc7b9ab2e");
        al = new ArrayList<>();
        al.add(0, weatherHandler.getTemp());
        al.add(1, weatherHandler.getSpeed());
        return al;
    }

//    private String getLines(BufferedReader in) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return in.lines().collect(Collectors.joining("\n"));
//        }
//        return null;
//    }


    private void initRetorfit() {
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/") // Базовая часть
                // адреса
                // Конвертер, необходимый для преобразования JSON
                // в объекты
                .addConverterFactory(GsonConverterFactory.create()).build();
        // Создаём объект, при помощи которого будем выполнять запросы
        openWeather = retrofit.create(OpenWeather.class);
    }

    private void requestRetrofit(String city, String keyApi) {
        openWeather.loadWeather(city, keyApi)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null) {
                            Double resultTemp = response.body().getMain().getTemp();
                            weatherHandler.setTemp(resultTemp);

                            Double resultWind = response.body().getWind().getSpeed();
                            weatherHandler.setSpeed(resultWind);
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
//                        temp.setText("Error");
                    }
                });
    }

    private String processingTemp(Double resultTemp) {
        long n = Math.round(resultTemp) + AbsoluteZero;
        String plusTemp = "+" + n + "°";
        String minTemp = "-" + n + "°";
        String zeroTemp = " " + n + "°";
        if (n > 0) {
            return plusTemp;
        } else if (n < 0) {
            return minTemp;
        } else {
            return zeroTemp;
        }
    }

    private String processingSpeed(Double speed) {
        BigDecimal bd = new BigDecimal(Double.toString(speed));
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue() + " м/с";
    }

}
