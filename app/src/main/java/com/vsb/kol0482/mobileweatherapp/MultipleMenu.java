package com.vsb.kol0482.mobileweatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MultipleMenu extends AppCompatActivity {
    private List<String> menuItems = new ArrayList<>(); // list pro položky menu
    private GridLayout gridLayout; // GridLayout pro dlaždice
    private String selectedDateFrom;
    private String selectedDateTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_menu);

        //naplní menu dlaždicemi
        WidgetOptions[] options = WidgetOptions.values();
        for (int i = 0; i < options.length; i++) {
            if(options[i].getValue() != WidgetOptions.NONE.getValue())
                menuItems.add(options[i].getValue());
        }

        // nalezení GridLayoutu v layoutu
        gridLayout = findViewById(R.id.grid_layout_multiple);

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
                    if(selectedDateFrom == null || selectedDateFrom.isEmpty() || selectedDateTo == null || selectedDateTo.isEmpty()) {
                        Toast.makeText(MultipleMenu.this, "Nastavte časové období", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    long diffInDays = 0;
                    // vytvoření formatteru pro data ve formátu "dd.MM.yyyy"
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    // ošetření výjimky ParseException
                    try {
                        // převod řetězců na Date instance pomocí formatteru
                        Date date1 = format.parse(selectedDateFrom);
                        Date date2 = format.parse(selectedDateTo);

                        long diffInMilliseconds = Math.abs(date2.getTime() - date1.getTime());
                        //Rozdíl mezi daty
                        diffInDays = TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);

                        // porovnání dvou Date instancí pomocí metody compareTo()
                        int compareResult = date1.compareTo(date2);
                        if (compareResult > 0) {
                            //Data jsou různá a data v selectedDateFrom jsou větší než v selectedDateTo
                            Toast.makeText(MultipleMenu.this, "Datum 'od' nemůže být větší než datum 'do'.", Toast.LENGTH_SHORT).show();
                            return;
                        }


                    } catch (ParseException e) {
                        // zachycení výjimky a vypsání chybové zprávy
                        e.printStackTrace();
                        Log.d("DATE","Chyba při parsování data.");
                    }


                    Intent intent = new Intent(MultipleMenu.this, MultipleDays.class);
                    intent.putExtra("SelectedUnit", menuText);
                    intent.putExtra("SelectedDateFrom", selectedDateFrom);
                    intent.putExtra("SelectedDateTo", selectedDateTo);
                    intent.putExtra("DaysBetween", diffInDays);
                    startActivity(intent);
                }
            });

            // přidáno: přidání dlaždice do GridLayoutu
            gridLayout.addView(cardView);
        }

        Button datePickerButton1 = findViewById(R.id.date_picker_button_multiple1);
        datePickerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(datePickerButton1, true);
            }
        });

        Button datePickerButton2 = findViewById(R.id.date_picker_button_multiple2);
        datePickerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(datePickerButton2, false);
            }
        });
    }

    private void showDatePickerDialog(Button datePickerButton, Boolean from) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(from) {
                    selectedDateFrom = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                    datePickerButton.setText(selectedDateFrom);
                }
                else{
                    selectedDateTo = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                    datePickerButton.setText(selectedDateTo);
                }
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void exit(View v){
        finish();
    }
}