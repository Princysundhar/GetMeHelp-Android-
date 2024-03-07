package com.example.getmehelp_project;

//import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class custom_view_request extends BaseAdapter {
    private Context context;
    String[] rid, worker, service, amount, date,latitude,longitude,payment_status,wid,bstatus,rstatus;
    SharedPreferences sh;
    String url;



    public custom_view_request(Context applicationContext, String[] rid, String[] worker, String[] service, String[] amount, String[] date, String[] latitude, String[] longitude, String[] payment_status, String[] wid, String[] bstatus, String[] rstatus) {
        this.context = applicationContext;
        this.rid = rid;
        this.worker = worker;
        this.service = service;
        this.amount = amount;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.payment_status = payment_status;
        this.wid = wid;
        this.bstatus = bstatus;
        this.rstatus = rstatus;
    }

    @Override
    public int getCount() {
        return worker.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_request, null);//same class name

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView17);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView19);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView21);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView23);
        TextView tv5 = (TextView) gridView.findViewById(R.id.textView3);
        Button b1 = (Button) gridView.findViewById(R.id.button4); // Location

        Button b2 = (Button) gridView.findViewById(R.id.button7); //bill


        Button b3 = (Button) gridView.findViewById(R.id.button8);// make payment

        Button b4 = (Button) gridView.findViewById(R.id.button9);  // send rate

        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);

        if(bstatus[i].equalsIgnoreCase("Approve"))
        {

            b3.setVisibility(View.VISIBLE);
            b3.setEnabled(true);
            b4.setEnabled(true);
            b2.setEnabled(true);

            if(rstatus[i].equalsIgnoreCase("rated")) {
                b4.setEnabled(false);
            }

            if(payment_status[i].equalsIgnoreCase("pending")){
                b3.setEnabled(true);
                b3.setVisibility(View.VISIBLE);

            }
        }

        b1.setTag(i);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ik=(int)view.getTag();
                String url = "http://maps.google.com/?q=" + latitude[ik] + "," + longitude[ik];
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        b2.setTag(i);
        b2.setOnClickListener(new View.OnClickListener() {      // view bill
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);

                SharedPreferences.Editor ed = sh.edit();
                ed.putString("rid",rid[pos]);
                ed.putString("wid",wid[pos]);
                ed.commit();

                Intent i = new Intent(context.getApplicationContext(),view_bill.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        if(payment_status[i].equalsIgnoreCase("online")){
            b3.setEnabled(false);
            b3.setVisibility(View.VISIBLE);
        }
        if(payment_status[i].equalsIgnoreCase("offline")){
            b3.setEnabled(false);
            b3.setVisibility(View.VISIBLE);

        }

        b3.setTag(i);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);

                SharedPreferences.Editor ed = sh.edit();
                ed.putString("rid",rid[pos]);
                ed.putString("wid",wid[pos]);
                ed.putString("amount",amount[pos]);
                ed.commit();

                Intent i = new Intent(context,Make_payment.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
        b4.setTag(i);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);

                SharedPreferences.Editor ed = sh.edit();
                ed.putString("rid",rid[pos]);
                ed.putString("wid",wid[pos]);
                ed.commit();

                Intent i = new Intent(context,send_rate.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });


        tv1.setTextColor(Color.RED);  //color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);


        tv1.setText(worker[i]);
        tv2.setText(service[i]);
        tv3.setText(amount[i]);
        tv4.setText(date[i]);
        tv5.setText(bstatus[i]);


//
        return gridView;
    }
}