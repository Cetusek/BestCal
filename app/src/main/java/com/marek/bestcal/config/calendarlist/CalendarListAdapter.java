package com.marek.bestcal.config.calendarlist;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.marek.bestcal.R;
import com.marek.bestcal.repository.Repo;
import com.marek.bestcal.repository.calendar.UsersCalendar;

import org.w3c.dom.Text;

import java.util.List;

public class CalendarListAdapter extends ArrayAdapter<CalendarListItem> {


    public CalendarListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        populateList();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CalendarListItem listItem = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_calendar_list_row, parent, false);
        Switch calendarSwitch = (Switch) layout.findViewById(R.id.CalendarListRowSwitch);
        TextView displayedName = (TextView) layout.findViewById(R.id.CalendarListRowDisplayedName);
        displayedName.setText(listItem.getDisplayedName());
        TextView name = (TextView) layout.findViewById(R.id.CalendarListRowAccountName);
        name.setText(listItem.getAccountName());
        return layout;
    }

    private void populateList() {
        Repo repo = new Repo();
        List<UsersCalendar> calendars = repo.getCalendars(getContext());
        for (UsersCalendar calendar:calendars
             ) {
            add(new CalendarListItem(calendar));
        }
    }

}
