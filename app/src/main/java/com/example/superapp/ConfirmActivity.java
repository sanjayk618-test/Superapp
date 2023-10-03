package com.example.superapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class ConfirmActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        String count = sharedPreferences.getString("key4", "");

        Log.d("In conifrm", "Count : " + count);

    }
}