/*
 * Developed by Keivan Kiyanfar on 10/9/18 11:38 PM
 * Last modified 10/9/18 11:38 PM
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.superapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
//import com.example.cognito_conf.R;

public class Login extends AppCompatActivity {
    // ############################################################# View Components
    TextView txtNotAccount;     // For creating account
    //TextView txtForgetPass;     // For retrieving password
    Button btnLogin;            // Button for Login
    EditText etUsername;
    EditText etPassword;
    // ############################################################# End View Components

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        initViewComponents();
    }

    private void initViewComponents(){
        txtNotAccount = findViewById(R.id.txtNotAccount);
        //txtForgetPass= findViewById(R.id.txtForgetPass);
        btnLogin = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);



        txtNotAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cognito authentication = new Cognito(getApplicationContext());
                authentication.userLogin(etUsername.getText().toString().replace(" ", ""), etPassword.getText().toString());

            }
        });



    }
}
