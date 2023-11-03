/*
 * Developed by Keivan Kiyanfar on 10/9/18 9:48 PM
 * Last modified 10/9/18 9:48 PM
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.superapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.ContentValues.TAG;



public class Signup extends AppCompatActivity {
    // ############################################################# View Components
    EditText etUsername;        // Enter Username
    EditText etEmail;           // Enter Email
    EditText etMobile;          // Enter Mobile
    EditText etPass;            // Enter Password
    EditText etRepeatPass;      // Repeat Password
    EditText etConfCode;        // Enter Confirmation Code

    Button btnSignUp;           // Sending data to Cognito for registering new user
    Button btnVerify;
    // ############################################################# End View Components

    // ############################################################# Cognito connection
    Cognito authentication;
    private String userId;
    // ############################################################# End Cognito connection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        authentication = new Cognito(getApplicationContext());
        initViewComponents();
    }

    private void initViewComponents () {
   //     etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etPass = findViewById(R.id.etPass);
        etRepeatPass = findViewById(R.id.etRepeatPass);
        etConfCode = findViewById(R.id.etConfCode);

        String email= etEmail.getText().toString();

        btnSignUp = findViewById(R.id.btnSignUp);
        btnVerify = findViewById(R.id.btnVerify);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = etMobile.getText().toString().replace(" ", "");

                if (phone.length() == 10) {

                    if (etPass.getText().toString().endsWith(etRepeatPass.getText().toString())) {
                        userId = etEmail.getText().toString().replace(" ", "");
                        authentication.addAttribute("name", userId);
                        authentication.addAttribute("phone_number", "+91" + etMobile.getText().toString().replace(" ", ""));
                        authentication.addAttribute("email", etEmail.getText().toString().replace(" ", ""));
                        authentication.signUpInBackground(userId, etPass.getText().toString());

                    } else {
                        Toast.makeText(getApplicationContext(), "Password Does Not Match", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Phone number must be 10 digits", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authentication.confirmUser(userId, etConfCode.getText().toString().replace(" ", ""));
                //finish();
            }
        });

    }

}
