package com.marek.bestcal.main.model;


import java.util.ArrayList;

public class DayList {

    private ArrayList<DayListItem> list;

    public DayList() {
        list = new ArrayList<>();
        populateList();
    }

    private void populateList() {
        for (int i = 0; i < 10; i++) {
            DayListItem item = new DayListItem();
            item.text = String.valueOf(i);
            list.add(item);
        }
    }

    public int size() {
        return list.size();
    }

    public DayListItem getItem(int position) {
        return list.get(position);
    }
}
