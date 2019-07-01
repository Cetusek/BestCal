package com.marek.bestcal.month;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marek.bestcal.R;
import com.marek.bestcal.config.Configuration;
import com.marek.bestcal.main.model.DayListItemEvent;

import java.util.ArrayList;
import java.util.Date;


public class MonthCell extends LinearLayout {

    private final int IN_MONTH_COLOR = Color.BLACK;
    private final int OUT_MONTH_COLOR = Color.GRAY;
    private final int HOLIDAY_DAY_COLOR = Color.RED;
    private final int TODAY_BACKGROUND_COLOR = Color.GREEN;

    private MonthCellObserver monthCellObserver;


    private TextView textViewDayNo;
    private LinearLayout content;
    private Date cellDate;

    private int defaultBackgroundColor;

    private ArrayList<TextView> events;

    private Configuration configuration;


    public MonthCell(Context context, MonthCellObserver monthCellObserver) {
        super(context);
        this.monthCellObserver = monthCellObserver;
        events = new ArrayList<>();
        configuration = Configuration.getInstance();
        inflate();
        mapGUI();
    }

    private void inflate() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        content = (LinearLayout) inflater.inflate(R.layout.month_cell, this);
        content = (LinearLayout) content.findViewById(R.id.MonthCellContent);
    }

    private void mapGUI() {
        textViewDayNo = (TextView) content.findViewById(R.id.MonthCellDayNo);
        if (textViewDayNo.getBackground() instanceof ColorDrawable) {
            ColorDrawable cd = (ColorDrawable) textViewDayNo.getBackground();
            defaultBackgroundColor = cd.getColor();
        }
        content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onContentClick();
            }
        });
    }

    private void onContentClick() {
        monthCellObserver.onMonthCellClick(cellDate);
    }

    public void setDimensions(int width, int height) {
        GridLayout.LayoutParams params = (GridLayout.LayoutParams) getLayoutParams();
        params.width = width;
        params.height = height;
        setLayoutParams(params);
    }

    public void setValue(Date date, int dayNo, boolean isInMonth, boolean isHoliday, boolean isToday) {
        cellDate = date;
        textViewDayNo.setBackgroundColor(defaultBackgroundColor);
        if (isToday) {
            textViewDayNo.setBackgroundColor(TODAY_BACKGROUND_COLOR);
        }
        textViewDayNo.setText(Integer.toString(dayNo));
        if (isHoliday) {
            textViewDayNo.setTextColor(HOLIDAY_DAY_COLOR);
        }
        if (isInMonth) {
            //textViewDayNo.setTextColor(IN_MONTH_COLOR);
            textViewDayNo.setAlpha(1.0f);
        }
        else {
            //textViewDayNo.setTextColor(OUT_MONTH_COLOR);
            textViewDayNo.setAlpha(0.3f);
        }
    }

    public void addEvent(DayListItemEvent itemEvent) {
        TextView event = new TextView(getContext());
        event.setText(itemEvent.getEventLabel());
        event.setBackgroundColor(configuration.getEventBackgroundColorForCalendar(itemEvent.getCalendarId()));
        event.setTextColor(configuration.getEventTextColorForCalendar(itemEvent.getCalendarId()));
        event.setMaxLines(1);
        event.setTextSize(11);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);;
        params.setMarginStart(2);
        event.setLayoutParams(params);

        content.addView(event);
        events.add(event);
    }


    public void clearEvents()  {
        for (int i = 0; i < events.size(); i++) {
            content.removeView(events.get(i));
        }
        events.clear();
    }

    public Date getCellDate() {
        return cellDate;
    }

    public interface MonthCellObserver {
        public void onMonthCellClick(Date cellDate);
    }
}
