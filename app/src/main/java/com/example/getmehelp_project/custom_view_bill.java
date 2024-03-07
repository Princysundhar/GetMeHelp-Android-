package com.example.getmehelp_project;

//import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class custom_view_bill extends BaseAdapter {
    private Context context;
    String [] bid,amount,date,item;
    SharedPreferences sh;
    String url;



    public custom_view_bill(Context applicationContext, String[] bid, String[] amount, String[] date, String[] item) {
        this.context = applicationContext;
        this.bid =bid;
        this.amount = amount;
        this.date = date;
        this.item = item;
    }

    @Override
    public int getCount() {
        return amount.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.activity_custom_view_bill, null);//same class name

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView25);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView27);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView39);


        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);


        tv1.setText(amount[i]);
        tv2.setText(date[i]);
        tv3.setText(item[i]);


//
        return gridView;
    }
}


