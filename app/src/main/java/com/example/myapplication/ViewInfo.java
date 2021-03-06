package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.DatabaseReference;

public class ViewInfo extends AppCompatActivity {
    TextView name, Age,Email,member,gender,phone,calls;
    Button bac;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final int REQUEST_CALL = 1;
    DatabaseReference myref4= database.getReference("User Data");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info);
        name = (TextView) findViewById(R.id.name);
        Age = (TextView) findViewById(R.id.age);
        Email = (TextView) findViewById(R.id.gmal);
        member = (TextView) findViewById(R.id.mem);
        gender = (TextView) findViewById(R.id.gen);
        phone = (TextView) findViewById(R.id.mobile_no);
        calls =  (Button) findViewById(R.id.call);
        bac = (Button) findViewById(R.id.back);
        bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Family.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


                startActivity(i);
            }
        });

        ValueEventListener listener2 = myref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    UserData user = snapshot1.getValue(UserData.class);
                    String fmember1 = user.fmember;
                    if (fmember1.equals("Visuall imapaired")){
                        String phoneno = user.phoneno;
                        name.setText("Name : "+user.fname);
                        Age.setText("Age : "+user.age);
                        Email.setText("Gmail : "+user.email);
                        member.setText("Family Member : "+user.fmember);
                        gender.setText("Gender : "+user.gender);
                        phone.setText("Phone No. : "+user.phoneno);
                        String phoneno1 = user.phoneno;


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        calls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });
    }
    private void makePhoneCall(){
        ValueEventListener listener3 = myref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    UserData user = snapshot1.getValue(UserData.class);
                    String fmember1 = user.fmember;
                    if (fmember1.equals("Visuall imapaired")){
                        if (ContextCompat.checkSelfPermission(ViewInfo.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(ViewInfo.this,new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);

                        }else{
                            String phoneno = "tel:"+user.phoneno;
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(phoneno)));

                        }

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(ViewInfo.this,MainActivity.class);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);

    }
}