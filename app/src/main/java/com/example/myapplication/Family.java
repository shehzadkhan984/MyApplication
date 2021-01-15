package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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

public class Family extends AppCompatActivity {
    TextView name,lati,longi;
    TextView textLocation_lat,textLocation_long,sensor1,sensor2,sensor3,tempra,humidity;
    Button logout,location_view;
    FirebaseDatabase firebasedatabase;
    DatabaseReference myref;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    // Write a message to the database

    DatabaseReference myref1 = database.getReference("Location");

    DatabaseReference myref2 = database.getReference("weather");

    DatabaseReference myref3 = database.getReference("Sensors");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebasedatabase = FirebaseDatabase.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        logout = findViewById(R.id.flogout);
        name = findViewById(R.id.fname);
        lati = findViewById(R.id.lati);
        longi = findViewById(R.id.longi);
        sensor1 = (TextView) findViewById(R.id.sensor1);
        sensor2 = (TextView) findViewById(R.id.sensor2);
        sensor3 = (TextView) findViewById(R.id.sensor3);

//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Location");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Double latitude = snapshot.child("lat").getValue(Double.class);
//                Double longitude = (Double) snapshot.child("long").getValue(Double.class);
//                lati.setText(latitude.toString());
//                longi.setText(longitude.toString());
//
//                // Add a marker in Sydney and move the camera
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification","My Notification",NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        ValueEventListener listener = myref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Notification

                int notifi = snapshot.child("noti").getValue(int.class);
                int sens1 = snapshot.child("S1").getValue(int.class);
                sensor1.setText("Sensor1 "+sens1);
                int sens2 = snapshot.child("S2").getValue(int.class);
                sensor2.setText("Sensor2 "+sens2);
                int sens3 = snapshot.child("S3").getValue(int.class);
                sensor3.setText("Sensor3 "+sens3);

                if (sens1 <= 100 && sens2 > 100 && sens3 > 100){
                    sensor1.setTextColor(Color.parseColor("#FF0000"));

                }else if (sens1 > 100 && sens2 <= 100 && sens3 > 100){
                    sensor2.setTextColor(Color.parseColor("#FF0000"));


                }
                else if (sens1 > 100 && sens2 > 100 && sens3 <= 100){
                    sensor3.setTextColor(Color.parseColor("#FF0000"));
                }
                else if (sens1 <= 100 && sens2 <= 100 && sens3 > 100){
                    sensor1.setTextColor(Color.parseColor("#FF0000"));
                    sensor2.setTextColor(Color.parseColor("#FF0000"));
                }
                else if (sens1 <= 100 && sens2 > 100 && sens3 <= 100){
                    sensor1.setTextColor(Color.parseColor("#FF0000"));
                    sensor3.setTextColor(Color.parseColor("#FF0000"));
                }
                else if (sens1 > 100 && sens2 <= 100 && sens3 <= 100){
                    sensor2.setTextColor(Color.parseColor("#FF0000"));
                    sensor3.setTextColor(Color.parseColor("#FF0000"));
                }

                if (notifi == 1){
                    NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(Family.this,"My Notification");
                    mbuilder.setContentTitle("Emergency");
                    mbuilder.setContentText("Emergency situation");
                    mbuilder.setSmallIcon(R.mipmap.ic_logo_app);
                    mbuilder.setAutoCancel(true);
                    Intent intenti = new Intent(Family.this,MapsActivity.class);
                    intenti.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    PendingIntent pendingIntent = PendingIntent.getActivity(Family.this,0,intenti,PendingIntent.FLAG_UPDATE_CURRENT);
                    mbuilder.setContentIntent(pendingIntent);

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Family.this);
                    managerCompat.notify(1, mbuilder.build());
                    myref3.child("noti").setValue(0);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        name.setText("Welcome to dashboard "+getIntent().getStringExtra("name"));
        location_view = (Button) findViewById(R.id.location_view);
        location_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Family.this,MapsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
//        ValueEventListener listener = myref3.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int notifi = snapshot.child("Noty").getValue(int.class);
//
//                if (notifi == 1){
//////                    String message = "This is a Notification";
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(Family.this).setContentTitle("Emergency Notification")
//                            .setContentText(message).setAutoCancel(true);
//                    Intent intenti = new Intent(Family.this,MapsActivity.class);
//                    intenti.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                    PendingIntent pendingIntent = PendingIntent.getActivity(Family.this,0,intenti,PendingIntent.FLAG_UPDATE_CURRENT);
//                    builder.setContentIntent(pendingIntent);
//                    NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//                    notificationManager.notify(0,builder.build());

//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Family.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

}