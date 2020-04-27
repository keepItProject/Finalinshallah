package com.example.myapplication.receivers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.myapplication.Application;
import com.example.myapplication.MyAlarmManager;
import com.example.myapplication.NotificationActivity123;
import com.example.myapplication.R;
import com.example.myapplication.alarm.AlarmData;
import com.example.myapplication.docinfoActivity;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "com.singhajit.notificationDemo.channelId";

    private static final String TAG = "AlarmReceiver";

    public static final String EXTRA_ALARM_ID = "james.alarmio.EXTRA_ALARM_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");

        MyAlarmManager alarmio = (MyAlarmManager) ((Application) context.getApplicationContext()).getMyAlarmManager();
        AlarmData alarm = alarmio.getAlarms().get(intent.getIntExtra(EXTRA_ALARM_ID, 0));

        String key = alarm.getKey();


        Intent notificationIntent = new Intent(context, docinfoActivity.class);
        intent.putExtra("user_invoice_key",key);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);

        Notification notification = builder.setContentTitle("KEEP IT APPLACATION")
                .setContentText("ضمانك قارب على الانتهاء!")
                .setTicker("New Message!")
                .setSmallIcon(R.drawable.keepit)
                .setContentIntent(pendingIntent)
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationDemo",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notification);
    }
}
