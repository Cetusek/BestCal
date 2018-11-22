package com.marek.bestcal.repository.calendar;


import android.support.annotation.NonNull;
import android.util.Log;

import com.marek.bestcal.main.model.DayListItemEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UsersEvent implements Comparable{

    private String title;
    private Date dateFrom;
    private Date dateAndTimeFrom;
    private Date dateAndTimeTo;
    private int isAllDay;

    public UsersEvent(String title, Date dateAndTimeFrom, int isAllDay, Date dateAndTimeTo) {
        this.title = title;
        this.dateAndTimeFrom = dateAndTimeFrom;
        this.dateAndTimeTo = dateAndTimeTo;
        this.isAllDay = isAllDay;
        dateFrom = truncateDate(dateAndTimeFrom);
    }


    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateAndTimeFrom);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        dateFrom = calendar.getTime();
    }

    @Override
    public String toString() {
        return "UsersEvent{" +
                "title='" + title + '\'' +
                ", dateFrom=" + dateFrom + '\'' +
                ", dateAndTime=" + dateAndTimeFrom +'\'' +
                ", dateAndTo=" + dateAndTimeTo +
                '}';
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    private String getHHMM(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }

    private String getHTML() {
        String result = "";
        if (isAllDay == 0) {
            result = getHHMM(dateAndTimeFrom) + " ";
        }
        result += title;
        return result;
    }

    @Override
    public int compareTo(@NonNull Object another) {
        UsersEvent u = (UsersEvent) another;
        return dateAndTimeFrom.compareTo(u.dateAndTimeFrom);
    }

    public int getIsAllDay() {
        return isAllDay;
    }

    private Date truncateDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date nextDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    private Date prevDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }


    public ArrayList<DayListItemEvent> toDayListItemEvents() {
        ArrayList<DayListItemEvent> list = new ArrayList<>();
        String eventLabel = getHTML();
        if (isAllDay == 0) {
            list.add(new DayListItemEvent(dateFrom, eventLabel));
        }
        else {
            Date eventPeriodCurrentDate = (Date) dateFrom.clone();
            Date eventPeriodLastDate = prevDate(truncateDate(dateAndTimeTo)); //Dla calodniowych zdarzen data konca jest przesunieta od 24 od daty poczatkowej
            while (true) {
                list.add(new DayListItemEvent(eventPeriodCurrentDate, eventLabel));
                if (eventPeriodCurrentDate.compareTo(eventPeriodLastDate) >= 0) {
                    break;
                }
                else {
                    eventPeriodCurrentDate = nextDate(eventPeriodCurrentDate);
                }
            }
        }
        return list;
    }
}
