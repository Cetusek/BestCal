package com.marek.bestcal.main.model;


import android.util.Log;

import com.marek.bestcal.repository.calendar.UsersEvent;

import java.util.ArrayList;
import java.util.Date;

public class DayListItem {

    public Date date;
    public String dayOfMonth;
    public String dayOfWeek;
    public String month;
    public Boolean isHoliday = false;

    private ArrayList<DayListItemEvent> events;

    public void addEvent(DayListItemEvent event) {
        if (events == null) {
            events = new ArrayList<>();
        }
        events.add(event);
    }

    private ArrayList<DayListItemEvent> getEvents() {
        return events;
    }

    public String getHTMLDayContent() {
        StringBuilder sb = new StringBuilder();
        if (events != null) {
            int eventsTotalNo = events.size();
            for (int i = 0; i < eventsTotalNo; i++) {
                sb.append(events.get(i).getEventLabel());
                if (i < eventsTotalNo - 1) {
                    sb.append("<BR>");
                }
            }
        }
        return sb.toString();
    }

    public void clearEvents() {
        if (events != null) {
            events.clear();
        }
    }

}
