package com.vsb.kol0482.mobileweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(!WidgetSettings.GetConnectionString().equals(""))
        {
            EditText conStr = findViewById(R.id.editTextTextPersonName);
            conStr.setText(WidgetSettings.GetConnectionString());
        }
    }

    public void confirm(View view) {
        EditText connectionString = findViewById(R.id.editTextTextPersonName);
        WidgetSettings.SetConnectionString(connectionString.getText().toString());
        EditText userName = findViewById(R.id.editTextTextPersonName3);
        EditText passWord = findViewById(R.id.editTextTextPersonName2);
        String usName = userName.getText().toString();
        String usPass = passWord.getText().toString();

        if(!WidgetSettings.GetConnectionString().equals("") && !usName.isEmpty() && !usPass.isEmpty())
        {

            getToken(usName, usPass, new Response.Listener<AccessToken>() {
                @Override
                public void onResponse(AccessToken token) {

                    String tmpToken = token.getAccessToken();
                    WidgetSettings.SetToken(tmpToken);
                    setResult(RESULT_OK);
                    finish();
                    Toast.makeText(Login.this, "Nastavení bylo uloženo.", Toast.LENGTH_SHORT).show(); // vypsání Toast hlášky
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Login.this, "Chyba při získávání tokenu", Toast.LENGTH_SHORT).show(); // vypsání Toast hlášky
                }
            });
        }
        else {
            Toast.makeText(this, "Uložení selhalo, všechny údaje nejsou vyplněné.", Toast.LENGTH_SHORT).show(); // vypsání Toast hlášky
        }
    }

    public void getToken(String username, String password, Response.Listener<AccessToken> successListener, Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(Request.Method.POST, WidgetSettings.GetConnectionString() + "/api/Token/AuthUser", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                AccessToken token = gson.fromJson(response, AccessToken.class);
                successListener.onResponse(token);
            }
        }, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("granttype", "password");
                params.put("username", username);
                params.put("password", password);
                params.put("clientid", "1");
                params.put("clientsecret", "abrakadabra");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onBackPressed() {
        // zavření aktuální aktivity
        finish();

        // zavření všech ostatních aktivit
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

