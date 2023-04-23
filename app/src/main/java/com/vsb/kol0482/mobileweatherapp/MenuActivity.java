package com.vsb.kol0482.mobileweatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private List<String> menuItems = new ArrayList<>(); // list pro položky menu
    private GridLayout gridLayout; // GridLayout pro dlaždice

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //naplní menu dlaždicemi
        WidgetOptions[] options = WidgetOptions.values();
        for (int i = 0; i < options.length; i++) {
            if(options[i].getValue() != WidgetOptions.NONE.getValue())
                menuItems.add(options[i].getValue());
        }

        // nalezení GridLayoutu v layoutu
        gridLayout = findViewById(R.id.grid_layout);

        // výpočet počtu řádků
        int numRows = (int) Math.ceil((double) menuItems.size() / 2);


        // nastavení počtu řádků a sloupců pro GridLayout
        gridLayout.setColumnCount(2);
        gridLayout.setRowCount(numRows);

        // iterace přes položky menu a vytvoření dlaždic
        for (int i = 0; i < menuItems.size(); i++) {
            // vytvoření nové dlaždice
            CardView cardView = new CardView(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, 1f), // horizontal spec: column, width ratio
                    GridLayout.spec(GridLayout.UNDEFINED, 1f)  // vertical spec: row, height ratio
            );
            int size = getResources().getDimensionPixelSize(R.dimen.card_size); // velikost ctverce v pixelech
            params.width = size;
            params.height = size;
            params.setMargins(16, 16, 16, 16);
            cardView.setLayoutParams(params);

            // nastavení pozadí a paddingu dlaždice
            cardView.setCardBackgroundColor(Color.WHITE);
            cardView.setRadius(16);
            cardView.setContentPadding(16, 16, 16, 16);

            // vytvoření TextView pro text v dlaždici
            String menuText = menuItems.get(i);
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setText(menuText);
            textView.setTextSize(18);
            textView.setTextColor(Color.BLACK);

            // přidání TextView do dlaždice
            cardView.addView(textView);

            // přidání OnClickListeneru pro přesměrování na GraphActivity
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuActivity.this, GraphActivity.class);
                    intent.putExtra("SelectedUnit", menuText);
                    startActivity(intent);
                }
            });

            // přidáno: přidání dlaždice do GridLayoutu
            gridLayout.addView(cardView);
        }

        Button datePickerButton = findViewById(R.id.date_picker_button);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // zpracování vybraného data
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void exit(View v){
        finish();
    }
}