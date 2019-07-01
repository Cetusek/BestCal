package com.marek.bestcal.repository;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;

import com.marek.bestcal.repository.calendar.UsersCalendar;
import com.marek.bestcal.repository.calendar.UsersEvent;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Repo {

    private static Repo instance;

    private Repo() {
    }

    public static Repo getInstance() {
        if (instance == null) {
            instance = new Repo();
        }
        return instance;
    }


    public List<UsersCalendar> getCalendars(Context context) {
        ArrayList<UsersCalendar> list = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            return list;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "(1=1)";
        String[] selectionArgs = new String[]{};
        String[] eventProjection = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.NAME,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.OWNER_ACCOUNT,
                CalendarContract.Calendars.CALENDAR_COLOR
        };
        Cursor cursor = contentResolver.query(uri, eventProjection, selection, selectionArgs, null);
        while (cursor.moveToNext()) {
            UsersCalendar usersCalendar = new UsersCalendar(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            );
            list.add(usersCalendar);
        }
        return list;
    }

    public List<UsersEvent> getEvents(Context context, Date dateFrom, Date dateTo) {
        long eventDateFromUTC;
        long eventDateFromLocal;
        long eventDateToUTC;
        long eventDateToLocal;
        int isAllDay;
        Date eventDateFrom;
        Date eventDateTo;
        String timeZone;
        ArrayList<UsersEvent> list = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            return list;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = CalendarContract.Events.CONTENT_URI;
        String selection = CalendarContract.Events.DTSTART + " <= ? AND " + CalendarContract.Events.DTEND + " >= ?";
        String[] selectionArgs = new String[]{Long.toString(dateTo.getTime()),Long.toString(dateFrom.getTime())};
        String[] eventProjection =
                {
                        CalendarContract.Events._ID,
                        CalendarContract.Events.TITLE,
                        CalendarContract.Events.DTSTART,
                        CalendarContract.Events.ALL_DAY,
                        CalendarContract.Events.DTEND,
                        CalendarContract.Events.CALENDAR_ID,
                        CalendarContract.Events.EVENT_TIMEZONE
                };
        Cursor cursor = contentResolver.query(uri, eventProjection, selection, selectionArgs, null);
        while (cursor.moveToNext()) {
            timeZone = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.EVENT_TIMEZONE));
            isAllDay = cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.ALL_DAY));
            if (timeZone.equals("UTC")) {
                eventDateFromUTC = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART));
                eventDateFromLocal = eventDateFromUTC - TimeZone.getDefault().getOffset(eventDateFromUTC);
                eventDateToUTC = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTEND));
                eventDateToLocal = eventDateToUTC - TimeZone.getDefault().getOffset(eventDateToUTC);
            }
            else {
                eventDateFromLocal = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART));
                eventDateToLocal = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTEND));
            }
            if (isAllDay == 1 && eventDateFromLocal == eventDateToLocal) {
                eventDateToLocal = eventDateFromLocal + 24*60*60*1000;
            }
            if (isAllDay == 1) {
                eventDateToLocal = eventDateToLocal - 1000;
            }
            eventDateFrom = new Date(eventDateFromLocal);
            eventDateTo = new Date(eventDateToLocal);
            UsersEvent usersEvent = new UsersEvent(cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE)),
                    eventDateFrom,
                    isAllDay,
                    eventDateTo,
                    cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.CALENDAR_ID))
                    );
            list.add(usersEvent);

            /*
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Log.i("MY_APP", "usersEvent = "+cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE))+" "+
            sdf.format(new Date(cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART)))) + " "+
            sdf.format(new Date(cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTEND  ))))
            );
            */

            //Log.i("MY_APP", "Event: "+usersEvent+" TimeZone: "+cursor.getString(6));

            Collections.sort(list);
        }
        return list;
    }



}
