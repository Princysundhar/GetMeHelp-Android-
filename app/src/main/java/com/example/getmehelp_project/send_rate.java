package com.example.getmehelp_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class send_rate extends AppCompatActivity {
RatingBar r1;
EditText e1;
Button b1;
SharedPreferences sh;
String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_rate);
        r1 = findViewById(R.id.ratingBar);
        e1 = findViewById(R.id.editText);
        b1 = findViewById(R.id.button18);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final int rate=int.valueOf(r1.getRating());     // rating
                final float rate = r1.getRating();
                final String review =e1.getText().toString();
                int flag = 0;
                if (rate==0.0){
                    Toast.makeText(getApplicationContext(), "set the rate", Toast.LENGTH_SHORT).show();
                    flag++;

                }
                if(review.equalsIgnoreCase("")){
                    e1.setError("Enter review");
                    flag++;
                }
                if(flag==0){
                    sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sh.getString("ip","");
                    url = sh.getString("url","")+"android_send_rate";

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                            Toast.makeText(send_rate.this, "Rate added", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), home.class);
                                            startActivity(i);


                                        } else {
                                            Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                        }

                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {

                        //                value Passing android to python
                        @Override
                        protected Map<String, String> getParams() {
                            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("rate", String.valueOf(rate));  // rating
                            params.put("review", review);
//                        params.put("lid", sh.getString("lid", ""));//passing to python
                            params.put("rid", sh.getString("rid", ""));//passing to python


                            return params;
                        }
                    };


                    int MY_SOCKET_TIMEOUT_MS = 100000;

                    postRequest.setRetryPolicy(new DefaultRetryPolicy(
                            MY_SOCKET_TIMEOUT_MS,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(postRequest);

                }




            }
        });



    }
}





