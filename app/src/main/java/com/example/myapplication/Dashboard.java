package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class Dashboard extends AppCompatActivity {
    TextView name, textLocation_lat,textLocation_long,sensor1,sensor2,sensor3;
    Button logout,start,stop;
    private Intent serviceIntent;
    private Button buttonStart,buttonStop;

    static Dashboard instance;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myref1 = database.getReference("Location");
    DatabaseReference myref2 = database.getReference("Sensors");
//    DatabaseReference myref2 = database.getReference("long");


    public static Dashboard getInstance() {
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sensor1 = (TextView) findViewById(R.id.sensor1);
        sensor2 = (TextView) findViewById(R.id.sensor2);
        sensor3 = (TextView) findViewById(R.id.sensor3);
        buttonStart = (Button) findViewById(R.id.dstart);
        buttonStop = (Button) findViewById(R.id.dstop);
        serviceIntent = new Intent(getApplicationContext(),Myservice.class);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplicationContext(),Myservice.class));

            }
        });
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(getApplicationContext(),Myservice.class));
            }
        });
        ValueEventListener listener = myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sens1 = snapshot.child("S1").getValue(int.class);
                sensor1.setText("Sensor1 "+sens1);
                int sens2 = snapshot.child("S2").getValue(int.class);
                sensor2.setText("Sensor2 "+sens2);
                int sens3 = snapshot.child("S3").getValue(int.class);
                sensor3.setText("Sensor3 "+sens3);
                if (sens1 <= 100 ){
                    sensor1.setTextColor(Color.parseColor("#FF0000"));

                }else if ( sens2 <= 100 ){
                    sensor2.setTextColor(Color.parseColor("#FF0000"));

                }
                else if (sens3 <= 100){
                    sensor3.setTextColor(Color.parseColor("#FF0000"));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        instance = this;
        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                updateLocation();


            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(Dashboard.this, "You must accept this location", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

            }
        }).check();
        textLocation_lat = (TextView) findViewById(R.id.txt_location_lat);
        textLocation_long = (TextView) findViewById(R.id.txt_location_long);


        logout = (Button) findViewById(R.id.dlogout);
        name = (TextView) findViewById(R.id.dname);

        name.setText("Welcome " + getIntent().getStringExtra("name"));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Dashboard.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void updateLocation() {
        buildLocationRequest();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
   
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());

    }

    private PendingIntent getPendingIntent() {
       Intent intent = new Intent(this,MyLocationService.class);
       intent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);
       return PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);

    }
    public void updateTextView(final String valuelat, final String valuelong){
        Dashboard.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textLocation_lat.setText(valuelat);
                Double lati = Double.valueOf(valuelat).doubleValue();
                Double longi = Double.valueOf(valuelong).doubleValue();
                myref1.child("lat").setValue(lati);
                myref1.child("long").setValue(longi);
                textLocation_long.setText(valuelong);

            }
        });

    }


}