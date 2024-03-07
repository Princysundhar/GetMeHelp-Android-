package com.example.getmehelp_project;

//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText  e1,e2,e3,e4,e5,e6,e7,e8;
    ImageView pho;
    RadioGroup r1;
    RadioButton rb1;
    RadioButton rb2;
    Bitmap bitmap = null;
    ProgressDialog pd;
    String url = "";
    Button b1;
    SharedPreferences sh;
    String gender ="male";
    String MobilePattern = "[6-9][0-9]{9}";
    String PinPattern = "[6-9][0-9]{5}";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String password_pattern="[A-Za-z0-9]{3,8}";

//    String password_pattern ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{4,}$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sh.getString("ip","");
        url=sh.getString("url","")+"android_user_registration";

        e1= (EditText) findViewById(R.id.editTextTextPersonName2);
        r1 = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.radioButton2);
        rb2 = findViewById(R.id.radioButton);
        e2 = (EditText) findViewById(R.id.editTextTextPersonName);
        e3= (EditText) findViewById(R.id.editTextTextPersonName3);
        e4= (EditText) findViewById(R.id.editTextTextPersonName4);
        e5= (EditText) findViewById(R.id.editTextNumber2);
        e6= (EditText) findViewById(R.id.editTextTextEmailAddress2);
        e7= (EditText) findViewById(R.id.editTextPhone);
        e8= (EditText) findViewById(R.id.editTextTextPassword);
        pho= (ImageView) findViewById(R.id.imageView3);
        b1 = (Button) findViewById(R.id.button2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }
        pho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = e1.getText().toString();
                final String house_name = e2.getText().toString();
                final String place = e3.getText().toString();
                final String post = e4.getText().toString();
                final String pin = e5.getText().toString();
                final String email = e6.getText().toString();
                final String contact = e7.getText().toString();
                final String password = e8.getText().toString();
//                String email_format = "[a-z0-9._%+\-]+@[a-z0-9._%+\-]+\.[a-z]{2,}$";

                if(rb2.isChecked()){
                    gender ="female";
                }
                int flag = 0;
                if(name.equalsIgnoreCase("")){
                    e1.setError("Enter name");
                    flag++;
                }

                if(house_name.equalsIgnoreCase("")){
                    e2.setError("Enter house name");
                    flag++;
                }
                if(place.equalsIgnoreCase("")){
                    e3.setError("Enter place");
                    flag++;
                }
                if(post.equalsIgnoreCase("")){
                    e4.setError("Enter post");
                    flag++;
                }
                if(!pin.matches(PinPattern)){
                    e5.setError("Enter valid pin");
                    flag++;
                }
                if(!email.matches(emailPattern)){
                    e6.setError("Enter the valid email");
                    flag++;
                }
                if(!contact.matches(MobilePattern)){
                    e7.setError("Enter contact");
                    flag++;
                }
                if(!password.matches(password_pattern)){
                    Toast.makeText(Register.this, "Enter valid password", Toast.LENGTH_SHORT).show();
                    flag++;
                }
                if(bitmap==null){
                    Toast.makeText(Register.this, "Choose image", Toast.LENGTH_SHORT).show();
                    flag++;
                }
                if(flag==0){
                    uploadBitmap(name,email,contact,house_name,place,post,pin,password);
                }

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                pho.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //converting to bitarray
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void uploadBitmap(final String name, final String email, final String contact,final String house_name ,final String place, final String post, final String pin,final String password) {


        pd = new ProgressDialog(Register.this);
        pd.setMessage("Uploading....");
        pd.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            pd.dismiss();


                            JSONObject obj = new JSONObject(new String(response.data));

                            if(obj.getString("status").equals("ok")){
                                Toast.makeText(getApplicationContext(), "Registration success", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), login.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Registration failed" ,Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences o = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                params.put("name",name);
                params.put("gender",gender);
                params.put("house_name",house_name);
                params.put("place",place);
                params.put("post",post);
                params.put("pin",pin);
                params.put("email",email);
                params.put("contact",contact);
//                params.put("photo",pho);
//
                params.put("password",password);
                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

}

