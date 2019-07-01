package com.marek.bestcal.config;


import android.content.Context;
import android.graphics.Color;
import android.util.ArrayMap;

import com.marek.bestcal.config.eventcolor.EventColor;
import com.marek.bestcal.config.eventcolor.EventColorManager;
import com.marek.bestcal.repository.Repo;
import com.marek.bestcal.repository.calendar.UsersCalendar;

import java.util.Map;

public class Configuration {

    private static Configuration instance;

    private EventColorManager eventColorManager;

    private Configuration() {
        eventColorManager = EventColorManager.getInstance();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }


    public int getHolidayColor() {
        return Color.RED;
    }

    public int getEventBackgroundColorForCalendar(int calendarId) {
        return eventColorManager.getBackgroundColor(calendarId);
    }

    public int getEventTextColorForCalendar(int calendarId) {
        return eventColorManager.getTextColor(calendarId);
    }

    public void refreshColorInformation(Context context) {
        eventColorManager.refreshList(context);
    }

}
