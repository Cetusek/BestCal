package com.marek.bestcal.main.model;


import android.content.Context;
import android.util.Log;

import com.marek.bestcal.month.MonthCell;
import com.marek.bestcal.repository.Repo;
import com.marek.bestcal.repository.calendar.UsersEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DayList {

    private static DayList instance;

    private ArrayList<DayListItem> list;
    private Context context;
    private int eventsTotal;

    public static DayList getInstance(Context context) {
        if (instance == null) {
            instance = new DayList(context);
        }
        return instance;
    }

    private DayList(Context context) {
        list = new ArrayList<>();
        this.context = context;
        populateList();
    }

    private void populateList() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < 50; i++) {
            DayListItem item = new DayListItem();
            item.date = calendar.getTime();
            item.dayOfMonth = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            item.dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()).substring(0, 3);
            item.month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()).substring(0 ,3);
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                item.isHoliday = true;
            }
            list.add(item);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        attachEvents();
    }

    private void attachEvents() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        int dateDayItem;
        int dateEventItem;
        Date dateFrom = list.get(0).date;
        Date dateTo = list.get(list.size()-1).date;
        List<DayListItemEvent> events = readEvents(dateFrom, dateTo);
        int eventsLeftToAttach = events.size();
        int lastAddedEventPos = 0;
        if (eventsLeftToAttach == 0) {
            return;
        }
        for (DayListItem day : list) {
            dateDayItem = Integer.parseInt(sdf.format(day.date));
            for (int i = lastAddedEventPos; i < events.size(); i++) {
                DayListItemEvent event = events.get(i);
                dateEventItem = Integer.parseInt(sdf.format(event.getEventDate()));
                //Log.i("MY_APP", dateDayItem+" "+dateEventItem+" "+event.getEventLabel());
                if (dateDayItem == dateEventItem) {
                    //Log.i("MY_APP", "++++");
                    day.addEvent(event);
                    eventsTotal++;
                    lastAddedEventPos = i;
                    eventsLeftToAttach--;
                    if (eventsLeftToAttach == 0) {
                        //Log.i("MY_APP", "return");
                        return;
                    }
                }
                else {
                    if (dateDayItem < dateEventItem) {
                       // Log.i("MY_APP", "break");
                        break;
                    }
                }
            }
        }
    }


    private List<UsersEvent> readEventsFromRepo(Date dateFrom, Date dateTo) {
        Repo repo = new Repo();
        return repo.getEvents(context, dateFrom, dateTo);
    }


    private List<DayListItemEvent> readEvents(Date dateFrom, Date dateTo) {
        List<UsersEvent> repoEventsList = readEventsFromRepo(dateFrom, dateTo);
        List<DayListItemEvent> eventList = new ArrayList<>();
        for (UsersEvent repoEvent : repoEventsList) {
            eventList.addAll(repoEvent.toDayListItemEvents());
        }
        Collections.sort(eventList);
        /*
        Log.i("MY_APP", "After sort");
        for (DayListItemEvent item : eventList) {
            Log.i("MY_APP", item.getEventDate()+" "+item.getEventLabel());
        }
        */
        return eventList;
    }

    public int size() {
        return list.size();
    }

    public DayListItem getItem(int position) {
        return list.get(position);
    }

    private void clearAllBeforeRefresh() {
        list.clear();
        eventsTotal = 0;
    }

    public int getEventsTotal() {
        return eventsTotal;
    }

    public void refreshList() {
        clearAllBeforeRefresh();
        populateList();
    }

}
