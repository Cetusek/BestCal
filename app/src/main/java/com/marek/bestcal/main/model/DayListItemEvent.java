package com.marek.bestcal.main.model;


import java.util.Date;

public class DayListItemEvent {

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
}
