package com.vsb.kol0482.mobileweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

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

public class MultipleDays extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_days);
        String selectedUnit = getIntent().getStringExtra("SelectedUnit");
        TextView headerText = findViewById(R.id.title_multiple);
        headerText.setText(selectedUnit);

        Button datePickerButton1 = findViewById(R.id.date_picker_button_multiple3);
        datePickerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(datePickerButton1);
            }
        });

        Button datePickerButton2 = findViewById(R.id.date_picker_button_multiple4);
        datePickerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(datePickerButton2);
            }
        });

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

    private void showDatePickerDialog(Button datePickerButton) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = dayOfMonth + "." + (monthOfYear+1) + "." + year;
                datePickerButton.setText(selectedDate);
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