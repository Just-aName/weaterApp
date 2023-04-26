package com.vsb.kol0482.mobileweatherapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

interface WeatherDataCallback {
    void onSuccess(JSONArray data);
    void onError(VolleyError error);
}

public class GraphActivity extends AppCompatActivity {
    private String selectedDate;
    private String selectedUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        selectedUnit = getIntent().getStringExtra("SelectedUnit");
        TextView headerText = findViewById(R.id.title);
        headerText.setText(selectedUnit);

        Button datePickerButton = findViewById(R.id.date_picker_button2);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        selectedDate = getIntent().getStringExtra("SelectedDate");
        if(selectedDate == null || selectedDate.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            selectedDate = sdf.format(new Date());
        }
        datePickerButton.setText(selectedDate);

        getData(this, new WeatherDataCallback() {
            @Override
            public void onSuccess(JSONArray data)
            {
                createChart(data);
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(GraphActivity.this, "Problém s načtením dat.", Toast.LENGTH_LONG).show();
                Log.d("GRAPH", "Graph data load ERROR!");
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
                selectedDate = dayOfMonth + "." + (monthOfYear+1) + "." + year;
                Button datePickerButton = findViewById(R.id.date_picker_button2);
                datePickerButton.setText(selectedDate);
                getData(GraphActivity.this, new WeatherDataCallback() {
                    @Override
                    public void onSuccess(JSONArray data) {
                        createChart(data);
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(GraphActivity.this, "Problém s načtením dat.", Toast.LENGTH_LONG).show();
                        Log.d("GRAPH", "Graph data load ERROR!");
                    }
                });
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

    private void getData(Context context, final WeatherDataCallback callback)
    {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        // převod řetězce na datum pomocí inputFormat
        String dateString;
        try {
            Date date = inputFormat.parse(selectedDate);
            dateString = outputFormat.format(date);
        } catch (ParseException e) {
            Toast.makeText(GraphActivity.this, "Problém s načtením dat.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }


        String url = WidgetSettings.GetConnectionString() + "/api/Avg/GetAvgPerHourForDate/" + dateString;
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
        LineChart lineChart = findViewById(R.id.line_chart);
        if(data.length() == 0)
            Toast.makeText(GraphActivity.this, "Pro tento den nejsou naměřeny žádné hodnoty.", Toast.LENGTH_LONG).show();

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
            Toast.makeText(GraphActivity.this, "Problém s parsovanim dat.", Toast.LENGTH_LONG).show();
            return;
        }

        TextView field1 = findViewById(R.id.field1_value);
        TextView field2 = findViewById(R.id.field2_value);
        TextView field3 = findViewById(R.id.field3_value);

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
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        IAxisValueFormatter xAxisFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return sdf.format(new Date((long) value));
            }
        };
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new MyXAxisValueFormatter(xAxisFormatter));
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