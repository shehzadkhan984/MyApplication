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
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class Dashboard extends AppCompatActivity {
    TextView name, textLocation_lat,textLocation_long,sensor1,sensor2,sensor3,tempra,humidity,ditchess,sensorss;
    Button logout,stop;
    private Intent serviceIntent;
    private Button buttonStop;

    static Dashboard instance;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myref1 = database.getReference("Location");
    DatabaseReference myref2 = database.getReference("Sensors");
    DatabaseReference myref3 = database.getReference("weather");
    DatabaseReference myref4= database.getReference("User Data");
    DatabaseReference myref5= database.getReference("iot");
    DatabaseReference myref6= database.getReference("ground");

    public static Dashboard getInstance() {
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tempra = (TextView) findViewById(R.id.temp);
        humidity = (TextView) findViewById(R.id.humidity);


        buttonStop = (Button) findViewById(R.id.dstop);
        serviceIntent = new Intent(getApplicationContext(),Myservice.class);
//        buttonStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(getApplicationContext(),Myservice.class));
            }
        });
//        ValueEventListener listener2 = myref2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int sens1 = snapshot.child("S1").getValue(int.class);
//                sensor1.setText("Sensor1 "+sens1);
//                int sens2 = snapshot.child("S2").getValue(int.class);
//                sensor2.setText("Sensor2 "+sens2);
//                int sens3 = snapshot.child("S3").getValue(int.class);
//                sensor3.setText("Sensor3 "+sens3);
//                if (sens1 <= 100 ){
//                    sensor1.setTextColor(Color.parseColor("#FF0000"));
//                }else if ( sens2 <= 100 ){
//                    sensor2.setTextColor(Color.parseColor("#FF0000"));
//                }
//                else if (sens3 <= 100){
//                    sensor3.setTextColor(Color.parseColor("#FF0000"));
//                }
//                if (sens1 <= 100 && sens2 > 100 && sens3 > 100){
//                    sensor1.setTextColor(Color.parseColor("#FF0000"));
//                }else if (sens1 > 100 && sens2 <= 100 && sens3 > 100){
//                    sensor2.setTextColor(Color.parseColor("#FF0000"));
//                }
//                else if (sens1 > 100 && sens2 > 100 && sens3 <= 100){
//                    sensor3.setTextColor(Color.parseColor("#FF0000"));
//                }
//                else if (sens1 <= 100 && sens2 <= 100 && sens3 > 100){
//                    sensor1.setTextColor(Color.parseColor("#FF0000"));
//                    sensor2.setTextColor(Color.parseColor("#FF0000"));
//                }
//                else if (sens1 <= 100 && sens2 > 100 && sens3 <= 100){
//                    sensor1.setTextColor(Color.parseColor("#FF0000"));
//                    sensor3.setTextColor(Color.parseColor("#FF0000"));
//                }
//                else if (sens1 > 100 && sens2 <= 100 && sens3 <= 100){
//                    sensor2.setTextColor(Color.parseColor("#FF0000"));
//                    sensor3.setTextColor(Color.parseColor("#FF0000"));
//                }else if (sens1 <= 100 && sens2 <= 100 && sens3 <= 100){
//                    sensor1.setTextColor(Color.parseColor("#FF0000"));
//                    sensor2.setTextColor(Color.parseColor("#FF0000"));
//                    sensor3.setTextColor(Color.parseColor("#FF0000"));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        ValueEventListener listner3 = myref6.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int sens4 = snapshot.child("ditch_stair").getValue(int.class);
//                if (sens4 > 50){
//                    ditchess.setText("Ditch "+sens4);
//
//                }else if (sens4 < 40){
//                    sensorss.setText("Stair  "+sens4);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
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

        name.setText("Welcome to dashboard " + getIntent().getStringExtra("name"));

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
                final Double lati = Double.valueOf(valuelat).doubleValue();
                final Double longi = Double.valueOf(valuelong).doubleValue();
//                myref1.child("lat").setValue(lati);
//                myref1.child("long").setValue(longi);
                textLocation_long.setText(valuelong);
// sending message to family member
                ValueEventListener listener1 = myref5.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final int notifi = snapshot.child("noti").getValue(int.class);
                        ValueEventListener listener2 = myref4.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                    UserData user = snapshot1.getValue(UserData.class);
                                    String fmember1 = user.fmember;
                                    if (fmember1.equals("family Member")){
                                        String phoneno = user.phoneno;
                                        String name = user.fname;
                                        if (notifi==1){
                                            String part1="hello "+name+", its an emergency. to trace my location kindly click the link below\n";
                                            String part2 ="https://www.google.com/maps/search/?api=1&query="+lati+","+longi;
                                            String merged = part1+part2;
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                                if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                                                    try {
                                                        SmsManager smsManager= SmsManager.getDefault();
                                                        smsManager.sendTextMessage(phoneno,null,merged,null,null);
                                                        Toast.makeText(Dashboard.this,"message sent",Toast.LENGTH_SHORT).show();
                                                    }catch (Exception e){
                                                        e.printStackTrace();
                                                        Toast.makeText(Dashboard.this,"failed to send message",Toast.LENGTH_SHORT).show();
                                                    }
                                                }else{
                                                    requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                                                }
                                            }
                                            myref5.child("noti").setValue(0);
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
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(getApplicationContext(),Myservice.class));
        find_weather();
//        startService(new Intent(getApplicationContext(),Myservice.class));
    }
    public void find_weather(){
        ValueEventListener listener = myref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double latitude = snapshot.child("lat").getValue(Double.class);
                Double longitude = snapshot.child("long").getValue(Double.class);
                String apikey = "4a500557fb7eeb2fdfa2e59eb50e3e4f";
                String url = "https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=4a500557fb7eeb2fdfa2e59eb50e3e4f";
                JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject main_obj = response.getJSONObject("main");
                            JSONArray array = response.getJSONArray("weather");
                            JSONObject object = array.getJSONObject(0);
                            DecimalFormat form =  new DecimalFormat("#.##");
                            String temp = String.valueOf(main_obj.getDouble("temp")-273.15);
                            String feel = String.valueOf(main_obj.getDouble("feels_like")-273.15);
                            String humi = main_obj.getString("humidity");
                            Double temprat = Double.valueOf(temp).doubleValue();
                            Double feel_like = Double.valueOf(feel).doubleValue();
                            Integer humidit = Integer.valueOf(humi);
                            myref3.child("temp").setValue(temprat);
                            myref3.child("humidity").setValue(humidit);
                            myref3.child("feel_like").setValue(form.format(feel_like));
                            humidity.setText("humidity" +humi);
                            tempra.setText("temp "+temp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                RequestQueue queue = Volley.newRequestQueue(Dashboard.this);
                queue.add(jor);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}