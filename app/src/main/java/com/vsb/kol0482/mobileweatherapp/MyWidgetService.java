package com.vsb.kol0482.mobileweatherapp;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


public class MyWidgetService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //wake the device
        WakeLocker.acquire(context);

        //force widget update
        Intent widgetIntent = new Intent(context, WeatherWidget.class);
        widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, WeatherWidget.class));
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(widgetIntent);

        Log.d("WIDGET", "Widget set to update!");

        //go back to sleep
        WakeLocker.release();
    }
}