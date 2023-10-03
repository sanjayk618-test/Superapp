package com.example.superapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.List;

public class VerifiedActivity extends AppCompatActivity {

    TextView tv;

    private SharedPreferences sharedPreferences;

    private int count = 0;
    private TextView countDisplay;


    private static final String PREF_NAME = "MyPrefs"; // SharedPreferences file name
    //private static final String addr = "Myaddr";
  //  private static final String KEY_USERNAME = "phoneno";
    //private static final String useraddress = "useraddress";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verified);




// Use CognitoCachingCredentialsProvider to provide AWS credentials
// for the ApiClientFactory





        Intent intent = getIntent();

        String str = intent.getStringExtra("Address");




        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String phoneno = sharedPreferences.getString("key1", "");

       // sharedPreferences = getSharedPreferences(addr, MODE_PRIVATE);
        String useradd = sharedPreferences.getString("key2", "");

        String uid = sharedPreferences.getString("key3", "");


        Log.d("Update", "phoneno="+ phoneno + "  UserAddress=" + useradd + " ... userid= "+uid);


        countDisplay = findViewById(R.id.count);
        Button incrementButton = findViewById(R.id.incrementButton);
        Button decrementButton = findViewById(R.id.decrementButton);
        Button confirmButton=findViewById(R.id.confirm_button);

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                updateCountDisplay();
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 0) {
                    count--;
                    updateCountDisplay();
                }
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("key4", String.valueOf(count));
                edit.apply();


                Intent i = new Intent(VerifiedActivity.this, ConfirmActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    private void updateCountDisplay() {
        countDisplay.setText("Count: " + count);
    }
}