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


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerifiedActivity extends AppCompatActivity {

    TextView tv;

    private SharedPreferences sharedPreferences;

    private SharedPreferences sdp;
    public int count = 0;
    public int Total=0;
    private TextView TotalDisplay;
    private TextView countDisplay;


    private static final String PREF_NAME = "MyPrefs"; // SharedPreferences file name
    //private static final String addr = "Myaddr";
  //  private static final String KEY_USERNAME = "phoneno";
    //private static final String useraddress = "useraddress";

    public static final String Pn = "datain";


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
        TotalDisplay=findViewById(R.id.Total);
        Button incrementButton = findViewById(R.id.incrementButton);
        Button decrementButton = findViewById(R.id.decrementButton);
        Button confirmButton=findViewById(R.id.confirm_button);

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                Total+=50;
                updateTotalDisplay();
                updateCountDisplay();
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 0) {
                    count--;
                    Total-=50;
                    updateTotalDisplay();
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


                sdp = getSharedPreferences(Pn, MODE_PRIVATE);

                String idt= sdp.getString("key1", "");

                // sharedPreferences = getSharedPreferences(addr, MODE_PRIVATE);
                String em = sdp.getString("key2", "");




                Log.d("IDT Update", "IDT ==  " + idt);
                Log.d("email Update ", "Mail ==  " + em);



                RequestQueue requestQueue = Volley.newRequestQueue(VerifiedActivity.this);

// Define the URL of the API endpoint.
                String url = "https://6wv32jwoc3.execute-api.ap-south-1.amazonaws.com/Dev/data/";

// Define your authorization token (e.g., a Bearer token).

                // Create a JSON object for the request body.

                try {
                    JSONObject requestBody = new JSONObject();
                    requestBody.put("address", useradd);
                    requestBody.put("email", em);
                    requestBody.put("count",count);

                    // Create a JSON object to wrap the request body.
                    JSONObject requestObject = new JSONObject();
                    requestObject.put("body", requestBody.toString());

                    // Create a request with a custom header and request body.
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("OnResponse ", "onResponse" +response);
                                    // Handle the successful response here.
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Log.d("OnError ", "onError" +error);
                                    // Handle errors here.
                                }
                            }
                    ) {
                        @Override
                        public Map<String, String> getHeaders() {
                            // Add the authorization header to the request.
                            Map<String, String> headers = new HashMap<>();
                            headers.put("Authorization", "Bearer " + idt);
                            return headers;
                        }
                    };

                    // Add the request to the RequestQueue.
                    requestQueue.add(jsonObjectRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }







                Intent i = new Intent(VerifiedActivity.this, ConfirmActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    private void updateCountDisplay() {
        countDisplay.setText("Count: " + count);
    }


    private void updateTotalDisplay() {TotalDisplay.setText("Total: " + Total);}
}