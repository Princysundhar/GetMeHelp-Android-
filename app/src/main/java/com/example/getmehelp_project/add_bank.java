package com.example.getmehelp_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class add_bank extends AppCompatActivity {
EditText e1,e2,e3,e4;
Button b1;
SharedPreferences sh;
String url;
String ifsc_pattern = "^[A-Za-z]{4}0[A-Z0-9a-z]{6}$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);
        e1 = findViewById(R.id.editTextTextPersonName5);
        e2 = findViewById(R.id.editTextTextPersonName6);
        e3 = findViewById(R.id.editTextTextPersonName7);
        e4 = findViewById(R.id.editTextTextPersonName8);
        b1 = findViewById(R.id.button11);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String bankname = e1.getText().toString();
                final String account_no = e2.getText().toString();
                final String ifsc_code = e3.getText().toString();
                final String amount = e4.getText().toString();

                int flag = 0;
                if(bankname.equalsIgnoreCase("")){
                    e1.setError("Enter bank name");
                    flag++;
                }
                if(account_no.equalsIgnoreCase("")){
                    e2.setError("Enter account no");
                    flag++;
                }
                if(!ifsc_code.matches(ifsc_pattern)){
                    e3.setError("Enter ifsc code");
                    flag++;
                }
                if(amount.equalsIgnoreCase("")){
                    e4.setError("Enter amount");
                    flag++;
                }
                if(flag ==0){
                    sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sh.getString("ip","");
                    url = sh.getString("url","")+"android_add_bank";

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                            Toast.makeText(add_bank.this, "Bank added", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), view_bank.class);
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
                            params.put("lid", sh.getString("lid", ""));//passing to python
                            params.put("bank_name", bankname);
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
}