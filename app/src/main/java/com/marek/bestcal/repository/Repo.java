package com.marek.bestcal.repository;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.marek.bestcal.repository.calendar.UsersCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Repo {


    public List<UsersCalendar> getCalendars(Context context) {
        ArrayList<UsersCalendar> list = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "(1=1)";
        String[] selectionArgs = new String[]{};
        String[] eventProjection = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.NAME,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.OWNER_ACCOUNT
        };
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            return list;
        }
        Cursor cursor = contentResolver.query(uri, eventProjection, selection, selectionArgs, null);
        while (cursor.moveToNext()) {
            UsersCalendar usersCalendar = new UsersCalendar(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            list.add(usersCalendar);
        }
        return list;
    }

}
