package com.example.alarmdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    final String ALARM_TITLE = "Alarm Demo";
    final String ALARM_CONTENT = "This is notification from AlarmDemo";
    final String NOTIFICATION_CHANNEL_ID = "1";
    final String NOTIFICATION_CHANNEL_NAME = "1";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().compareTo("ALARM_ACTION") == 0) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(context,
                    NOTIFICATION_CHANNEL_ID);
            notiBuilder.setContentTitle(ALARM_TITLE);
            notiBuilder.setContentText(ALARM_CONTENT);
            notiBuilder.setSmallIcon(android.R.drawable.ic_menu_my_calendar);
            notiBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
            notiBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
            int notificationId = intent.getIntExtra("id",0);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(notificationId, notiBuilder.build());
        }
    }
}