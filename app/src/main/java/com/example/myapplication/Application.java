package com.example.myapplication;

/**
 * Created by Mohamed Fadel
 * Date: 4/25/2020.
 * email: mohamedfadel91@gmail.com.
 */
public class Application extends android.app.Application {
    private MyAlarmManager myAlarmManager;

    @Override
    public void onCreate() {
        super.onCreate();

        myAlarmManager = new MyAlarmManager(this);
    }

    public MyAlarmManager getMyAlarmManager() {
        return myAlarmManager;
    }
}
