package com.vsb.kol0482.mobileweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MultipleDays extends AppCompatActivity {
    String selectedDateFrom;
    String selectedDateTo;
    long daysBetween;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_days);

        String selectedUnit = getIntent().getStringExtra("SelectedUnit");
        TextView headerText = findViewById(R.id.title_multiple);
        headerText.setText(selectedUnit);

        selectedDateFrom = getIntent().getStringExtra("SelectedDateFrom");
        selectedDateTo = getIntent().getStringExtra("SelectedDateTo");
        daysBetween = getIntent().getLongExtra("DaysBetween", 0);


        Button datePickerButton1 = findViewById(R.id.date_picker_button_multiple3);
        datePickerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(datePickerButton1, true);
            }
        });

        Button datePickerButton2 = findViewById(R.id.date_picker_button_multiple4);
        datePickerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(datePickerButton2, false);
            }
        });

        datePickerButton1.setText(selectedDateFrom);
        datePickerButton2.setText(selectedDateTo);



        LineChart lineChart = findViewById(R.id.line_chart_multiple);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(getXValue("2023-04-20 02:11"), 4));
        entries.add(new Entry(getXValue("2023-04-21 03:12"), 2));
        entries.add(new Entry(getXValue("2023-04-22 04:16"), 6));
        entries.add(new Entry(getXValue("2023-04-23 15:18"), 8));

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLUE);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        Description description = new Description();
        description.setText("My chart");
        lineChart.setDescription(description);

// configure x-axis formatter
        final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm", Locale.getDefault());
        IAxisValueFormatter xAxisFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return sdf.format(new Date((long) value));
            }
        };
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new MultipleDays.MyXAxisValueFormatter(xAxisFormatter));

        lineChart.invalidate(); // refresh
    }

    private void showDatePickerDialog(Button datePickerButton, boolean from) {
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

    // helper method to convert date string to x value
    private long getXValue(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Date date = sdf.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void loadView(View view) {
        if(selectedDateFrom == null || selectedDateFrom.isEmpty() || selectedDateTo == null || selectedDateTo.isEmpty()) {
            Toast.makeText(MultipleDays.this, "Nastavte časové období", Toast.LENGTH_SHORT).show();
            return;
        }

        // vytvoření formatteru pro data ve formátu "dd.MM.yyyy"
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        // ošetření výjimky ParseException
        try {
            // převod řetězců na Date instance pomocí formatteru
            Date date1 = format.parse(selectedDateFrom);
            Date date2 = format.parse(selectedDateTo);

            long diffInMilliseconds = Math.abs(date2.getTime() - date1.getTime());
            //Rozdíl mezi daty
            daysBetween = TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);

            // porovnání dvou Date instancí pomocí metody compareTo()
            int compareResult = date1.compareTo(date2);
            if (compareResult > 0) {
                //Data jsou různá a data v selectedDateFrom jsou větší než v selectedDateTo
                Toast.makeText(MultipleDays.this, "Datum 'od' nemůže být větší než datum 'do'.", Toast.LENGTH_SHORT).show();
                return;
            }


        } catch (ParseException e) {
            // zachycení výjimky a vypsání chybové zprávy
            e.printStackTrace();
            Log.d("DATE","Chyba při parsování data.");
        }

    }

    public void loadMinGraph(View view) {
        Toast.makeText(MultipleDays.this, "Min graph", Toast.LENGTH_SHORT).show();

    }

    public void loadAvgGraph(View view) {
        Toast.makeText(MultipleDays.this, "Avg graph", Toast.LENGTH_SHORT).show();

    }

    public void loadMaxGraph(View view) {
        Toast.makeText(MultipleDays.this, "Max graph", Toast.LENGTH_SHORT).show();
    }

    class MyXAxisValueFormatter extends ValueFormatter {
        private final IAxisValueFormatter xAxisFormatter;

        MyXAxisValueFormatter(IAxisValueFormatter xAxisFormatter) {
            this.xAxisFormatter = xAxisFormatter;
        }

        @Override
        public String getFormattedValue(float value) {
            return xAxisFormatter.getFormattedValue(value, null);
        }
    }
}