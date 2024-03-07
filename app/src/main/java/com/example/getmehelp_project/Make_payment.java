package com.example.getmehelp_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

//import androidx.appcompat.app.AppCompatActivity;

public class Make_payment extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {
RadioGroup r1;
RadioButton rb1;
RadioButton rb2;
TextView credit_point,amount_payable,pay_using_cp,no_credit;
CheckBox c1;
Button b1,pay;
String url;
SharedPreferences sh;

String mode = "online";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
        r1 = findViewById(R.id.radioGroup2);
        rb1 = findViewById(R.id.radioButton4);
        rb2 = findViewById(R.id.radioButton5);
        credit_point = findViewById(R.id.textView7);
        amount_payable = findViewById(R.id.textView8);
        pay_using_cp = findViewById(R.id.textView9);
        no_credit = findViewById(R.id.textView11);
        c1 = findViewById(R.id.checkBox);
        pay = findViewById(R.id.button20);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        credit_point.setVisibility(View.INVISIBLE);
        amount_payable.setVisibility(View.INVISIBLE);
        pay_using_cp.setVisibility(View.INVISIBLE);
        pay.setVisibility(View.INVISIBLE);
        no_credit.setVisibility(View.INVISIBLE);
        c1.setVisibility(View.INVISIBLE);

        c1.setOnCheckedChangeListener(this);
        b1 = findViewById(R.id.button13);
//        r1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                Toast.makeText(Make_payment.this, ""+i, Toast.LENGTH_SHORT).show();
////                if(i == 2131230922) {
//                if(i == 2131361995) {
//                    c1.setVisibility(View.VISIBLE);
//                }
//                else {
//                    c1.setVisibility(View.INVISIBLE);
//                    no_credit.setVisibility(View.INVISIBLE);
//
//                }
//            }
//        });
//
//
        rb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                c1.setVisibility(View.VISIBLE);
            }
        });

        rb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                c1.setVisibility(View.INVISIBLE);
                    no_credit.setVisibility(View.INVISIBLE);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(rb2.isChecked()){
                    mode = "offline";



                    sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sh.getString("ip", "");
                    url = sh.getString("url", "") + "android_make_payment";


                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                            Toast.makeText(Make_payment.this, "payment is offline", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), view_request.class);
                                            startActivity(i);



                                        }else if (jsonObj.getString("status").equalsIgnoreCase("Already payed")) {
                                            Toast.makeText(Make_payment.this, "payment is offline", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), view_request.class);
                                            startActivity(i);
                                        }

                                        else {
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
                            params.put("mode", mode);
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
                else{

                    Intent i = new Intent(getApplicationContext(), online_payment.class);
                    startActivity(i);
                }

            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b == true){
//            String am=totalamount.getText().toString();
            sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sh.getString("ip","");
            final String totalamount=sh.getString("amount", "");
            url=sh.getString("url","")+"and_ajax_view_credit_points";

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//view service code
                                    String c=jsonObj.getString("credit");
                                    credit_point.setText("Credit points Available: "+c);
                                    String p=jsonObj.getString("payable");
                                    amount_payable.setText("Amount payable with credit points: ₹"+p);
                                    String b=jsonObj.getString("balance");
                                    pay_using_cp.setText("Pay using ₹"+b+"+"+c+"credit points");
                                    credit_point.setTextColor(Color.RED);


                                    credit_point.setVisibility(View.VISIBLE);
                                    pay_using_cp.setVisibility(View.VISIBLE);
                                    amount_payable.setVisibility(View.VISIBLE);
                                    pay.setVisibility(View.VISIBLE);
                                    amount_payable.setTextColor(Color.BLUE);
                                    pay_using_cp.setTextColor(Color.BLUE);
                                    SharedPreferences.Editor ed = sh.edit();
                                    ed.putString("amount_credit", b);
                                    ed.putString("credits", c);
                                    ed.commit();
                                }
                                if (jsonObj.getString("status").equalsIgnoreCase("no")) {
//view service code
                                    no_credit.setTextColor(Color.RED);
                                    no_credit.setText("No Credit Points Available");
                                    no_credit.setVisibility(View.VISIBLE);


                                }


                                // }
                                else {
//                                    Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                }

                            }    catch (Exception e) {
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
//                String l=Locationservice.lati;
//                String lb=Locationservice.logi;
//                String place=Locationservice.place;
                    params.put("id",sh.getString("lid",""));
//                params.put("latitude",l);
                    params.put("amt",totalamount);
//                params.put("longitude",lb);
//                params.put("place",place);
//                params.put("stt",ctt);
                    return params;
                }
            };


            int MY_SOCKET_TIMEOUT_MS=100000;

            postRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(postRequest);

        }
        else {

                credit_point.setVisibility(View.INVISIBLE);
                pay_using_cp.setVisibility(View.INVISIBLE);
                amount_payable.setVisibility(View.INVISIBLE);
                pay.setVisibility(View.INVISIBLE);

        }
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), credit_point_payment.class);
                startActivity(i);

            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
//        Toast.makeText(this, ""+i, Toast.LENGTH_SHORT).show();
//        rb1=r1.findViewById(i);
//        String d=((RadioButton)findViewById(r1.getCheckedRadioButtonId())).toString();
//        Toast.makeText(this, ""+d, Toast.LENGTH_SHORT).show();
//        if (d == "Online payment"){
//            Toast.makeText(this, "hii", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(this, "no", Toast.LENGTH_SHORT).show();
//        }
//        boolean a=rb1.isChecked();
//        if (a){
//            c1.setVisibility(View.VISIBLE);
//
//        }
//        else {
//            c1.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),view_request.class);
        startActivity(i);
//        super.onBackPressed();
    }
}