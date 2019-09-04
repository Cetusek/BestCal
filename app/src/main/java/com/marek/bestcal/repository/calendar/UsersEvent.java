package com.marek.bestcal.repository.calendar;


import android.support.annotation.NonNull;
import android.util.Log;

import com.marek.bestcal.main.model.DayListItemEvent;

import java.text.ParseException;
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
    private int calendarId;

    public UsersEvent(String title, Date dateAndTimeFrom, int isAllDay, Date dateAndTimeTo, int calendarId) {
        this.title = title;
        this.dateAndTimeFrom = dateAndTimeFrom;
        this.dateAndTimeTo = dateAndTimeTo;
        this.isAllDay = isAllDay;
        this.calendarId = calendarId;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss zzz");
        return "UsersEvent{" +
                "title='" + title + '\'' +
                ", dateAndTimeFrom=" + sdf.format(dateAndTimeFrom) +'\'' +
                ", dateAndTimeTo=" + sdf.format(dateAndTimeTo) +'\'' +
                ", isAllDay=" + isAllDay +
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


    public ArrayList<DayListItemEvent> toDayListItemEvents____Old() {
        ArrayList<DayListItemEvent> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String eventLabel = getHTML();
        if (isAllDay == 0) {
            list.add(new DayListItemEvent(dateFrom, eventLabel, calendarId));
            //Log.i("MY_APP", "++++ not all day"+sdf.format(dateFrom)+" - "+eventLabel);
        }
        else {
            Date eventPeriodCurrentDate = (Date) dateFrom.clone();
            //Date eventPeriodLastDate = prevDate(truncateDate(dateAndTimeTo)); //Dla calodniowych zdarzen data konca jest przesunieta od 24 od daty poczatkowej
            Date eventPeriodLastDate = prevDate(dateAndTimeTo); //Dla calodniowych zdarzen data konca jest przesunieta od 24 od daty poczatkowej
            if (Integer.parseInt(sdf.format(eventPeriodLastDate)) < Integer.parseInt(sdf.format(dateFrom))) {
                eventPeriodLastDate = dateFrom;
            }
            //Log.i("MY_APP", "eventLabel = "+eventLabel+" "+sdf.format(dateFrom)+" - "+sdf.format(eventPeriodLastDate));
            while (true) {
                if (Integer.parseInt(sdf.format(eventPeriodCurrentDate)) > Integer.parseInt(sdf.format(eventPeriodLastDate))) {//(eventPeriodCurrentDate.compareTo(eventPeriodLastDate) >= 0) {
                    break;
                }
                else {
                    //Log.i("MY_APP", "++++ "+sdf.format(eventPeriodCurrentDate)+" - "+eventLabel);
                    list.add(new DayListItemEvent(eventPeriodCurrentDate, eventLabel, calendarId));
                    eventPeriodCurrentDate = nextDate(eventPeriodCurrentDate);
                }
            }
        }
        return list;
    }

    public ArrayList<DayListItemEvent> toDayListItemEvents() {
        ArrayList<DayListItemEvent> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        int intDateFrom;
        int intDateTo;
        Calendar calendar = Calendar.getInstance();
        String eventLabel = getHTML();
        if (isAllDay == 0) {
            list.add(new DayListItemEvent(dateFrom, eventLabel, calendarId));
        }
        else {
            intDateFrom = Integer.parseInt(sdf.format(dateFrom));
            intDateTo = Integer.parseInt(sdf.format(dateAndTimeTo));
            try {
                calendar.setTime(sdf.parse(Integer.toString(intDateFrom)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            for (int i = intDateFrom; i <= intDateTo; i++) {
                list.add(new DayListItemEvent(calendar.getTime(), eventLabel, calendarId));
                calendar.add(Calendar.DATE, 1);
            }
        }
        return list;
    }

    public DayListItemEvent toDayListItemEvent() {
        return new DayListItemEvent(dateFrom, getHTML(), calendarId);
    }

}
