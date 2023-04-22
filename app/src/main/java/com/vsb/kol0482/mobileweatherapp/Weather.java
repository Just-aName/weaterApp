package com.vsb.kol0482.mobileweatherapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link WeatherConfigureActivity WeatherConfigureActivity}
 */
public class Weather extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather);

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
            views.setTextViewText(R.id.tile1_text, "Temperature: 22°C");
            views.setTextViewText(R.id.tile2_text, "Humidity: 45%");
            views.setTextViewText(R.id.tile3_text, "Wind Speed: 12 km/h");
            views.setTextViewText(R.id.tile4_text, "Precipitation: 25%");

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            WeatherConfigureActivity.deleteTitlePref(context, appWidgetId);
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
}