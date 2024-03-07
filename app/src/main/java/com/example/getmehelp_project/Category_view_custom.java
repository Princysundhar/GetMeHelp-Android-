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

public class Category_view_custom extends BaseAdapter {
    private  Context context;
    String [] cid,category;
    SharedPreferences sh;

    public Category_view_custom(Context applicationContext, String[] cid, String[] category) {
        this.context = applicationContext;
        this.cid = cid;
        this.category = category;
    }


    @Override
    public int getCount() {
        return category.length;
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
            gridView = inflator.inflate(R.layout.activity_category_view_custom, null);//same class name

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView6);
        Button b1 = (Button) gridView.findViewById(R.id.button); // for viewworker button
        b1.setTag(i);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int)view.getTag();
                sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("cid",cid[pos]);

                ed.commit();
                Intent j = new Intent(context.getApplicationContext(),view_approved_worker.class);
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(j);
            }
        });
//        TextView tv3 = (TextView) gridView.findViewById(R.id.t61);


        tv1.setTextColor(Color.RED);//color setting
//        tv2.setTextColor(Color.BLACK);
//        tv3.setTextColor(Color.BLACK);


        tv1.setText(category[i]);
//        tv2.setText(post[i]);
//        tv3.setText(date[i]);


//
        return gridView;
    }
}

