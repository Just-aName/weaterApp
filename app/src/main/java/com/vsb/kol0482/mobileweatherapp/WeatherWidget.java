package com.vsb.kol0482.mobileweatherapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class WeatherWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);

            Intent settingIntent = new Intent(context, Settings.class);
            PendingIntent pendingIntent5 = PendingIntent.getActivity(context, 5, settingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.header_layout, pendingIntent5);

            // Set up the first tile
            Intent intent1 = new Intent(context, GraphActivity.class);
            intent1.putExtra("SelectedUnit", WidgetSettings.GetLeftUp());
            PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.tile1, pendingIntent1);

            // Set up the second tile
            Intent intent2 = new Intent(context, GraphActivity.class);
            intent2.putExtra("SelectedUnit", WidgetSettings.GetRightUp());
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 2, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.tile2, pendingIntent2);

            // Set up the third tile
            Intent intent3 = new Intent(context, GraphActivity.class);
            intent3.putExtra("SelectedUnit", WidgetSettings.GetLeftDown());
            PendingIntent pendingIntent3 = PendingIntent.getActivity(context, 3, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.tile3, pendingIntent3);

            // Set up the fourth tile
            Intent intent4 = new Intent(context, GraphActivity.class);
            intent4.putExtra("SelectedUnit", WidgetSettings.GetRightDown());
            PendingIntent pendingIntent4 = PendingIntent.getActivity(context, 4, intent4, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.tile4, pendingIntent4);

            // Update the text for each tile
            if(WidgetSettings.GetLeftUp().equals(WidgetOptions.NONE.getValue()))
            {
                views.setViewVisibility(R.id.tile1, View.GONE);
            }
            else {
                views.setViewVisibility(R.id.tile1, View.VISIBLE);
                views.setTextViewText(R.id.tile1_text, "a");
                views.setTextViewText(R.id.tile1_title, WidgetSettings.GetLeftUp());
            }

            if(WidgetSettings.GetRightUp().equals(WidgetOptions.NONE.getValue()))
            {
                views.setViewVisibility(R.id.tile2, View.GONE);
            }
            else {
                views.setViewVisibility(R.id.tile2, View.VISIBLE);
                views.setTextViewText(R.id.tile2_text, "b");
                views.setTextViewText(R.id.tile2_title, WidgetSettings.GetRightUp());
            }

            if(WidgetSettings.GetLeftDown().equals(WidgetOptions.NONE.getValue()))
            {
                views.setViewVisibility(R.id.tile3, View.GONE);
            }
            else {
                views.setViewVisibility(R.id.tile3, View.VISIBLE);
                views.setTextViewText(R.id.tile3_text, "c");
                views.setTextViewText(R.id.tile3_title, WidgetSettings.GetLeftDown());
            }

            if(WidgetSettings.GetRightDown().equals(WidgetOptions.NONE.getValue()))
            {
                views.setViewVisibility(R.id.tile4, View.GONE);
            }
            else {
                views.setViewVisibility(R.id.tile4, View.VISIBLE);
                views.setTextViewText(R.id.tile4_text, "d");
                views.setTextViewText(R.id.tile4_title, WidgetSettings.GetRightDown());
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
        WidgetSettings.initWidgetSettings(context);
    }

    @Override
    public void onDisabled(Context context) {
        //stop updating the widget
        AlarmHandler alarmHandler = new AlarmHandler(context);
        alarmHandler.cancelAlarmManager();

        Log.d("WIDGET", "Widget removed!");
    }

}