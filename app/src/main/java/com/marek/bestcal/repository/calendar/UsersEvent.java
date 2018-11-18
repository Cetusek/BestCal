package com.marek.bestcal.repository.calendar;


import android.support.annotation.NonNull;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class UsersEvent implements Comparable{

    private String title;
    private Date date;
    private Date dateAndTime;
    private int allDay;

    public UsersEvent(String title, Date dateAndTime, int allDay) {
        this.title = title;
        this.dateAndTime = dateAndTime;
        this.allDay = allDay;
        setDate();
    }


    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateAndTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
    }

    @Override
    public String toString() {
        return "UsersEvent{" +
                "title='" + title + '\'' +
                ", date=" + date + '\'' +
                ", dateAndTime=" + dateAndTime +
                '}';
    }

    public Date getDate() {
        return date;
    }

    private String getHHMM() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        //simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(dateAndTime);
    }

    public String getHTML() {
        String result = "";
        if (allDay == 0) {
            result = getHHMM() + " ";
        }
        result += title;
        return result;
    }

    @Override
    public int compareTo(@NonNull Object another) {
        UsersEvent u = (UsersEvent) another;
        return dateAndTime.compareTo(u.dateAndTime);
    }
}
