package com.example.getmehelp_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class change_password extends AppCompatActivity {
    EditText e1,e2,e3;
    Button b1;
    SharedPreferences sh;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        e1 = findViewById(R.id.editText2);
        e2 = findViewById(R.id.editText3);
        e3 = findViewById(R.id.editText4);
        b1 = findViewById(R.id.button19);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String old_password = e1.getText().toString();
                final String new_password = e2.getText().toString();
                final String confirm_password = e3.getText().toString();
                int flag=0;
                if(old_password.length()==0){
                    e1.setError("missing");
                    flag++;
                }
                if(new_password.length()==0){
                    e2.setError("missing");
                    flag++;
                }
                if(!new_password.equalsIgnoreCase(confirm_password)){
//                    e1.setError("password mismatch");
                    Toast.makeText(change_password.this, "password mismatch", Toast.LENGTH_SHORT).show();
                    flag++;
                }
                if(flag == 0){
                    sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sh.getString("ip","");
                    url = sh.getString("url","")+"android_change_password";

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("already exists")) {
                                            Toast.makeText(change_password.this, "password exists", Toast.LENGTH_SHORT).show();
//                                        Intent i =new Intent(getApplicationContext(),login.class);
//                                        startActivity(i);


                                        } if (jsonObj.getString("status").equalsIgnoreCase("password updated")) {
                                            Toast.makeText(change_password.this, "password changed", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), login.class);
                                            startActivity(i);
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "password mismatch", Toast.LENGTH_LONG).show();
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
                            params.put("old_password",old_password);
                            params.put("new_password",new_password);
                            params.put("confirm_password",confirm_password);

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
