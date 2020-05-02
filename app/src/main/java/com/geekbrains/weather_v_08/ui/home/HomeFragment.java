package com.geekbrains.weather_v_08.ui.home;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;


import com.geekbrains.weather_v_08.R;
import com.geekbrains.weather_v_08.ui.home.modelWeatherDay.MyWorker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private TextView cityName, data, temperature, feels, wind, humidity;
    private RecyclerView hourRecycler;
    private RecyclerView dayRecycler;
    private EditText editText;
    private boolean nowDay;
    private String city = "Moscow";

    public HomeFragment() {
    }
    public HomeFragment(String city) {
        this.city = city;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void onStart() {
        super.onStart();
        Log.i("FFFAAA", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("FFFAAA", "onResume");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("FFFAAA", "onCreateView");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("FFFAAA", "onViewCreated");

        Bundle bundle = getArguments();
        if (bundle != null) {
            city = bundle.getString("city");
        }

        initView();
        data.setText(dataTime());
        Date currentDate = new Date();
        if (UpdateBackground(currentDate)) {
            view.setBackgroundResource(R.drawable.fon_day);
            nowDay = true;
        } else {
            view.setBackgroundResource(R.drawable.fon_night);
            nowDay = false;
        }

        final Data myData = new Data.Builder()
                .putString("city", city)
                .build();

        OneTimeWorkRequest myWorkRequest1 = new OneTimeWorkRequest
                .Builder(MyWorker.class)
                .setInputData(myData)
                .build();
        WorkManager.getInstance().enqueue(myWorkRequest1);

        WorkManager mWorkManager = WorkManager.getInstance();
        mWorkManager.getWorkInfoByIdLiveData(myWorkRequest1.getId())
                .observe(getActivity(), new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo.getState().isFinished()) {
                            Data data = workInfo.getOutputData();
                            String cityData = data.getString("city");
                            String tempData = data.getString("temp");
                            String feelsData = data.getString("feels");
                            String windData = data.getString("wind");
                            String humidityData = data.getString("humidity");

                            cityName.setText(cityData);
                            temperature.setText(tempData);
                            feels.setText(feelsData);
                            wind.setText(windData);
                            humidity.setText(humidityData);

                            String[] arrHourTime = data.getStringArray("arrHourTime");
                            double[] arrHourTemp = data.getDoubleArray("arrHourTemp");
                            int[] arrHourIcon = data.getIntArray("arrHourIcon");

                            String[] arrDayTime = data.getStringArray("arrDayTime");
                            double[] arrDayTemp = data.getDoubleArray("arrDayTemp");
                            int[] arrDayIcon = data.getIntArray("arrDayIcon");

                            initRecyclerHour(arrHourTime, arrHourTemp, arrHourIcon);
                            initRecyclerDay(arrDayTime, arrDayTemp, arrDayIcon);
                        }
                    }
                });
    }

    private void initRecyclerHour(String[] arrHourTime, double[] arrHourTemp, int[] arrHourIcon) {
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,  false);
        hourRecycler.setLayoutManager(linearLayout);

        List<TimeWeather> listTime;
        listTime = uppTimeWeather(arrHourTime, arrHourTemp, arrHourIcon);

        HourRecyclerAdapter adapter = new HourRecyclerAdapter(getContext(), listTime);
        hourRecycler.setAdapter(adapter);
    }

    private void initRecyclerDay(String[] arrDayTime, double[] arrDayTemp, int[] arrDayIcon) {
        LinearLayoutManager linearLayoutDay = new LinearLayoutManager(getContext());
        dayRecycler.setLayoutManager(linearLayoutDay);

        List<TimeWeather> listTimeDay;
        listTimeDay = uppTimeWeatherDay(arrDayTime, arrDayTemp, arrDayIcon);

        DayRecyclerAdapter adapterDay = new DayRecyclerAdapter(getContext(), listTimeDay);
        dayRecycler.setAdapter(adapterDay);
    }

    private List<TimeWeather> uppTimeWeather(String[] arrTime, double[] arrTemp, int[] arrInteger) {
        List<TimeWeather> listTime = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            listTime.add(new TimeWeather(arrTime[i], arrTemp[i], arrInteger[i]));
        }
        return listTime;
    }

    private List<TimeWeather> uppTimeWeatherDay(String[] arrTime, double[] arrTemp, int[] arrInteger) {
        List<TimeWeather> listTime = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            listTime.add(new TimeWeather(arrTime[i], arrTemp[i], arrInteger[i]));
        }
        return listTime;
    }

    private void initView() {
        cityName = getActivity().findViewById(R.id.city_name);
        data = getActivity().findViewById(R.id.data);
        temperature = getActivity().findViewById(R.id.temperature);
        wind = getActivity().findViewById(R.id.wind);
        humidity = getActivity().findViewById(R.id.humidity);
        feels = getActivity().findViewById(R.id.feels);
        hourRecycler = getActivity().findViewById(R.id.horizontal_wether);
        dayRecycler = getActivity().findViewById(R.id.future_wether);
    }

    private String dataTime() {
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String timeText = timeFormat.format(currentDate);

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK)-1;

        String[] names = getResources().getStringArray(R.array.week);

        String result = names[dayOfWeek] + ", " +  dateText + ", " + timeText;
        return result;
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
