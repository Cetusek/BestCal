package com.marek.bestcal.config.eventcolor;


import android.graphics.Color;
import android.util.Log;

public class EventColor{

    private int calendarId;
    private int backgroundColor;
    private int textColor;

    public EventColor(int calendarId, int calendarColor) {
        this.calendarId = calendarId;
        this.backgroundColor = calendarColor;
        setTextColor();
    }

    private void setTextColor() {

        int red = Color.blue(backgroundColor);
        int green = Color.green(backgroundColor);
        int blue = Color.blue(backgroundColor);

        double y = (299 * red + 587 * green + 114 * blue) / 1000;


        if (y >= 128) {
            textColor = Color.BLACK;
        }
        else {
            textColor = Color.WHITE;
        }

        /*
        textColor = Color.rgb(255-red,
                255-green,
                255-blue);
        */
    }

    public int getCalendarId() {
        return calendarId;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }
}
