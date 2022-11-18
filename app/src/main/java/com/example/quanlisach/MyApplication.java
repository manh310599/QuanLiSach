package com.example.quanlisach;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;


public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        createChannelNotification();
    }

    public static final String CHANNEL_ID = "mua bán sách";

    private  void  createChannelNotification () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Mua bán sách",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
