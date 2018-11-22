package com.marek.bestcal.main.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.marek.bestcal.main.BestCalWidget;

import java.io.Serializable;
import java.util.Calendar;

public class BestCalUpdater {



    public BestCalUpdater() {
    }

    private long getMillisecondsToMidnight() {
        Calendar currentTime = Calendar.getInstance();
        Calendar destTime = Calendar.getInstance();
        destTime.add(Calendar.DATE, 1);
        destTime.set(Calendar.HOUR_OF_DAY, 0);
        destTime.set(Calendar.MINUTE, 0);
        destTime.set(Calendar.SECOND, 1);
        destTime.set(Calendar.MILLISECOND, 0);
        return destTime.getTimeInMillis();
    }

    public void registerAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, getMillisecondsToMidnight(), getPendingIntent(context));
    }

    public void unregisterAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }

    private PendingIntent getPendingIntent(Context context) {
        Intent i = new Intent(context, BestCalWidget.class);
        i.setAction(BestCalWidget.REFRESH_FROM_TIMER);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);
        return pendingIntent;
    }

}
