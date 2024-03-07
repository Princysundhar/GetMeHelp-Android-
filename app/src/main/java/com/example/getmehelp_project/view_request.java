package com.example.getmehelp_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
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

//import androidx.appcompat.app.AppCompatActivity;

public class view_request extends AppCompatActivity {
ListView li;
String [] rid,worker,service,amount,date,latitude,longitude,payment_status,wid,bstatus,rstatus;
SharedPreferences sh;
String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);
        li = findViewById(R.id.listview);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url = sh.getString("url","")+"android_view_request";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js = jsonObj.getJSONArray("data");//from python
                                rid = new String[js.length()];
                                worker = new String[js.length()];
                                service = new String[js.length()];
                                amount = new String[js.length()];
                                date = new String[js.length()];
                                latitude = new String[js.length()];
                                longitude = new String[js.length()];
                                payment_status = new String[js.length()];
                                wid = new String[js.length()];
                                bstatus = new String[js.length()];
                                rstatus = new String[js.length()];



                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    rid[i] = u.getString("rid");        //dbcolumn name in double quotes
                                    worker[i] = u.getString("worker");
                                    service[i] = u.getString("service");
                                    amount[i] = u.getString("amount");
                                    date[i] = u.getString("date");
                                    latitude[i] = u.getString("latitude");
                                    longitude[i] = u.getString("longitude");
                                    payment_status[i] = u.getString("payment_status");
                                    wid[i] = u.getString("wid");
                                    bstatus[i] = u.getString("b_status");
                                    rstatus[i] = u.getString("r_status");

                                }
                                li.setAdapter(new custom_view_request(getApplicationContext(), rid,worker, service,amount,date,latitude,longitude,payment_status,wid,bstatus,rstatus));//custom_view_service.xml and li is the listview object


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
                params.put("sid", sh.getString("sid", ""));//passing to python
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

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),home.class);
        startActivity(i);
//        super.onBackPressed();
    }
}


