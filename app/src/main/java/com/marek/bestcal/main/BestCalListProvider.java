package com.marek.bestcal.main;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.marek.bestcal.R;
import com.marek.bestcal.config.Configuration;
import com.marek.bestcal.main.model.DayList;
import com.marek.bestcal.main.model.DayListItem;

public class BestCalListProvider implements RemoteViewsService.RemoteViewsFactory {

    private DayList list; // = new DayList(null);
    private Context context;
    private int appWidgedId;
    private int holidayColor;

    public BestCalListProvider(Context context, Intent intent) {
        this.context = context;
        list = DayList.getInstance(context);
        appWidgedId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        setValuesFromConfiguration();
    }

    private void setValuesFromConfiguration() {
        Configuration configuration = Configuration.getInstance();
        holidayColor = configuration.getHolidayColor();
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
        remoteViews.setTextViewText(R.id.WidgetDayDayOfMonth, item.dayOfMonth);
        remoteViews.setTextViewText(R.id.WidgetDayNameOfDay, item.dayOfWeek);
        remoteViews.setTextViewText(R.id.WidgetDayNameOfMonth, item.month);
        if (item.isHoliday) {
            remoteViews.setTextColor(R.id.WidgetDayDayOfMonth, holidayColor);
            remoteViews.setTextColor(R.id.WidgetDayNameOfDay, holidayColor);
            remoteViews.setTextColor(R.id.WidgetDayNameOfMonth, holidayColor);
        }
        else {
            remoteViews.setTextColor(R.id.WidgetDayDayOfMonth, Color.BLACK);
            remoteViews.setTextColor(R.id.WidgetDayNameOfDay, Color.BLACK);
            remoteViews.setTextColor(R.id.WidgetDayNameOfMonth, Color.BLACK);
        }
        remoteViews.setTextViewText(R.id.WidgetDayDayContent, Html.fromHtml(item.getHTMLDayContent()));
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

    public void refreshList() {

    }

}
