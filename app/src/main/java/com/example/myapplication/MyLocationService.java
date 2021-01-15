package com.example.myapplication;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyLocationService extends BroadcastReceiver {
    public static final String ACTION_PROCESS_UPDATE = "com.example.myapplication.UPDATE_LOCATION";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myref1 = database.getReference("Location");


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null){
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATE.equals(action)){
                LocationResult result = LocationResult.extractResult(intent);

                if(result != null){
                    Location location = result.getLastLocation();
                    String location_String_lat = new StringBuilder(""+location.getLatitude()).toString();
                    String location_String_long = new StringBuilder(""+location.getLongitude()).toString();

                    try {
                        Double lati = Double.valueOf(location_String_lat).doubleValue();
                        Double longi = Double.valueOf(location_String_long).doubleValue();
                        myref1.child("lat").setValue(lati);
                        myref1.child("long").setValue(longi);
                        Dashboard.getInstance().updateTextView(location_String_lat,location_String_long);

                    }catch (Exception ex){
                        Toast.makeText(context,location_String_lat+""+location_String_long,Toast.LENGTH_SHORT).show();

                    }

                }
            }
        }

    }
}
