package com.geekbrains.myapplication;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyApp123";



    private String str;
    private int messageId = 1000;

    public void setStr(String str) {
        this.str = str;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Это мое сообщение для записи в журнале");
        Toast.makeText(context, "Режим полета", Toast.LENGTH_LONG).show();



    }

}
