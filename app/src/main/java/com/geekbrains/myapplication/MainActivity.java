package com.geekbrains.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyApp123";
    Button button;
    public static final String WHERE_MY_CAT_ACTION = "ru.alexanderklimov.action.CAT";
    public static final String ALARM_MESSAGE = "Срочно пришлите кота!";

    private TimeBroadcastReceiver mTimeBroadCastReceiver = new TimeBroadcastReceiver();
    private MyReceiver myReceiver = new MyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(myReceiver,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        initView();

    }

    private void initView() {
        button = findViewById(R.id.bottom);
    }

    public void sendMessage(View view) {
        Log.i(TAG, "Это мое сообщение для записи в журнале");
        myReceiver.setStr("OLOLOLO");
        this.registerReceiver(myReceiver,
                new IntentFilter(WHERE_MY_CAT_ACTION));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

}
