package com.marek.bestcal.config.calendarlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.marek.bestcal.R;
import com.marek.bestcal.config.Configuration;

public class CalendarListActivity extends AppCompatActivity {

    private CalendarListAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_list);
        Configuration.getInstance().refreshColorInformation(this);
        mapGUI();
        deployAdapter();
    }

    private void mapGUI() {
        listView = (ListView) findViewById(R.id.CalendarListListView);
    }

    private void deployAdapter() {
        adapter = new CalendarListAdapter(getApplicationContext(), R.layout.activity_calendar_list_row);
        listView.setAdapter(adapter);
    }

}
