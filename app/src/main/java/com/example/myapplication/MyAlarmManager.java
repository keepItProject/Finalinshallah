package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.myapplication.alarm.AlarmData;
import com.example.myapplication.alarm.PreferenceData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Mohamed Fadel
 * Date: 4/4/2020.
 * email: mohamedfadel91@gmail.com.
 */
public class MyAlarmManager {
    private final SharedPreferences prefs;
    private List<AlarmData> alarms;

    public MyAlarmManager(Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        alarms = new ArrayList<>();

        int alarmLength = PreferenceData.ALARM_LENGTH.getValue(context);
        for (int id = 0; id < alarmLength; id++) {
            alarms.add(new AlarmData(id, context));
        }
    }

    public List<AlarmData> getAlarms() {
        return alarms;
    }

    /**
     * Create a new alarm, assigning it an unused preference id.
     *
     * @return          The newly instantiated [AlarmData](./data/AlarmData).
     */
    public AlarmData newAlarm(Context context) {
        AlarmData alarm = new AlarmData(alarms.size(), Calendar.getInstance());
        alarms.add(alarm);
        onAlarmCountChanged(context);
        return alarm;
    }

    /**
     * Update preferences to show that the alarm count has been changed.
     */
    public void onAlarmCountChanged(Context context) {
        PreferenceData.ALARM_LENGTH.setValue(context, alarms.size());
    }
}
