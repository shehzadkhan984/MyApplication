package com.example.myapplication;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.security.Provider;

public class Myservice extends Service {
    public  MediaPlayer player = null;
    public   MediaPlayer player1;
    public    MediaPlayer player2;
    public    MediaPlayer player3;
    public    MediaPlayer player4;
    private SoundPool soundPool;

    private  int sound1,sound2,sound3,sound4,sound5,sound6,sound7,sound8,sound9,sound10;
    private int sound1id,sound2id,sound3id,sound4id,sound5id,sound6id,sound7id,sound8id,sound9id,sound10id;
    //player1,player2,player3,player4,player5,player6,player7;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myref2 = database.getReference("Sensors");
    DatabaseReference myref3 = database.getReference("Notification");
    DatabaseReference myref4 = database.getReference("Water");
    DatabaseReference myref5 = database.getReference("ground");
    DatabaseReference myref6 = database.getReference("iot");
    private Context mContext;
    private Myservice mActivity;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void onCreate() {
        Toast.makeText(this,"Service create",Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool = new SoundPool.Builder().setMaxStreams(9).setAudioAttributes(audioAttributes).build();

        }else{
            soundPool = new SoundPool(9, AudioManager.STREAM_MUSIC,0);

        }
        sound1 = soundPool.load(this,R.raw.left,1);
        sound2 = soundPool.load(this,R.raw.front,1);
        sound3 = soundPool.load(this,R.raw.everything,1);
        sound4 = soundPool.load(this,R.raw.everything,1);
        sound5 = soundPool.load(this,R.raw.obstacle_ditch,1);
        sound6 = soundPool.load(this,R.raw.obstacle_stair,1);
        sound7 = soundPool.load(this,R.raw.emergency,1);
        sound8 = soundPool.load(this,R.raw.water,1);
        sound9 = soundPool.load(this,R.raw.stairs,1);
        sound10 = soundPool.load(this,R.raw.ditch,1);




    }
    public void onStart(Intent intent, int startid){
        Toast.makeText(this, "Service Start", Toast.LENGTH_SHORT).show();
        mContext = getApplicationContext();
        mActivity = Myservice.this;
        ValueEventListener listener = myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sens1 = snapshot.child("S1").getValue(int.class);
                int sens2 = snapshot.child("S2").getValue(int.class);
                int sens3 = snapshot.child("S3").getValue(int.class);
                if ((sens1 <= 100 || sens2 <= 100 || sens3 <= 100)){
                    myref6.child("obstacle").setValue(1);

                }else{
                    myref6.child("obstacle").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ValueEventListener listener1 = myref5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int obstacle = snapshot.child("ditch_stair").getValue(int.class);
                if (obstacle >= 40 && obstacle < 50){
                    myref6.child("ditch_stair").setValue(0);
                }else if( obstacle < 40) {
                    myref6.child("ditch_stair").setValue(1);

                }else if (obstacle > 50) {
                    myref6.child("ditch_stair").setValue(2);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ValueEventListener listener2 = myref6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int obstacle = snapshot.child("obstacle").getValue(int.class);
                int ditch = snapshot.child("ditch_stair").getValue(int.class);
                int noti = snapshot.child("noti").getValue(int.class);
                int water = snapshot.child("water").getValue(int.class);
                if (obstacle == 1 && ditch == 0 && water==0 && noti==0){
//                    FOR OBSTACLE
                    soundPool.pause(sound8id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound4id);
                    sound2id = soundPool.play(sound2,1,1,0,0,1);

                }
                else if (obstacle == 0 && ditch == 1 && water==0 && noti == 0){
//                    for ditch only
                    soundPool.pause(sound7id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    sound9id = soundPool.play(sound9,1,1,0,0,1);

                }
                else if (obstacle == 1 && ditch == 1 && water==0 && noti == 0){
//                    for obstacle and stair
                    soundPool.pause(sound7id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound3id);
                    sound6id = soundPool.play(sound6,1,1,0,0,1);

                }
                else if (obstacle == 1 && ditch == 2 && water==0 && noti == 0){
//                    for obstacle and ditch
                    soundPool.pause(sound7id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    sound5id = soundPool.play(sound5,1,1,0,0,1);

                }
                else if (obstacle == 0 && ditch == 2 && water==0 && noti == 0){
//                    for ditchn only
                    soundPool.pause(sound7id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound3id);
                    sound10id = soundPool.play(sound10,1,1,0,0,1);
                }else if (water== 1 && noti == 0 && obstacle == 0 && ditch == 0){
//                    for water
                    soundPool.pause(sound7id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound3id);
                    sound8id = soundPool.play(sound8,1,1,0,0,1);

                }else if (water== 0 && noti == 1 && obstacle == 0 && ditch == 0){
//                    for notification
                    soundPool.pause(sound8id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound3id);
                    sound7id = soundPool.play(sound7,1,1,0,0,1);
                }
                else if (water== 1 && noti == 0 && obstacle == 1 && (ditch == 1 || ditch == 2)){
//                    for water obstacle  and ditch or stair
                    soundPool.pause(sound8id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound4id);

                    sound3id = soundPool.play(sound3,1,1,0,0,1);
                }
                else if (water== 1 && noti == 1 && obstacle == 1 && (ditch == 1 || ditch == 2)){
//                    for everything
                    soundPool.pause(sound8id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound3id);

                    sound4id = soundPool.play(sound4,1,1,0,0,1);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    public void onDestroy(){
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();

        soundPool.release();
        soundPool = null;





    }
}
