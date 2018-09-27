package com.marek.bestcal.main;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.marek.bestcal.R;
import com.marek.bestcal.main.model.DayList;
import com.marek.bestcal.main.model.DayListItem;

import java.util.ArrayList;

public class BestCalListProvider implements RemoteViewsService.RemoteViewsFactory {

    private DayList list = new DayList();
    private Context context;
    private int appWidgedId;

    public BestCalListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgedId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_day);
        DayListItem item = list.getItem(position);
        remoteViews.setTextViewText(R.id.WidgetDayNumber, item.text);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
