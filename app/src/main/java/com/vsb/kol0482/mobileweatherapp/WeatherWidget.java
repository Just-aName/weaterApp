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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
interface WeatherInfoCallback {
    void onSuccess(JSONObject data);
    void onError(VolleyError error);
}

public class WeatherWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);

            getWeatherInfo(context, new WeatherInfoCallback() {
                @Override
                public void onSuccess(JSONObject data) {
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
                    String leftUp = WidgetSettings.GetLeftUp();
                    String rightUp = WidgetSettings.GetRightUp();
                    String leftDown = WidgetSettings.GetLeftDown();
                    String rightDown = WidgetSettings.GetRightDown();
                    try {
                    if(leftUp.equals(WidgetOptions.NONE.getValue()) || leftUp.equals(""))
                    {
                        views.setViewVisibility(R.id.tile1, View.GONE);
                    }
                    else {
                        String apiVer = ApiDataBinder.GetApiVersion(leftUp);
                        String val = data.getString(apiVer);
                        String unit = ApiDataBinder.GetUnitBaseOnEnum(ApiDataBinder.GetAppEnumVersion(apiVer));
                        views.setViewVisibility(R.id.tile1, View.VISIBLE);
                        views.setTextViewText(R.id.tile1_text, val + " " + unit);
                        views.setTextViewText(R.id.tile1_title, leftUp);
                    }

                    if(rightUp.equals(WidgetOptions.NONE.getValue()) || rightUp.equals(""))
                    {
                        views.setViewVisibility(R.id.tile2, View.GONE);
                    }
                    else {
                        String apiVer = ApiDataBinder.GetApiVersion(rightUp);
                        String val = data.getString(apiVer);
                        String unit = ApiDataBinder.GetUnitBaseOnEnum(ApiDataBinder.GetAppEnumVersion(apiVer));
                        views.setViewVisibility(R.id.tile2, View.VISIBLE);
                        views.setTextViewText(R.id.tile2_text, val + " " + unit);
                        views.setTextViewText(R.id.tile2_title, rightUp);
                    }

                    if(leftDown.equals(WidgetOptions.NONE.getValue()) || leftDown.equals(""))
                    {
                        views.setViewVisibility(R.id.tile3, View.GONE);
                    }
                    else {
                        String apiVer = ApiDataBinder.GetApiVersion(leftDown);
                        String val = data.getString(apiVer);
                        String unit = ApiDataBinder.GetUnitBaseOnEnum(ApiDataBinder.GetAppEnumVersion(apiVer));
                        views.setViewVisibility(R.id.tile3, View.VISIBLE);
                        views.setTextViewText(R.id.tile3_text, val + " " + unit);
                        views.setTextViewText(R.id.tile3_title, leftDown);
                    }

                    if(rightDown.equals(WidgetOptions.NONE.getValue()) || rightDown.equals(""))
                    {
                        views.setViewVisibility(R.id.tile4, View.GONE);
                    }
                    else {
                        String apiVer = ApiDataBinder.GetApiVersion(rightDown);
                        String val = data.getString(apiVer);
                        String unit = ApiDataBinder.GetUnitBaseOnEnum(ApiDataBinder.GetAppEnumVersion(apiVer));
                        views.setViewVisibility(R.id.tile4, View.VISIBLE);
                        views.setTextViewText(R.id.tile4_text, val + " " + unit);
                        views.setTextViewText(R.id.tile4_title, rightDown);
                    }

                    String timeString = data.getString("time");
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM. HH:mm");
                    try {
                            Date date = inputFormat.parse(timeString);
                            String formattedDateTime = outputFormat.format(date);
                            views.setTextViewText(R.id.header_text2, formattedDateTime);
                    } catch (ParseException e) {
                        Log.d("WIDGET", "Widget time parse load ERROR!");
                        e.printStackTrace();
                    }


                    appWidgetManager.updateAppWidget(appWidgetId, views);
                    } catch (JSONException e) {
                        Log.d("WIDGET", "Widget data parse load ERROR!");
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    // Handle the error
                    // ...
                    Log.d("WIDGET", "Widget data load ERROR!");
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
                        views.setTextViewText(R.id.tile1_text, "DATA ERROR");
                        views.setTextViewText(R.id.tile1_title, WidgetSettings.GetLeftUp());
                    }

                    if(WidgetSettings.GetRightUp().equals(WidgetOptions.NONE.getValue()))
                    {
                        views.setViewVisibility(R.id.tile2, View.GONE);
                    }
                    else {
                        views.setViewVisibility(R.id.tile2, View.VISIBLE);
                        views.setTextViewText(R.id.tile2_text, "DATA ERROR");
                        views.setTextViewText(R.id.tile2_title, WidgetSettings.GetRightUp());
                    }

                    if(WidgetSettings.GetLeftDown().equals(WidgetOptions.NONE.getValue()))
                    {
                        views.setViewVisibility(R.id.tile3, View.GONE);
                    }
                    else {
                        views.setViewVisibility(R.id.tile3, View.VISIBLE);
                        views.setTextViewText(R.id.tile3_text, "DATA ERROR");
                        views.setTextViewText(R.id.tile3_title, WidgetSettings.GetLeftDown());
                    }

                    if(WidgetSettings.GetRightDown().equals(WidgetOptions.NONE.getValue()))
                    {
                        views.setViewVisibility(R.id.tile4, View.GONE);
                    }
                    else {
                        views.setViewVisibility(R.id.tile4, View.VISIBLE);
                        views.setTextViewText(R.id.tile4_text, "DATA ERROR");
                        views.setTextViewText(R.id.tile4_title, WidgetSettings.GetRightDown());
                    }
                }
            });

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


    private void getWeatherInfo(Context context, final WeatherInfoCallback callback)
    {
        String url = "http://10.0.2.2:8080/api/RawData/GetCurrent";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject data = response.getJSONObject(0);
                            callback.onSuccess(data);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("WIDGET", "Widget data load ERROR!");
                callback.onError(error);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}