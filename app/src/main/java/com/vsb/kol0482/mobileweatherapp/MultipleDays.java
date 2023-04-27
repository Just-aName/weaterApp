package com.vsb.kol0482.mobileweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

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
    String selectedUnit;
    long daysBetween;
    int valueType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_days);

        selectedUnit = getIntent().getStringExtra("SelectedUnit");
        TextView headerText = findViewById(R.id.title_multiple);
        headerText.setText(selectedUnit);

        selectedDateFrom = getIntent().getStringExtra("SelectedDateFrom");
        selectedDateTo = getIntent().getStringExtra("SelectedDateTo");
        daysBetween = getIntent().getLongExtra("DaysBetween", 0);

        LinearLayout avgLabel = findViewById(R.id.label_avg_value);
        avgLabel.setBackgroundResource(R.drawable.border_selected);

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

        getData(this, valueType,new WeatherDataCallback() {
            @Override
            public void onSuccess(JSONArray data)
            {
                createChart(data);
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(MultipleDays.this, "Problém s načtením dat.", Toast.LENGTH_LONG).show();
                Log.d("GRAPH", "Graph data load ERROR!");
            }
        });
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
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
            if (compareResult >= 0) {
                //Data jsou různá a data v selectedDateFrom jsou větší než v selectedDateTo
                Toast.makeText(MultipleDays.this, "Datum 'od' nemůže být větší, nebo rovno datum 'do'.", Toast.LENGTH_SHORT).show();
                return;
            }

            getData(this, valueType,new WeatherDataCallback() {
                @Override
                public void onSuccess(JSONArray data)
                {
                    createChart(data);
                }

                @Override
                public void onError(VolleyError error) {
                    Toast.makeText(MultipleDays.this, "Problém s načtením dat.", Toast.LENGTH_LONG).show();
                    Log.d("GRAPH", "Graph data load ERROR!");
                }
            });

        } catch (ParseException e) {
            // zachycení výjimky a vypsání chybové zprávy
            e.printStackTrace();
            Log.d("DATE","Chyba při parsování data.");
        }

    }

    public void loadMinGraph(View view) {
        if(valueType == 1)
        {
            LinearLayout maxLabel = findViewById(R.id.label_max_value);
            maxLabel.setBackgroundResource(R.drawable.border_black);
        }
        else if (valueType == 0)
        {
            LinearLayout avgLabel = findViewById(R.id.label_avg_value);
            avgLabel.setBackgroundResource(R.drawable.border_black);
        }

        valueType = 2;
        LinearLayout minLabel = findViewById(R.id.label_min_value);
        minLabel.setBackgroundResource(R.drawable.border_selected);
    }

    public void loadAvgGraph(View view) {
        if(valueType == 1)
        {
            LinearLayout maxLabel = findViewById(R.id.label_max_value);
            maxLabel.setBackgroundResource(R.drawable.border_black);
        }
        else if (valueType == 2)
        {
            LinearLayout minLabel = findViewById(R.id.label_min_value);
            minLabel.setBackgroundResource(R.drawable.border_black);
        }
        valueType = 0;
        LinearLayout avgLabel = findViewById(R.id.label_avg_value);
        avgLabel.setBackgroundResource(R.drawable.border_selected);
    }

    public void loadMaxGraph(View view) {
        if(valueType == 0)
        {
            LinearLayout avgLabel = findViewById(R.id.label_avg_value);
            avgLabel.setBackgroundResource(R.drawable.border_black);
        }
        else if (valueType == 2)
        {
            LinearLayout minLabel = findViewById(R.id.label_min_value);
            minLabel.setBackgroundResource(R.drawable.border_black);
        }

        valueType = 1;
        LinearLayout maxLabel = findViewById(R.id.label_max_value);
        maxLabel.setBackgroundResource(R.drawable.border_selected);
    }

    private void getData(Context context, int valType, final WeatherDataCallback callback)
    {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        // převod řetězce na datum pomocí inputFormat
        String dateStringFrom;
        String dateStringTo;
        try {
            Date dateFrom = inputFormat.parse(selectedDateFrom);
            dateStringFrom = outputFormat.format(dateFrom);
            Date dateTo = inputFormat.parse(selectedDateTo);
            dateStringTo = outputFormat.format(dateTo);

        } catch (ParseException e) {
            Toast.makeText(MultipleDays.this, "Problém s načtením dat.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }


        String url;
        if(valType == 1)
            url = WidgetSettings.GetConnectionString() + "/api/Max/GetMaxPerDay/" + dateStringFrom + "/" + dateStringTo;
        else if (valType == 2)
            url = WidgetSettings.GetConnectionString() + "/api/Min/GetMinPerDay/" + dateStringFrom + "/" + dateStringTo;
        else
            url = WidgetSettings.GetConnectionString() + "/api/Avg/GetAvgPerDay/" + dateStringFrom + "/" + dateStringTo;

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onSuccess(response);

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

    private void createChart(JSONArray data)
    {
        LineChart lineChart = findViewById(R.id.line_chart_multiple);
        if(data.length() == 0)
            Toast.makeText(MultipleDays.this, "Pro tyto deny nejsou naměřeny žádné hodnoty.", Toast.LENGTH_LONG).show();

        ArrayList<Entry> entries = new ArrayList<>();
        float min = Float.POSITIVE_INFINITY;
        float max = Float.NEGATIVE_INFINITY;
        float avg = 0;
        try
        {
            for(int i=0;i<data.length();i++){
                JSONObject jData = data.getJSONObject(i);
                String time = jData.getString("time");
                String value = jData.getString(ApiDataBinder.GetApiVersion(selectedUnit));
                float val = Float.parseFloat(value);
                if(val < min)
                    min = val;
                if(val > max)
                    max = val;
                avg = avg + val;
                entries.add(new Entry(getXValue(time), val));
            }
            if(data.length() != 0)
                avg = Math.round(avg / data.length() * 10) / 10f;
        }catch (Exception e)
        {
            Toast.makeText(MultipleDays.this, "Problém s parsovanim dat.", Toast.LENGTH_LONG).show();
            return;
        }

        TextView field1 = findViewById(R.id.field1_value_multiple);
        TextView field2 = findViewById(R.id.field2_value_multiple);
        TextView field3 = findViewById(R.id.field3_value_multiple);

        WidgetOptions tmpUnit = WidgetOptions.fromValue(selectedUnit);
        if(tmpUnit != null) {
            String finStr1 = String.format(Locale.ENGLISH, "%.1f", min) + " " + ApiDataBinder.GetUnitBaseOnEnum(tmpUnit);
            String finStr2 = String.format(Locale.ENGLISH, "%.1f", avg) + " " + ApiDataBinder.GetUnitBaseOnEnum(tmpUnit);
            String finStr3 = String.format(Locale.ENGLISH, "%.1f", max) + " " + ApiDataBinder.GetUnitBaseOnEnum(tmpUnit);
            field1.setText(finStr1);
            field2.setText(finStr2);
            field3.setText(finStr3);
        }
        else
        {
            field1.setText(String.format(Locale.ENGLISH, "%.1f", min));
            field2.setText(String.format(Locale.ENGLISH, "%.1f", avg));
            field3.setText(String.format(Locale.ENGLISH, "%.1f", max));
        }
        LineDataSet dataSet = new LineDataSet(entries, selectedUnit); // add entries to dataset
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLUE);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);


        if(tmpUnit != null) {
            Description description = new Description();
            String unit = ApiDataBinder.GetUnitBaseOnEnum(tmpUnit);
            description.setText(unit);
            lineChart.setDescription(description);
        }

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
        xAxis.setLabelRotationAngle(45f);

        lineChart.setExtraOffsets(0f,0f,0f,30f);
        lineChart.invalidate(); // refresh
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