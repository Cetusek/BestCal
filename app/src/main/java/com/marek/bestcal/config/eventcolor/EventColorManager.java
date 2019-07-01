package com.marek.bestcal.config.eventcolor;

import android.content.Context;
import android.util.ArrayMap;

import com.marek.bestcal.repository.Repo;
import com.marek.bestcal.repository.calendar.UsersCalendar;

import java.util.List;

public class EventColorManager {

    private static EventColorManager instance;

    private ArrayMap<Integer, EventColor> list;

    private EventColorManager() {
        list = new ArrayMap<>();
    }

    public static EventColorManager getInstance() {
        if (instance == null) {
            instance = new EventColorManager();
        }
        return instance;
    }

    public void refreshList(Context context) {
        list.clear();
        Repo repo = Repo.getInstance();
        List<UsersCalendar> calendars = repo.getCalendars(context);
        for (UsersCalendar calendar : calendars) {
            list.put(calendar.getId(), new EventColor(calendar.getId(), calendar.getColor()));
        }
    }

    public int getBackgroundColor(int calendarId) {
        return list.get(calendarId).getBackgroundColor();
    }

    public int getTextColor(int calendarId) {
        return list.get(calendarId).getTextColor();
    }

}
