package com.marek.bestcal.main;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.marek.bestcal.R;
import com.marek.bestcal.config.calendarlist.CalendarListActivity;
import com.marek.bestcal.crash.CustomExceptionHandler;
import com.marek.bestcal.main.model.DayList;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link BestCalWidgetConfigureActivity BestCalWidgetConfigureActivity}
 */
public class BestCalWidget extends AppWidgetProvider implements BestCalThread.Callback{

    private static String REFRESH_BUTTON_ACTION = "RefreshButtonAction";
    private Context context;
    private BestCalThread bestCallThread;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        CustomExceptionHandler.assignHandler(context);
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = updateWidgetListView(context, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
        bestCallThread = BestCalThread.getInstance(this);
        bestCallThread.start();
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.best_cal_widget);
        Intent intent = new Intent(context, BestCalWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        remoteViews.setRemoteAdapter(appWidgetId, R.id.WidgetListView, intent);
        mapSettingsButton(context, remoteViews);
        mapRefreshButton(context, remoteViews);
        mapMonthCalendarButton(context, remoteViews);
        return remoteViews;
    }

    private void mapMonthCalendarButton(Context context, RemoteViews remoteViews) {
        remoteViews.setInt(R.id.WidgetButtonMonth, "setImageResource", R.drawable.month_calendar);
    }


    private void mapSettingsButton(Context context, RemoteViews remoteViews) {
        remoteViews.setInt(R.id.WidgetButtonSettings, "setImageResource", R.drawable.settings);
        Intent intent = new Intent(context, CalendarListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.WidgetButtonSettings, pendingIntent);
    }

    private void mapRefreshButton(Context context, RemoteViews remoteViews) {
        remoteViews.setInt(R.id.WidgetButtonRefresh, "setImageResource", R.drawable.refresh_120x120);
        Intent intent = new Intent(context, getClass());
        intent.setAction(REFRESH_BUTTON_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.WidgetButtonRefresh, pendingIntent);
    }


    private void onRefreshButtonPressed() {

        DayList d = DayList.getInstance(context);
        d.refreshList();
        Toast.makeText(context, "Events: "+d.getEventsTotal(), Toast.LENGTH_LONG).show();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.WidgetListView);

        /*

        Intent intent = new Intent(context, BestCalWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        context.sendBroadcast(intent);

        */

        /*
        Intent intent = new Intent();
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        context.sendBroadcast(intent);
        */
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            BestCalWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.i("MY_APP", "onEnabled");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled



    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (intent.getAction().equals(REFRESH_BUTTON_ACTION)) {
            onRefreshButtonPressed();
        }
        super.onReceive(context, intent);
    }

    @Override
    public void threadTask() {
        onRefreshButtonPressed();
    }
}

