package com.vsb.kol0482.mobileweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void confirm(View view) {
        EditText connectionString = findViewById(R.id.editTextTextPersonName);
        WidgetSettings.SetConnectionString(connectionString.getText().toString());
        if(!WidgetSettings.GetConnectionString().equals(""))
        {
            setResult(RESULT_OK);
            finish();
            Toast.makeText(this, "Nastavení bylo uloženo.", Toast.LENGTH_SHORT).show(); // vypsání Toast hlášky

        }
        else {
            Toast.makeText(this, "Uložení selhalo", Toast.LENGTH_SHORT).show(); // vypsání Toast hlášky
        }
    }
}