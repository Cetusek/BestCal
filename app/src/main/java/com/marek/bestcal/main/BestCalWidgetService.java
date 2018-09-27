package com.marek.bestcal.main;


import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class BestCalWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.i("MY_APP", "BestCalWidgetService.RemoteViewsFactory");
        return new BestCalListProvider(this.getApplicationContext(), intent);
    }
}
