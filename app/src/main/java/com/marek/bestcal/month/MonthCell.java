package com.marek.bestcal.month;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marek.bestcal.R;

import java.util.ArrayList;
import java.util.Date;


public class MonthCell extends LinearLayout {

    private final int IN_MONTH_COLOR = Color.BLACK;
    private final int OUT_MONTH_COLOR = Color.GRAY;
    private final int HOLIDAY_DAY_COLOR = Color.RED;
    private final int TODAY_BACKGROUND_COLOR = Color.GREEN;


    private TextView textViewDayNo;
    private LinearLayout content;
    private Date cellDate;

    private int defaultBackgroundColor;

    private ArrayList<TextView> events;


    public MonthCell(Context context) {
        super(context);
        events = new ArrayList<>();
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
        if (isInMonth) {
            textViewDayNo.setTextColor(IN_MONTH_COLOR);
            if (isHoliday) {
                textViewDayNo.setTextColor(HOLIDAY_DAY_COLOR);
            }
        }
        else {
            textViewDayNo.setTextColor(OUT_MONTH_COLOR);
        }
    }

    public void addEvent(String eventName) {
        TextView event = new TextView(getContext());
        event.setText(eventName);
        event.setTextColor(Color.BLACK);
        event.setMaxLines(1);
        event.setTextSize(10);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);;
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
}
