package com.marek.bestcal.main.model;


import android.support.annotation.NonNull;

import java.util.Date;

public class DayListItemEvent implements Comparable<DayListItemEvent> {

    private Date eventDate;
    private String eventLabel;
    private int calendarId;

    public DayListItemEvent(Date eventDate, String eventLabel, int calendarId) {
        this.eventDate = eventDate;
        this.eventLabel = eventLabel;
        this.calendarId = calendarId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public String getEventLabel() {
        return eventLabel;
    }


    public int getCalendarId() {
        return calendarId;
    }

    @Override
    public int compareTo(@NonNull DayListItemEvent another) {
        return eventDate.compareTo(another.getEventDate());
    }
}
