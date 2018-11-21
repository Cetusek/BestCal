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

public class BestCalUpdater {



    public BestCalUpdater() {
    }


    /*
    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        // Put here YOUR code.
        Log.i("MY_APP", "Alarm is fired");


        updateIsRequested();
        setExact(context);

        wl.release();

    }
    */


    public void registerAlarm(Context context) {
        AlarmManager alarmManager = ( AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, BestCalWidget.class);
        i.setAction(BestCalWidget.REFRESH_FROM_TIMER);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ (1000 * 5), pendingIntent); // Millisec * Second * Minute
    }

    public void unregisterAlarm(Context context) {
        AlarmManager alarmManager = ( AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, BestCalWidget.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);
        alarmManager.cancel(pendingIntent);
    }

}
