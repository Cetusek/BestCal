package com.marek.bestcal.main;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.marek.bestcal.R;
import com.marek.bestcal.config.calendarlist.CalendarListActivity;
import com.marek.bestcal.repository.Repo;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link BestCalWidgetConfigureActivity BestCalWidgetConfigureActivity}
 */
public class BestCalWidget extends AppWidgetProvider {

    private static String BUTTON_ACTION = "ButtonAction";
    private Context context;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.best_cal_widget);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = updateWidgetListView(context, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
            //updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.best_cal_widget);
        Intent intent = new Intent(context, BestCalWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        remoteViews.setRemoteAdapter(appWidgetId, R.id.WidgetListView, intent);
        mapButton(context, remoteViews);
        return remoteViews;
    }


    private void mapButton(Context context, RemoteViews remoteViews) {
        Intent intent = new Intent(context, CalendarListActivity.class);
        intent.setAction(BUTTON_ACTION);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.WidgetButton, pendingIntent);
    }

    private void mapButton2(Context context, RemoteViews remoteViews) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(BUTTON_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.WidgetButton, pendingIntent);
    }

    private void onButtonPressed() {
        Intent intent = new Intent(context, CalendarListActivity.class);

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
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (intent.getAction().equals(BUTTON_ACTION)) {
            onButtonPressed();
        }
        super.onReceive(context, intent);
    }
}

