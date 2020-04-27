package com.example.myapplication.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.addDoc2Activity;
import com.example.myapplication.receivers.AlarmReceiver;

import java.util.Calendar;

/**
 * Created by Mohamed Fadel
 * Date: 4/25/2020.
 * email: mohamedfadel91@gmail.com.
 */
public class AlarmData implements Parcelable {

    public int id;
    public String key;
    public Calendar time;

    public AlarmData(int id, Calendar time) {
        this.id = id;
        this.time = time;
    }

    public AlarmData(int id, Context context) {
        this.id = id;
        this.key = PreferenceData.ALARM_KEY.getSpecificOverriddenValue(context, "" , id);
        this.time = Calendar.getInstance();
        this.time.setTimeInMillis((long) PreferenceData.ALARM_TIME.getSpecificValue(context, id));
    }

    /**
     * Sets the user-defined "name" of the alarm.
     *
     * @param context An active context instance.
     * @param key     The new name to be set.
     */
    public void setKey(Context context, String key) {
        this.key = key;
        PreferenceData.ALARM_KEY.setValue(context, key, id);
    }

    /**
     * Change the scheduled alarm time,
     *
     * @param context    An active context instance.
     * @param timeMillis The UNIX time (in milliseconds) that the alarm should ring at.
     *                   This is independent to days; if the time correlates to 9:30 on
     *                   a Tuesday when the alarm should only repeat on Wednesdays and
     *                   Thursdays, then the alarm will next ring at 9:30 on Wednesday.
     */
    public void setTime(Context context, long timeMillis) {
        time.setTimeInMillis(timeMillis);
        PreferenceData.ALARM_TIME.setValue(context, timeMillis, id);
        schedule(context);
    }

    public void schedule(Context context) {
        setAlarm(context, id, time.getTimeInMillis());
    }

    /**
     * Schedule a time for the alarm to ring at.
     *
     * @param context    An active context instance.
     * @param timeMillis A UNIX timestamp specifying the next time for the alarm to ring.
     */
    private void setAlarm(Context context, int id, long timeMillis) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            manager.setAlarmClock(
                    new AlarmManager.AlarmClockInfo(
                            timeMillis,
                            PendingIntent.getActivity(context, 0, new Intent(context, addDoc2Activity.class), 0)
                    ),
                    getIntent(context, id)
            );
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                manager.setExact(AlarmManager.RTC_WAKEUP, timeMillis, getIntent(context, id));
            else
                manager.set(AlarmManager.RTC_WAKEUP, timeMillis, getIntent(context, id));

            Intent intent = new Intent("android.intent.action.ALARM_CHANGED");
            intent.putExtra("alarmSet", true);
            context.sendBroadcast(intent);
        }

        manager.set(AlarmManager.RTC_WAKEUP,
                timeMillis,
                getIntent(context, id));

    }

    /**
     * Returns the user-defined "name" of the alarm, defaulting to
     * "Alarm (1..)" if unset.
     *
     * @param context       An active context instance.
     * @return              The alarm name, as a string.
     */
    public String getKey() {
        return key;
    }

    /**
     * The intent to fire when the alarm should ring.
     *
     * @param context An active context instance.
     * @return A PendingIntent that will open the alert screen.
     */
    private static PendingIntent getIntent(Context context, int id) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.EXTRA_ALARM_ID, id);
        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.key);
        dest.writeSerializable(this.time);
    }

    protected AlarmData(Parcel in) {
        this.id = in.readInt();
        this.key = in.readString();
        this.time = (Calendar) in.readSerializable();
    }

    public static final Creator<AlarmData> CREATOR = new Creator<AlarmData>() {
        @Override
        public AlarmData createFromParcel(Parcel source) {
            return new AlarmData(source);
        }

        @Override
        public AlarmData[] newArray(int size) {
            return new AlarmData[size];
        }
    };
}
