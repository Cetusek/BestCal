package com.marek.bestcal.month;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.marek.bestcal.R;
import com.marek.bestcal.config.Configuration;
import com.marek.bestcal.main.model.DayListItem;
import com.marek.bestcal.main.model.DayListItemEvent;
import com.marek.bestcal.repository.Repo;
import com.marek.bestcal.repository.calendar.UsersEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MonthActivity extends AppCompatActivity implements MonthCell.MonthCellObserver{


    GridLayout cellLayout;
    MonthCell[] cells;
    MonthDayNameCell[] dayNameCells;

    Button prevMonthButton;
    Button nextMonthButton;

    TextView monthLabel;
    SimpleDateFormat monthLabelFormat;

    int displayedYear;
    int displayedMonth;

    private Configuration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);
        updateConfiguration();
        monthLabelFormat = new SimpleDateFormat("LLLL");

        mapGUI();
        showCurrentMonth();
    }

    private void updateConfiguration() {
        configuration = Configuration.getInstance();
        configuration.refreshColorInformation(this);
    }

    private void mapGUI() {
        cellLayout = (GridLayout) findViewById(R.id.MonthActivityMonthLayout);

        monthLabel = (TextView) findViewById(R.id.MonthActivityMonthLabel);
        monthLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMonthLabelClick();
            }
        });

        prevMonthButton = (Button) findViewById(R.id.MonthActivityPrevMonthButton);
        prevMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPrevMonth();
            }
        });

        nextMonthButton = (Button) findViewById(R.id.MonthActivityNextMonthButton);
        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextMonth();
            }
        });

        mapNameDayCells();
        mapCells();
    }

    private void mapNameDayCells() {
        dayNameCells = new MonthDayNameCell[cellLayout.getColumnCount()];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        for(int i = 0; i < dayNameCells.length; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            dayNameCells[i] = new MonthDayNameCell(this, calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()).substring(0, 3), i == dayNameCells.length - 1);
            cellLayout.addView(dayNameCells[i]);
        }
    }

    private void mapCells() {
        cells = new MonthCell[getMonthCellRows()*cellLayout.getColumnCount()];
        for (byte i = 0; i < cells.length; i++) {
            cells[i] = new MonthCell(this, this);
            cellLayout.addView(cells[i]);
        }
    }

    private void showCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        displayedYear = calendar.get(Calendar.YEAR);
        displayedMonth = calendar.get(Calendar.MONTH);
        showMonth();
    }

    private void showMonth() {
        int offset;

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(displayedYear, displayedMonth, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            offset = 12;
        }
        else {
            offset = 5;
        }
        calendar.add(Calendar.DAY_OF_YEAR, -(offset+calendar.get(Calendar.DAY_OF_WEEK)));
        for (int i = 0; i < cells.length; i++) {
            cells[i].setValue(calendar.getTime(),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.MONTH) == displayedMonth,
                    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY,
                    calendar.get(Calendar.YEAR) == currentYear && calendar.get(Calendar.MONTH) == currentMonth && calendar.get(Calendar.DAY_OF_MONTH) == currentDay);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        clearEventsInCells();
        attachEventsToMonthCells();
        refreshMonthLabel();
    }

    private void clearEventsInCells() {
        for (int i = 0; i < cells.length; i++) {
            cells[i].clearEvents();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            resizeCells();
        }
    }

    private void resizeCells() {
        int cellWidth = cellLayout.getWidth() / cellLayout.getColumnCount();
        int cellHeight = (cellLayout.getHeight() - dayNameCells[0].getHeight()) / getMonthCellRows();
        for (int i = 0; i < dayNameCells.length; i++) {
            dayNameCells[i].setWidth(cellWidth);
        }
        for (int i = 0; i < cells.length; i++) {
            cells[i].setDimensions(cellWidth, cellHeight);
        }
    }

    private int getMonthCellRows() {
        return cellLayout.getRowCount() - 1;
    }

    private void showPrevMonth() {
        if (displayedMonth == Calendar.JANUARY) {
            displayedYear--;
            displayedMonth = Calendar.DECEMBER;
        }
        else {
            displayedMonth--;
        }
        showMonth();
    }

    private void showNextMonth() {
        if (displayedMonth == Calendar.DECEMBER) {
            displayedYear++;
            displayedMonth = Calendar.JANUARY;
        }
        else {
            displayedMonth++;
        }
        showMonth();
    }

    private void refreshMonthLabel() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(displayedYear, displayedMonth, 1);
        monthLabel.setText(monthLabelFormat.format(calendar.getTime())+", "+ displayedYear);

        //monthLabel.setText(calendar.getDisplayName(Calendar.MONTH, Calendar, Locale.getDefault())+", "+ displayedYear);
    }


    private List<DayListItemEvent> getEvents(Date dateFrom, Date dateTo, boolean spreadEvents) {
        Repo repo = Repo.getInstance();
        List<UsersEvent> events = repo.getEvents(this, dateFrom, dateTo);
        List<DayListItemEvent> eventList = new ArrayList<>();
        for (UsersEvent repoEvent : events) {
            if (spreadEvents) {
                eventList.addAll(repoEvent.toDayListItemEvents());
            }
            else {
                eventList.add(repoEvent.toDayListItemEvent());
            }
        }
        Collections.sort(eventList);
        return eventList;
    }

    private void attachEventsToMonthCells() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        int dateCellItem;
        int dateEventItem;
        Date dateFrom = cells[0].getCellDate();
        Date dateTo = getEndOfDate(cells[cells.length-1].getCellDate());
        List<DayListItemEvent> eventList = getEvents(dateFrom, dateTo, true);
        int eventsLeftToAttach = eventList.size();
        int lastAddedEventPos = 0;
        if (eventsLeftToAttach == 0) {
            return;
        }
        for (int i = 0; i < cells.length; i++) {
            dateCellItem = Integer.parseInt(sdf.format(cells[i].getCellDate()));
            for (int j = lastAddedEventPos; j < eventList.size(); j++) {
                DayListItemEvent event = eventList.get(j);
                dateEventItem = Integer.parseInt(sdf.format(event.getEventDate()));
                if (dateCellItem == dateEventItem) {
                    cells[i].addEvent(event);
                    lastAddedEventPos = j;
                    eventsLeftToAttach--;
                    if (eventsLeftToAttach == 0) {
                        return;
                    }
                }
                else {
                    if (dateCellItem < dateEventItem) {
                        break;
                    }
                }
            }
        }
    }

    private void onMonthLabelClick() {
        showCurrentMonth();
    }

    private Date getEndOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 24);
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTime();
    }

    @Override
    public void onMonthCellClick(Date cellDate) {
        DialogCell dialog = new DialogCell(this, cellDate);
        dialog.deployEvents(getEvents(cellDate, getEndOfDate(cellDate), false));
        dialog.show();
    }
}
