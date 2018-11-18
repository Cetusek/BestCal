package com.marek.bestcal.main;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BestCalThread extends Thread {

    private Callback callback;

    private static BestCalThread instance;

    public static BestCalThread getInstance(Callback callback) {
        if (instance == null) {
            instance = new BestCalThread(callback);
        }
        return instance;
    }

    private BestCalThread(Callback callback) {
        this.callback = callback;
    }

    private long getSleepTime() {
        Calendar currentTime = Calendar.getInstance();
        Calendar destTime = Calendar.getInstance();
        destTime.add(Calendar.DATE, 1);
        destTime.set(Calendar.HOUR_OF_DAY, 0);
        destTime.set(Calendar.MINUTE, 0);
        destTime.set(Calendar.SECOND, 1);
        /*
        SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Log.i("MY_APP", f.format(currentTime.getTime()));
        Log.i("MY_APP", f.format(destTime.getTime()));
        Log.i("MY_APP", Long.toString(destTime.getTimeInMillis() - currentTime.getTimeInMillis()));
        */
        return destTime.getTimeInMillis() - currentTime.getTimeInMillis();
    }


    public interface Callback {
        public void threadTask();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(getSleepTime());
                callback.threadTask();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
