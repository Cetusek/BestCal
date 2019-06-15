package com.marek.bestcal.month;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marek.bestcal.R;

import org.w3c.dom.Text;


public class MonthCell extends LinearLayout {

    byte dayNo;

    TextView textViewDayNo;
    LinearLayout content;


    public MonthCell(Context context, byte dayNo) {
        super(context);
        this.dayNo = dayNo;
        inflate();
        mapGUI();
    }

    private void inflate() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        content = (LinearLayout) inflater.inflate(R.layout.month_cell, this);
    }

    private void mapGUI() {
        textViewDayNo = (TextView) content.findViewById(R.id.MonthCellDayNo);
        textViewDayNo.setText(Byte.toString(dayNo));
    }

    public void setDimensions(int width, int height) {
        GridLayout.LayoutParams params = (GridLayout.LayoutParams) getLayoutParams();
        params.width = width;
        params.height = height;
        setLayoutParams(params);
    }
}
