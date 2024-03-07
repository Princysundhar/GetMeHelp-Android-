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

public class IP_page extends AppCompatActivity {
EditText e1;
Button b1;
SharedPreferences s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_page);
        e1 = findViewById(R.id.editTextTextPersonName9);
        b1 = findViewById(R.id.button14);

        s = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1.setText(s.getString("ipaddress",""));
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              String ipaddress = e1.getText().toString();
              int flag = 0;
              if (ipaddress.equalsIgnoreCase("")){
                  //e1.setError("Enter ip");
                  Toast.makeText(IP_page.this, "Enter ip address", Toast.LENGTH_SHORT).show();
                  flag++;
              }

              if (flag==0) {


                  String url = "http://" + ipaddress + ":8000/";
                  Toast.makeText(IP_page.this, "welcome" + ipaddress, Toast.LENGTH_SHORT).show();
                  SharedPreferences.Editor ed = s.edit();
                  ed.putString("ipaddress", ipaddress);
                  ed.putString("url", url);
                  ed.commit();


                  Intent i = new Intent(getApplicationContext(), login.class);       // Redirecting to ip_page to login page
                  startActivity(i);
              }
            }
        });
    }
}