package com.example.superapp;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;

import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity {

    private FirebaseAuth mAuth;

    // variable for our text input
    // field for phone and OTP.
    private EditText edtPhone, edtOTP;

    // buttons for generating OTP and verifying OTP
    private View verifyOTPBtn;
    private View generateOTPBtn;


    // string for storing our verification ID
    private String verificationId;

    public SharedPreferences sharedPreferences;
    public static final String PREF_NAME = "MyPrefs"; // SharedPreferences file name
   // public static final String KEY_USERNAME = "phoneno";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseApp.initializeApp(/*context=*/ this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance());


        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);



        mAuth = FirebaseAuth.getInstance();

        edtPhone = findViewById(R.id.editTextPhone);
        edtOTP = findViewById(R.id.editotp);

        generateOTPBtn = findViewById(R.id.button2);
        verifyOTPBtn = findViewById(R.id.button);





        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // below line is for checking whether the user
                // has entered his mobile number or not.
                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    // when mobile number text field is empty
                    // displaying a toast message.
                    Toast.makeText(MainActivity2.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    // if the text field is not empty we are calling our
                    // send OTP method for getting OTP from Firebase.
                    String phone = "+91" + edtPhone.getText().toString();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("key1", phone);
                    editor.apply();

                    Log.d("Update", "phone number =" + phone);

                    sendVerificationCode(phone);





                }
            }
        });

        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                // validating if the OTP text field is empty or not.
           /*    if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                    // if the OTP text field is empty display
                    // a message to user to enter OTP
                    Toast.makeText(MainActivity2.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    verifyCode(edtOTP.getText().toString());
                    Log.d("Update", "VerifyOTP Button");
                }*/
               String phone = "+91" + edtPhone.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("key1", phone);
                editor.apply();

                Log.d("Update", "phone number =" + phone);
               Intent i = new Intent(MainActivity2.this, MyLocationLayerActivity.class);
                startActivity(i);
                finish();
                Log.d("Update", "Moving to CurrentLocation");
            }
        });
    }

    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        Log.d("Update", "Send verificationcode");
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.

            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created
            verificationId = s;
            Log.d("Update", "onCodesent" + s);
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                edtOTP.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
                Log.d("Update", "On Verification Completed");

            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("Update", "On Verification Failed");
        }
    };
    private void verifyCode(String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
        Log.d("Update", "Verify Code");

    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.


                            FirebaseUser cu = mAuth.getCurrentUser();

                            if (cu != null)
                            {
                                // User is signed in
                                String uid = cu.getUid();
                                Log.d("UID", "User UID: " + uid);

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("key3", uid);
                                editor.apply();
                            } else {
                                // No user is signed in
                                Log.d("UID", "No user signed in.");
                            }

                            String updatedphone = sharedPreferences.getString("key1", "");


                            Log.d("Updated phone", "phoneno="+ updatedphone);

                            Intent i = new Intent(MainActivity2.this, MyLocationLayerActivity.class);
                            startActivity(i);
                            finish();



                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(MainActivity2.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}

