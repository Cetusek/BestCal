package com.marek.bestcal.main.model;


import android.support.annotation.NonNull;

import java.util.Date;

public class DayListItemEvent implements Comparable<DayListItemEvent> {

    private Date eventDate;
    private String eventLabel;

    public DayListItemEvent(Date eventDate, String eventLabel) {
        this.eventDate = eventDate;
        this.eventLabel = eventLabel;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public String getEventLabel() {
        return eventLabel;
    }


    @Override
    public int compareTo(@NonNull DayListItemEvent another) {
        return eventDate.compareTo(another.getEventDate());
    }
}
