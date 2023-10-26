package com.example.superapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;





import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.services.cognitoidentityprovider.model.ResendConfirmationCodeRequest;
import com.amazonaws.services.cognitoidentityprovider.model.ResendConfirmationCodeResult;

public class Reconfirm extends AppCompatActivity {


    TextView reemail , codesent;     // For creating account
    //TextView txtForgetPass;     // For retrieving password
    Button sendcode , confirmcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reconfirm);

        reemail=findViewById(R.id.editTextTextEmailAddress);
        sendcode=findViewById(R.id.ReconfirmButton);
        codesent=findViewById(R.id.reconfirmCode);
        confirmcode=findViewById(R.id.ConfirmButton);


        sendcode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Cognito cogni=new Cognito(Reconfirm.this);

            }
        });



    }
}