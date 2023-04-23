package com.vsb.kol0482.mobileweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void exit(View v){
        finish();
    }

    public void save(View v){
        Spinner spinner0 = findViewById(R.id.menu_spinner0);
        String leftUp = spinner0.getSelectedItem().toString();

        Spinner spinner1 = findViewById(R.id.menu_spinner1);
        String rightUp = spinner1.getSelectedItem().toString();

        Spinner spinner2 = findViewById(R.id.menu_spinner2);
        String leftDown = spinner2.getSelectedItem().toString();

        Spinner spinner3 = findViewById(R.id.menu_spinner3);
        String rightDown = spinner3.getSelectedItem().toString();

        switch (rightUp) {
            case "Option 1":
                WidgetSettings.value1 = WidgetOptions.NONE;
                break;
            case "Option 2":
                WidgetSettings.value1 = WidgetOptions.TEPLOTA;
                break;
            case "Option 3":
                WidgetSettings.value1 = WidgetOptions.TLAK;
                break;
            case "Option 4":
                WidgetSettings.value1 = WidgetOptions.RYCHLOSTVETRU;
                break;
            case "Option 5":
                WidgetSettings.value1 = WidgetOptions.VLHKOST;
                break;
            default:
                WidgetSettings.value1 = WidgetOptions.NONE;
                break;
        }

        finish();
        Toast.makeText(this, "Aktivita byla ukončena.", Toast.LENGTH_SHORT).show(); // vypsání Toast hlášky
    }
}