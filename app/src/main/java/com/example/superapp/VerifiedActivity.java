package com.example.superapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class VerifiedActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verified);

        tv=findViewById(R.id.viewtext);

        Intent intent = getIntent();

        String str = intent.getStringExtra("Address");

        tv.setText(str);

    }
}