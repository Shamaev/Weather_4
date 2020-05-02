package com.geekbrains.weather_v_08.ui.home.modelWeatherDay;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.geekbrains.weather_v_08.R;
import com.geekbrains.weather_v_08.ui.home.modelWeather.OpenWeather;
import com.geekbrains.weather_v_08.ui.home.modelWeather.WeatherHandler;
import com.geekbrains.weather_v_08.ui.home.modelWeather.WeatherRequest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyWorker extends Worker {
    private ArrayList<String> al;
    private OpenWeather openWeather;
    private OpenWeatherDay openWeatherDay;
    private static final int AbsoluteZero = -273;
    private WeatherHandler weatherHandler = new WeatherHandler();

    private String[] arrHourTime;
    private double[] arrHourTemp;
    private int[] arrHourIcon;

    private String[] arrDayTime;
    private double[] arrDayTemp;
    private int[] arrDayIcon;


    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String city = getInputData().getString("city");
        initRetrofit();
        try {
            requestRetrofit(city, "6bdcb2b7e6c07df82dc5d96dc7b9ab2e");
        } catch (IOException e) {
            e.printStackTrace();
        }
        initRetrofitDay();
        try {
            requestRetrofitDay(city, "6bdcb2b7e6c07df82dc5d96dc7b9ab2e");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String temp = al.get(0);
        String feels = al.get(3);
        String wind = al.get(1);
        String humidity = al.get(2);

        Data output = new Data.Builder()
                .putString("city", city)
                .putString("temp", temp)
                .putString("feels", feels)
                .putString("wind", wind)
                .putString("humidity", humidity)
                .putStringArray("arrHourTime", arrHourTime)
                .putDoubleArray("arrHourTemp", arrHourTemp)
                .putIntArray("arrHourIcon", arrHourIcon)
                .putStringArray("arrDayTime", arrDayTime)
                .putDoubleArray("arrDayTemp", arrDayTemp)
                .putIntArray("arrDayIcon", arrDayIcon)
                .build();

        return Result.success(output);
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openWeather = retrofit.create(OpenWeather.class);
    }

    private void requestRetrofit(String city, String keyApi) throws IOException {
        Response<WeatherRequest> response = openWeather.loadWeather(city, keyApi).execute();
        if (response.body() != null) {

            Double resultTemp = response.body().getMain().getTemp();
            weatherHandler.setTemp(resultTemp + AbsoluteZero);

            Double resultWind = response.body().getWind().getSpeed();
            weatherHandler.setSpeed(resultWind);

            Integer resultHumidity = response.body().getMain().getHumidity();
            weatherHandler.setHumidity(resultHumidity);

            Double resultFeels = response.body().getMain().getFeelsLike();
            weatherHandler.setFeels(resultFeels + AbsoluteZero);

            al = new ArrayList<>();
            al.add(0, weatherHandler.getTemp());
            al.add(1, weatherHandler.getSpeed());
            al.add(2, weatherHandler.getHumidity());
            al.add(3, weatherHandler.getFeels());
        }
    }


    private void initRetrofitDay() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openWeatherDay = retrofit.create(OpenWeatherDay.class);
    }

    private void requestRetrofitDay(String city, String keyApi) throws IOException {
        Response<WeatherRequestDay> responseDay = openWeatherDay.loadWeather(city, keyApi).execute();
        if (responseDay.body() != null) {
            ArrayList<ListItem> listDay = responseDay.body().getList();

            ArrayList<String> alDayTime = new ArrayList<>();
            ArrayList<Double> alDayTemp = new ArrayList<>();
            ArrayList<Integer> alDayIcon = new ArrayList<>();

            for (int i = 8; i <= 32; i=i+8) {
                ListItem item = listDay.get(i);

                int clouds = item.getClouds().getAll();
                Double rain;
                if (item.getRain() == null) {
                    rain = 0.0;
                } else {
                    rain = item.getRain().get3h();
                }

                Double snow;
                if (item.getSnow() == null) {
                    snow = 0.0;
                } else {
                    snow = item.getSnow().get3h();
                }

                int icon;

                int iconCloudy;
                if (clouds < 40) {
                    iconCloudy = R.drawable.blue_sun;
                } else  {
                    iconCloudy = R.drawable.blue_cloudy;
                }

                int iconRain = 0;
                if (rain > 0.5) {
                    iconRain = R.drawable.blue_rain;
                }

                int iconSnow = 0;
                if (snow > 0.5) {
                    iconRain = R.drawable.blue_snow;
                }

                if (iconRain == 0 && iconSnow == 0) {
                    icon = iconCloudy;
                } else if (iconRain > 0 && iconSnow == 0) {
                    icon = iconRain;
                } else {
                    icon = iconSnow;
                }

                String timeText = item.getDt_txt();
                Double tempText = item.getMain().getTemp();
                int iconText = icon;

                alDayTime.add(timeText);
                alDayTemp.add(tempText);
                alDayIcon.add(iconText);
            }

            arrDayTime = new String[alDayTime.size()];
            for (int i = 0; i<alDayTime.size(); i++) {
                arrDayTime[i] = alDayTime.get(i);
            }
            arrDayTemp = new double[alDayTemp.size()];
            for (int i = 0; i<alDayTemp.size(); i++) {
                arrDayTemp[i] = alDayTemp.get(i);
            }
            arrDayIcon = new int[alDayIcon.size()];
            for (int i = 0; i<alDayIcon.size(); i++) {
                arrDayIcon[i] = alDayIcon.get(i);
            }

            ArrayList<String> alHourTime= new ArrayList<>();
            ArrayList<Double> alHourTemp = new ArrayList<>();
            ArrayList<Integer> alHourIcon = new ArrayList<>();

            for (int i = 1; i < 10; i++) {
                ListItem item = listDay.get(i);

                int clouds = item.getClouds().getAll();
                Double rain;
                if (item.getRain() == null) {
                    rain = 0.0;
                } else {
                    rain = item.getRain().get3h();
                }

                Double snow;
                if (item.getSnow() == null) {
                    snow = 0.0;
                } else {
                    snow = item.getSnow().get3h();
                }

                int icon;

                int iconCloudy;
                if (clouds < 33) {
                    iconCloudy = R.drawable.sun;
                } else if (clouds > 33 && clouds < 66) {
                    iconCloudy = R.drawable.sun2;
                } else  {
                    iconCloudy = R.drawable.cloudy;
                }


                int iconRain = 0;
                if (rain > 0.5 && rain < 2.0) {
                    iconRain = R.drawable.rain;
                } else if (rain > 2.0) {
                    iconRain = R.drawable.rain2;
                }

                int iconSnow = 0;
                if (snow > 0.5) {
                    iconRain = R.drawable.snow;
                }

                if (iconRain == 0 && iconSnow == 0) {
                    icon = iconCloudy;
                } else if (iconRain > 0 && iconSnow == 0) {
                    icon = iconRain;
                } else {
                    icon = iconSnow;
                }

                long longTime = item.getDt()*1000-10800000;
                Date date = new Date(longTime);

//                Calendar calendar = new GregorianCalendar();
//                long longcal = calendar.getTime().getTime();
//                Date ddd = calendar.getTime();
//                Log.i("TAG123", String.valueOf(longcal));
//                Log.i("TAG123", String.valueOf(longTime));
//                Log.i("TAG123", date.toString());

                if (!UpdateBackground(date)) {
                    switch (icon) {
                        case R.drawable.sun:  icon = R.drawable.night_sun;
                            break;
                        case R.drawable.sun2:  icon = R.drawable.night_sun;
                            break;
                        case R.drawable.cloudy:  icon = R.drawable.night_cloudy;
                            break;
                        case R.drawable.rain:  icon = R.drawable.night_rain1;
                            break;
                        case R.drawable.rain2:  icon = R.drawable.night_rain2;
                            break;
                        case R.drawable.snow:  icon = R.drawable.night_snow;
                            break;
                    }
                }

                String timeText = item.getDt_txt();
                Double tempText = item.getMain().getTemp();
                int iconText = icon;

                alHourTime.add(timeText);
                alHourTemp.add(tempText);
                alHourIcon.add(iconText);

            }

            arrHourTime = new String[alHourTime.size()];
            for (int i = 0; i<alHourTime.size(); i++) {
                arrHourTime[i] = alHourTime.get(i);
            }

            arrHourTemp = new double[alHourTemp.size()];
            for (int i = 0; i<alHourTemp.size(); i++) {
                arrHourTemp[i] = alHourTemp.get(i);
            }

            arrHourIcon = new int[alHourIcon.size()];
            for (int i = 0; i<alHourIcon.size(); i++) {
                arrHourIcon[i] = alHourIcon.get(i);
            }
        }
    }

    private boolean UpdateBackground(Date currentDate) {
        DateFormat timeFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        DateFormat formatE = new SimpleDateFormat("EEE", Locale.ENGLISH);
        DateFormat formatM = new SimpleDateFormat("MMM", Locale.ENGLISH);
        DateFormat formatD = new SimpleDateFormat("dd", Locale.ENGLISH);
        DateFormat formatZ = new SimpleDateFormat("zzz", Locale.ENGLISH);
        DateFormat formatY = new SimpleDateFormat("yyyy", Locale.ENGLISH);

        String textE = formatE.format(currentDate);
        String textM = formatM.format(currentDate);
        String textD = formatD.format(currentDate);
        String textZ = formatZ.format(currentDate);
        String textY = formatY.format(currentDate);

        String timeNight = textE + " " + textM + " " + textD + " "
                + "21:00:00" + " " + textZ + " " + textY;
        String timeDay = textE + " " + textM + " " + textD + " "
                + "06:00:00" + " " + textZ + " " + textY;

        try {
            Date dateNight = timeFormat.parse(timeNight);
            Date dateDay = timeFormat.parse(timeDay);

            return currentDate.after(dateDay) && currentDate.before(dateNight);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
