package com.geekbrains.weather_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView city_name, data, temp, wind, wind_info;
    private WeatherHandler weatherHandler;
    private WeatherProvider weatherProvider;

//    private static final String TAG = "WEATHER";
//    private static final String WEATHER_URL =
//            "https://api.openweathermap.org/data/2.5/weather?q=Ivanovo&units=metric&RU&appid=";
//    private static final String WEATHER_API_KEY = "6bdcb2b7e6c07df82dc5d96dc7b9ab2e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        city_name = findViewById(R.id.city_name);
        data = findViewById(R.id.data);
        temp = findViewById(R.id.temp);
        wind = findViewById(R.id.wind);
        wind_info = findViewById(R.id.wind_info);

        weatherProvider = new WeatherProvider();
        weatherProvider.start();

        temp.setText(weatherProvider.getTemp());
        wind_info.setText(weatherProvider.getWind());
    }

//    private void refreshOnTemperature() {
//        try {
//            final URL uri = new URL(WEATHER_URL + WEATHER_API_KEY);
//            final Handler handler = new Handler(); // Запоминаем основной поток
//            new Thread(new Runnable() {
//                public void run() {
//                    HttpsURLConnection urlConnection = null;
//                    try {
//                        urlConnection = (HttpsURLConnection) uri.openConnection();
//                        urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
//                        urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд
//                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
//                        String result = getLines(in);
//                        // преобразование данных запроса в модель
//                        Gson gson = new Gson();
//                        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
//                        // Возвращаемся к основному потоку
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                displayWeather(weatherRequest);
//                            }
//                        });
//                    } catch (Exception e) {
//                        Log.e(TAG, "Fail connection", e);
//                        e.printStackTrace();
//                    } finally {
//                        if (null != urlConnection) {
//                            urlConnection.disconnect();
//                        }
//                    }
//                }
//            }).start();
//        } catch (MalformedURLException e) {
//            Log.e(TAG, "Fail URI", e);
//            e.printStackTrace();
//        }
//
//    }

//    private String getLines(BufferedReader in) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return in.lines().collect(Collectors.joining("\n"));
//        }
//        return null;
//    }

//    private void displayWeather(WeatherRequest weatherRequest){
//        Double d = weatherRequest.getMain().getTemp();
//        long n = Math.round(d);
//        String str = String.valueOf(n);
//
//        if (n>0) {
//            temp.setText("+" + str + "°");
//        } else if (n==0) {
//            temp.setText(str + "°");
//        } else {
//            temp.setText("-" + str + "°");
//        }
//
//        Double speed = weatherRequest.getWind().getSpeed();
//
//        BigDecimal bd = new BigDecimal(Double.toString(speed));
//        bd = bd.setScale(1, RoundingMode.HALF_UP);
//
//
//        String str2 = String.valueOf(bd.doubleValue());
//        wind_info.setText(str2 + " м/с");
//
////        wind_info.setText(String.format("%d", weatherRequest.getWind().getSpeed()));
//
////            pressure.setText(String.format("%d", weatherRequest.getMain().getPressure()));
////            humidity.setText(String.format("%d", weatherRequest.getMain().getHumidity()));
//
//    }
}
