package com.marek.bestcal.anniversary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CalendarSpinnerAdapter extends ArrayAdapter<CalendarSpinnerPosition> {



    public CalendarSpinnerAdapter(Context context, CalendarSpinnerPosition[] positions) {
        super(context, android.R.layout.simple_spinner_dropdown_item, positions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getItem(position).label);
        return view;
    }



}
