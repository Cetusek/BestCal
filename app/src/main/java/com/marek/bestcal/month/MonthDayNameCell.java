package com.marek.bestcal.month;

import android.content.Context;
import android.graphics.Color;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * Created by Marek on 2019-06-15.
 */

public class MonthDayNameCell extends TextView {

    private final int HOLIDAY_DAY_COLOR = Color.RED;
    private final int WORK_DAY_COLOR = Color.BLACK;


    public MonthDayNameCell(Context context, String text, boolean isHoliday) {
        super(context);
        setPadding(0, 20, 0, 20);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        if (isHoliday) {
            setTextColor(HOLIDAY_DAY_COLOR);
        } else {
            setTextColor(WORK_DAY_COLOR);
        }
        setText(text);
    }

    /*
    public void setWidth(int width) {

        GridLayout.LayoutParams params = (GridLayout.LayoutParams) getLayoutParams();
        params.width = width;
        setLayoutParams(params);

    }
    */

}
