package com.vsb.kol0482.mobileweatherapp;

import android.content.Context;
import android.content.SharedPreferences;

public class WidgetSettings {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static final String PREFS_NAME = "WidgetSettingsPrefs";
    public static final String CONNECTION_STRING = "CONNECTION_STRING";
    public static final String LEFT_UP = "LEFT_UP";
    public static final String RIGHT_UP = "RIGHT_UP";
    public static final String LEFT_DOWN = "LEFT_DOWN";
    public static final String RIGHT_DOWN = "RIGHT_DOWN";

    public static void initWidgetSettings(Context context) {
        if (sharedPreferences == null) {
            synchronized (WidgetSettings.class) {
                if (sharedPreferences == null) {
                    sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                }
            }
        }
    }

    public static String GetConnectionString() {
        return sharedPreferences.getString(CONNECTION_STRING, "");
    }

    public static void SetConnectionString(String input)
    {
        editor.putString(CONNECTION_STRING, input);
        editor.commit();
    }

    public static String GetLeftUp() {
        return sharedPreferences.getString(LEFT_UP, "");
    }

    public static void SetLeftUp(String input)
    {
        editor.putString(LEFT_UP, input);
        editor.commit();
    }

    public static String GetRightUp() {
        return sharedPreferences.getString(RIGHT_UP, "");
    }

    public static void SetRightUp(String input)
    {
        editor.putString(RIGHT_UP, input);
        editor.commit();
    }

    public static String GetLeftDown() {
        return sharedPreferences.getString(LEFT_DOWN, "");
    }

    public static void SetLeftDown(String input)
    {
        editor.putString(LEFT_DOWN, input);
        editor.commit();
    }

    public static String GetRightDown() {
        return sharedPreferences.getString(RIGHT_DOWN, "");
    }

    public static void SetRightDown(String input)
    {
        editor.putString(RIGHT_DOWN, input);
        editor.commit();
    }
}
