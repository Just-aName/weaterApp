package com.vsb.kol0482.mobileweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Map;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        WidgetOptions[] options = WidgetOptions.values();
        String[] stringOptions = new String[options.length];
        for (int i = 0; i < options.length; i++) {
            stringOptions[i] = options[i].getValue();
        }

        Spinner spinner0 = findViewById(R.id.menu_spinner0);
        Spinner spinner1 = findViewById(R.id.menu_spinner1);
        Spinner spinner2 = findViewById(R.id.menu_spinner2);
        Spinner spinner3 = findViewById(R.id.menu_spinner3);

        // Vytvoření ArrayAdapteru s výchozí hodnotou a seznamem možností
        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringOptions);
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringOptions);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringOptions);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringOptions);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Nastavení adaptéru pro Spinner
        spinner0.setAdapter(adapter0);
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);

        // Nastavení výchozí hodnoty pro Spinner
        int selectedPosition0 = Arrays.asList(stringOptions).indexOf(WidgetSettings.GetLeftUp());
        int selectedPosition1 = Arrays.asList(stringOptions).indexOf(WidgetSettings.GetRightUp());
        int selectedPosition2 = Arrays.asList(stringOptions).indexOf(WidgetSettings.GetLeftDown());
        int selectedPosition3 = Arrays.asList(stringOptions).indexOf(WidgetSettings.GetRightDown());

        if (selectedPosition0 == -1)
            spinner0.setSelection(0);
        else
            spinner0.setSelection(selectedPosition0);

        if (selectedPosition1 == -1)
            spinner1.setSelection(0);
        else
            spinner1.setSelection(selectedPosition1);

        if (selectedPosition2 == -1)
            spinner2.setSelection(0);
        else
            spinner2.setSelection(selectedPosition2);

        if (selectedPosition3 == -1)
            spinner3.setSelection(0);
        else
            spinner3.setSelection(selectedPosition3);
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

        WidgetSettings.SetLeftUp(leftUp);
        WidgetSettings.SetRightUp(rightUp);
        WidgetSettings.SetLeftDown(leftDown);
        WidgetSettings.SetRightDown(rightDown);

        finish();
        Toast.makeText(this, "Aktivita byla ukončena.", Toast.LENGTH_SHORT).show(); // vypsání Toast hlášky
    }
}