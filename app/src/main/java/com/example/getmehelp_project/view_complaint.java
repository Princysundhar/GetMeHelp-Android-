package com.example.getmehelp_project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

//import androidx.appcompat.app.AppCompatActivity;

public class view_complaint extends AppCompatActivity {
ListView li;
String [] cid,complaint,complaint_date,reply,reply_date;
SharedPreferences sh;
String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint);
        li = findViewById(R.id.listview);



    }
}