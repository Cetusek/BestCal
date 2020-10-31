package com.marek.bestcal.anniversary;

import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.marek.bestcal.R;
import com.marek.bestcal.repository.Repo;
import com.marek.bestcal.repository.calendar.UsersCalendar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AnniversaryActivity extends AppCompatActivity {

    Spinner calendar;
    Spinner anniversaryTypeSpinner;
    Button addAnniversariesButton;
    EditText descriptionEditText;
    DatePicker dateFromDatePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anniversary);
        AnniversaryType.setContexts(this);
        mapGUI();
    }

    private void mapGUI() {
        calendar = (Spinner) findViewById(R.id.AnniversaryActivityCalendar);
        calendar.setAdapter(createCalendarSpinnerAdapter());
        anniversaryTypeSpinner = (Spinner) findViewById(R.id.AnniversaryActivityAnniversaryType);
        anniversaryTypeSpinner.setAdapter(createAnniversaryTypeSpinnerAdapter());
        addAnniversariesButton = (Button) findViewById(R.id.AnniversaryActivityAdd);
        addAnniversariesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddAnniversariesButtonPressed();
            }
        });
        descriptionEditText = (EditText) findViewById(R.id.AnniversaryActivityDescription);
        dateFromDatePicker = (DatePicker) findViewById(R.id.AnniversaryActivityDateFrom);
    }

    private ArrayAdapter createCalendarSpinnerAdapter() {
        Repo repo = Repo.getInstance();
        List<UsersCalendar> usersCalendars = repo.getCalendars(this);
        CalendarSpinnerPosition[] positions = new CalendarSpinnerPosition[usersCalendars.size()];
        UsersCalendar usersCalendar;
        for (int i = 0; i < positions.length; i++) {
            CalendarSpinnerPosition position = new CalendarSpinnerPosition();
            usersCalendar = usersCalendars.get(i);
            position.label = usersCalendar.getDisplayedName();
            position.id = usersCalendar.getId();
            positions[i] = position;
        }
        CalendarSpinnerAdapter adapter = new CalendarSpinnerAdapter(this, positions);
        return adapter;
    }

    private ArrayAdapter createAnniversaryTypeSpinnerAdapter() {
        AnniversaryTypeSpinnerAdapter adapter = new AnniversaryTypeSpinnerAdapter(this);
        return adapter;
    }

    private boolean isFormValidated() {
        if (calendar.getSelectedItem() == null) {
            Toast.makeText(this, R.string.calendar_is_not_chosen, Toast.LENGTH_LONG).show();
            return false;
        }
        if (descriptionEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.description_is_required, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void onAddAnniversariesButtonPressed() {
        if (!isFormValidated()) {
            return;
        }
        CalendarSpinnerPosition selectedCalendar = (CalendarSpinnerPosition) calendar.getSelectedItem();
        Calendar calendar = Calendar.getInstance();
        calendar.set(dateFromDatePicker.getYear(), dateFromDatePicker.getMonth() + 1, dateFromDatePicker.getDayOfMonth());
        AnniversaryType anniversaryType = (AnniversaryType) anniversaryTypeSpinner.getSelectedItem();
        Repo.getInstance().insertAnniversaries(this, Integer.toString(selectedCalendar.id), calendar.getTime(), descriptionEditText.getText().toString(), anniversaryType.getSign());
        Toast.makeText(this, R.string.anniversaries_were_added, Toast.LENGTH_LONG).show();
    }

}
