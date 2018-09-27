package com.marek.bestcal.main.model;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DayList {

    private ArrayList<DayListItem> list;

    public DayList() {
        list = new ArrayList<>();
        populateList();
    }

    private void populateList() {
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 50; i++) {
            DayListItem item = new DayListItem();
            item.dayOfMonth = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            item.dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
            item.month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                item.isHoliday = true;
            }
            list.add(item);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    public int size() {
        return list.size();
    }

    public DayListItem getItem(int position) {
        return list.get(position);
    }
}
