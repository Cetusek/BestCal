package com.marek.bestcal.anniversary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AnniversaryTypeSpinnerAdapter extends ArrayAdapter<AnniversaryType> {


    public AnniversaryTypeSpinnerAdapter(Context context) {
        super(context, android.R.layout.simple_spinner_dropdown_item, AnniversaryType.values());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(getItem(position).getDescription(view.getContext()));
        return view;
    }



}
