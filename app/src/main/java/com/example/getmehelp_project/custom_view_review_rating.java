package com.example.getmehelp_project;

//import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class custom_view_review_rating extends BaseAdapter {
    private Context context;
    String [] rid,rate,review,date,userinfo;


    public custom_view_review_rating(Context applicationContext, String[] rid, String[] rate, String[] review, String[] date, String[] userinfo) {
        this.context = applicationContext;
        this.rid = rid;
        this.rate = rate;
        this.review = review;
        this.date = date;
        this.userinfo = userinfo;
    }

    @Override
    public int getCount() {
        return review.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_review_rating, null);//same class name

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView62);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView64);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView66);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView68);

        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);

        tv1.setText(rate[i]);
        tv2.setText(review[i]);
        tv3.setText(date[i]);
        tv4.setText(userinfo[i]);

        return gridView;
    }
}
