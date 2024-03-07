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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class custom_view_approved_worker extends BaseAdapter {
    private Context context;
    SharedPreferences sh;
    String [] wid,name,gender,address,email,contact,photo;

    public custom_view_approved_worker(Context applicationContext, String[] wid, String[] name,String[] gender, String[] address, String[] email, String[] contact, String[] photo) {
        this.context = applicationContext;
        this.wid = wid;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.photo = photo;
    }


    @Override
    public int getCount() {
        return name.length;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.activity_custom_view_approved_worker, null);//same class name

        } else {
            gridView = (View) view;

        }

        ImageView imageView = (ImageView) gridView.findViewById(R.id.imageView2);
        Button b1 = (Button) gridView.findViewById(R.id.button5); // for view service button
        Button b2 = (Button) gridView.findViewById(R.id.button6); // for view review& rating button
        b1.setTag(i);
        b2.setTag(i);

        TextView tv1 = (TextView) gridView.findViewById(R.id.textView29);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView31);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView33);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView35);
        TextView tv5 = (TextView) gridView.findViewById(R.id.textView38);

        b2.setOnClickListener(new View.OnClickListener() {                  // button click for view review
            @Override
            public void onClick(View view) {
                int pos = (int)view.getTag();
                sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed =sh.edit();
                ed.putString("wid",wid[pos]);
                ed.commit();
                Intent j =new Intent(context,view_review_rating.class);
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(j);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {              // view_service
            @Override
            public void onClick(View view) {
                int pos = (int)view.getTag();
                sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("wid",wid[pos]);
                ed.commit();
                Intent j = new Intent(context,view_service.class);
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(j);
            }
        });
        Button b3 = (Button) gridView.findViewById(R.id.button22);          // button chat
        b3.setTag(i);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int)view.getTag();
                sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("wid",wid[pos]);
                ed.commit();
                Intent k = new Intent(context,Chat.class);
                k.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(k);
            }
        });

        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);



        tv1.setText(name[i]);
        tv2.setText(gender[i]);
        tv3.setText(address[i]);
        tv4.setText(email[i]);
        tv5.setText(contact[i]);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":8000" + photo[i];    // For Image
        Picasso.with(context).load(url).transform(new CircleTransform()).into(imageView);//circle

//
        return gridView;
    }
}


