package com.example.myapplication;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.myapplication.App.CHANNEL_ID;

public class ExampleService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intenti = new Intent(ExampleService.this,MapsActivity.class);
        intenti.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(ExampleService.this,0,intenti,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(ExampleService.this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText("Here is an Emergency")
                .setSmallIcon(R.drawable.ic_logo_app)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        startForeground(1, notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
