package com.example.getmehelp_project;

//import androidx.appcompat.app.AppCompatActivity;

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

public class login extends AppCompatActivity {
EditText e1,e2;
Button b1,b2;
SharedPreferences sh;
String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1 = findViewById(R.id.editTextTextPersonName10);
        e2 = findViewById(R.id.editTextTextPersonName11);
        b1 = findViewById(R.id.button15);
        b2 = findViewById(R.id.button17);
        b2.setOnClickListener(new View.OnClickListener() {      //Button Register
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Register.class);
                startActivity(i);

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {      // Button Login
            @Override
            public void onClick(View view) {
                final String username = e1.getText().toString();
                final String password = e2.getText().toString();
                int flag = 0;
                if(username.equalsIgnoreCase("")){
                    Toast.makeText(login.this, "Enter username", Toast.LENGTH_SHORT).show();
                    flag++;
                }
                if(password.length()>8){
                    Toast.makeText(login.this, "Enter valid password", Toast.LENGTH_SHORT).show();
                    flag++;
                }
                if(flag==0){
                    sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sh.getString("ip","");
                    url = sh.getString("url","")+"android_login";

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                            Toast.makeText(login.this, "welcome", Toast.LENGTH_SHORT).show();
                                            String typ = jsonObj.getString("type");
                                            String id = jsonObj.getString("lid");
                                            String name = jsonObj.getString("name");
                                            String email = jsonObj.getString("email");
                                            String photo = jsonObj.getString("photo");
                                            SharedPreferences.Editor ed = sh.edit();
                                            ed.putString("lid", id);
                                            ed.putString("name", name);
                                            ed.putString("email", email);
                                            ed.putString("photo", photo);
                                            ed.commit();
                                            if (typ.equalsIgnoreCase("user")) {
                                                Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_LONG).show();

                                                Intent j = new Intent(getApplicationContext(),MyService.class); // Location Access
                                                startService(j);

                                                Intent i = new Intent(getApplicationContext(), home.class);
                                                startActivity(i);
                                            }

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
                            params.put("name",username);
                            params.put("password",password);



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