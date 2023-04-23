package com.vsb.kol0482.mobileweatherapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class WeatherWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);

            // Set up the first tile
            Intent intent1 = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, intent1, 0);
            views.setOnClickPendingIntent(R.id.tile1, pendingIntent1);

            // Set up the second tile
            Intent intent2 = new Intent(context, GraphActivity.class);
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, intent2, 0);
            views.setOnClickPendingIntent(R.id.tile2, pendingIntent2);

            // Set up the third tile
            Intent intent3 = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent3 = PendingIntent.getActivity(context, 0, intent3, 0);
            views.setOnClickPendingIntent(R.id.tile3, pendingIntent3);

            // Set up the fourth tile
            Intent intent4 = new Intent(context, GraphActivity.class);
            PendingIntent pendingIntent4 = PendingIntent.getActivity(context, 0, intent4, 0);
            views.setOnClickPendingIntent(R.id.tile4, pendingIntent4);

            // Update the text for each tile
            views.setTextViewText(R.id.tile1_text, WidgetSettings.value0.getValue());
            views.setTextViewText(R.id.tile2_text, WidgetSettings.value1.getValue());
            views.setTextViewText(R.id.tile3_text, WidgetSettings.value2.getValue());
            views.setTextViewText(R.id.tile4_text, WidgetSettings.value3.getValue());

            if(WidgetSettings.value1.getValue() == WidgetOptions.NONE.getValue()) {
                views.setViewVisibility(R.id.tile2, View.GONE);
            }
            else{
                views.setViewVisibility(R.id.tile2, View.VISIBLE);
            }
            appWidgetManager.updateAppWidget(appWidgetId, views);

            AlarmHandler alarmHandler = new AlarmHandler(context);
            alarmHandler.cancelAlarmManager();
            alarmHandler.setAlarmManager();

            Log.d("WIDGET", "Widget updated!");
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        //stop updating the widget
        AlarmHandler alarmHandler = new AlarmHandler(context);
        alarmHandler.cancelAlarmManager();

        Log.d("WIDGET", "Widget removed!");
    }

}