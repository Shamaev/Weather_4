package com.geekbrains.weather_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekbrains.weather_4.model.WeatherRequest;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView city_name, data, temp, wind, wind_info;
    ImageView imageView;
    private ArrayList<String> al;
    Handler handler;
    WeatherProvider weatherProvider;
    MyTimerTask myTimerTask;
    Timer timer;

    private SharedPreferences sharedPref;
    OpenWeather openWeather;
    private static final int AbsoluteZero = -273;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
//        initRetorfit();
//        requestRetrofit("Ivanovo", "6bdcb2b7e6c07df82dc5d96dc7b9ab2e");
    }

    private void initView() {
        city_name = findViewById(R.id.city_name);
        data = findViewById(R.id.data);
        temp = findViewById(R.id.temp);
        wind = findViewById(R.id.wind);
        wind_info = findViewById(R.id.wind_info);
        ImageView imageView = findViewById(R.id.img);
        Picasso.get()
                .load("https://images.unsplash.com/photo-1534794048419-48e110dca88e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1896&q=80")
                .into(imageView);


        handler = new Handler();
        myTimerTask = new MyTimerTask();
        timer = new Timer();

        timer.schedule(myTimerTask, 1000);

    }

//    private void initPreferences() {
//        sharedPref = getPreferences(MODE_PRIVATE);
//        loadPreferences();                   // Загружаем настройки
//    }
//
//    // Загружаем настройки
//    private void loadPreferences() {
//        String loadedApiKey = sharedPref.getString("apiKey", "240af58b6f095eb759a3ecd2d282d448");
//        editApiKey.setText(loadedApiKey);
//    }

//    private void initRetorfit() {
//        Retrofit retrofit;
//        retrofit = new Retrofit.Builder()
//                .baseUrl("http://api.openweathermap.org/") // Базовая часть
//                // адреса
//                // Конвертер, необходимый для преобразования JSON
//                // в объекты
//                .addConverterFactory(GsonConverterFactory.create()).build();
//        // Создаём объект, при помощи которого будем выполнять запросы
//        openWeather = retrofit.create(OpenWeather.class);
//    }
//
//    private void requestRetrofit(String city, String keyApi) {
//        openWeather.loadWeather(city, keyApi)
//                .enqueue(new Callback<WeatherRequest>() {
//                    @Override
//                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
//                        if (response.body() != null) {
//                            Double resultTemp = response.body().getMain().getTemp();
//                            String temperature = processingTemp(resultTemp);
//                            temp.setText(temperature);
//
//                            Double resultWind = response.body().getWind().getSpeed();
//                            String speed = processingSpeed(resultWind);
//                            wind_info.setText(speed);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
//                        temp.setText("Error");
//                    }
//                });
//    }
//
//    private String processingTemp(Double resultTemp) {
//        long n = Math.round(resultTemp) + AbsoluteZero;
//        String plusTemp = "+" + n + "°";
//        String minTemp = "-" + n + "°";
//        String zeroTemp = " " + n + "°";
//        if (n > 0) {
//            return plusTemp;
//        } else if (n < 0) {
//            return minTemp;
//        } else {
//            return zeroTemp;
//        }
//    }
//
//    private String processingSpeed(Double speed) {
//        BigDecimal bd = new BigDecimal(Double.toString(speed));
//        bd = bd.setScale(1, RoundingMode.HALF_UP);
//        return bd.doubleValue() + " м/с";
//    }


    class MyTimerTask extends TimerTask {
        public void run() {
            upWeather();
        }
    }

    private void upWeather() {
        weatherProvider = new WeatherProvider();
        al = weatherProvider.getData("Ivanovo");

        handler.post(new Runnable() {
            @Override
            public void run() {
                temp.setText(al.get(0));
                wind_info.setText(al.get(1));
            }
        });
    }
}
