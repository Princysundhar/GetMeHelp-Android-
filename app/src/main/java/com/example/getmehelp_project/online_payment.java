package com.example.getmehelp_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.PreferenceChangeEvent;

//import androidx.appcompat.app.AppCompatActivity;

public class online_payment extends AppCompatActivity {
EditText e1;
EditText e2;
EditText e3;
//EditText e4;
TextView e4;
Button b1;
SharedPreferences sh;
String url;
String amount="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1 = findViewById(R.id.editTextTextPersonName14);
        e2 = findViewById(R.id.editTextTextPersonName15);
        e3 = findViewById(R.id.editTextTextPersonName16);
        e4 = findViewById(R.id.textView4);
        b1 = findViewById(R.id.button16);
        e4.setText(sh.getString("amount",""));
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String bank_name = e1.getText().toString();
                final String account_no = e2.getText().toString();
                final String ifsc_code = e3.getText().toString();
                final String amount =sh.getString("amount","");
                int flag=0;
                if (bank_name.equalsIgnoreCase("")){
                    e1.setError("Required");
                    flag++;
                }
                if (account_no.equalsIgnoreCase("")){
                    e2.setError("Required");
                    flag++;
                }
                if (ifsc_code.equalsIgnoreCase("")){
                    e3.setError("Required");
                    flag++;
                }
                if (flag==0) {

                    sh.getString("ip", "");
                    url = sh.getString("url", "") + "android_online_payment";

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                            Toast.makeText(online_payment.this, "payment via online & credit added", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), view_request.class);
                                            startActivity(i);
                                        }

                                        if (jsonObj.getString("status").equalsIgnoreCase("okk")) {
                                            Toast.makeText(online_payment.this, "payment done", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), view_request.class);
                                            startActivity(i);
                                        } else if (jsonObj.getString("status").equalsIgnoreCase("no")) {
                                            Toast.makeText(online_payment.this, "wrong bank details", Toast.LENGTH_SHORT).show();
//                                        Intent i =new Intent(getApplicationContext(),Make_payment.class);
//                                        startActivity(i);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Inefficient balance", Toast.LENGTH_LONG).show();
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
                            params.put("lid", sh.getString("lid", ""));//passing to python
                            params.put("rid", sh.getString("rid", ""));//passing to python
                            params.put("wid", sh.getString("wid", ""));//passing to python
                            params.put("bank_name", bank_name);
                            params.put("account_no", account_no);
                            params.put("ifsc_code", ifsc_code);
                            params.put("amount", amount);

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
    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),view_request.class);
        startActivity(i);
//        super.onBackPressed();
    }
}