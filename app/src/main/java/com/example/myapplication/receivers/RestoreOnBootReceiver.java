package com.example.myapplication.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.myapplication.Application;
import com.example.myapplication.MyAlarmManager;
import com.example.myapplication.alarm.AlarmData;

public class RestoreOnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MyAlarmManager alarmio = (MyAlarmManager) ((Application) context.getApplicationContext()).getMyAlarmManager();
        for (AlarmData alarm : alarmio.getAlarms()) {
            alarm.schedule(context);
        }
    }
}
