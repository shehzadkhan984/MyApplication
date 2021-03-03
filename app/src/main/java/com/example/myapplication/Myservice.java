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

    private  int sound1,sound2,sound3,sound4,sound5,sound6,sound7,sound8,sound9,sound10,sound11,sound12,sound13,sound14,sound15,sound16,sound17,sound18,sound19,sound20,sound21;
    private int sound1id,sound2id,sound3id,sound4id,sound5id,sound6id,sound7id,sound8id,sound9id,sound10id,sound11id,sound12id,sound13id,sound14id,sound15id,sound16id,sound17id,sound18id,sound19id,sound20id,sound21id;
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
            soundPool = new SoundPool.Builder().setMaxStreams(22).setAudioAttributes(audioAttributes).build();

        }else{
            soundPool = new SoundPool(22, AudioManager.STREAM_MUSIC,0);

        }
        sound1 = soundPool.load(this,R.raw.left,1);
        sound2 = soundPool.load(this,R.raw.front,1);
        sound3 = soundPool.load(this,R.raw.right,1);
        sound4 = soundPool.load(this,R.raw.stairs,1);
        sound5 = soundPool.load(this,R.raw.ditch,1);

        sound6 = soundPool.load(this,R.raw.everything,1);
        sound7 = soundPool.load(this,R.raw.everything,1);
        sound8 = soundPool.load(this,R.raw.everything,1);
        sound9 = soundPool.load(this,R.raw.everything,1);
        sound10 = soundPool.load(this,R.raw.everything,1);
        sound11 = soundPool.load(this,R.raw.obstacle_stair,1);
        sound12 = soundPool.load(this,R.raw.obstacle_ditch,1);

        sound13 = soundPool.load(this,R.raw.everything,1);
        sound14 = soundPool.load(this,R.raw.everything,1);
        sound15 = soundPool.load(this,R.raw.everything,1);
        sound16 = soundPool.load(this,R.raw.everything,1);
        sound17 = soundPool.load(this,R.raw.everything,1);
        sound18 = soundPool.load(this,R.raw.everything,1);
        sound19 = soundPool.load(this,R.raw.everything,1);

        sound20 = soundPool.load(this,R.raw.water,1);
        sound21 = soundPool.load(this,R.raw.emergency,1);




    }
    public void onStart(Intent intent, int startid){
        Toast.makeText(this, "Service Start", Toast.LENGTH_SHORT).show();
        mContext = getApplicationContext();
        mActivity = Myservice.this;
        ValueEventListener listener2 = myref6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int distance1 = snapshot.child("obstacle_sensor1").getValue(int.class);
                int distance2 = snapshot.child("obstacle_sensor2").getValue(int.class);
                int distance3 = snapshot.child("obstacle_sensor3").getValue(int.class);
                int distance4 = snapshot.child("ditch_stair_sensor").getValue(int.class);
                int WaterState0 = snapshot.child("water").getValue(int.class);
                int ButtonState1 = snapshot.child("noti").getValue(int.class);
                if(distance1 == 1 && distance2 == 0 && distance3 == 0 && distance4 == 0 && WaterState0 == 0 && ButtonState1 == 0){
//                    left sensor
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);
                    sound1id = soundPool.play(sound1,1,1,0,0,1);
                }
                else if(distance1 == 0 && distance2 == 1 && distance3 == 0 && distance4 == 0 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);
                    sound2id = soundPool.play(sound2,1,1,0,0,1);

                }
                else if(distance1 == 0 && distance2 == 0 && distance3 == 1 && distance4 == 0 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);
                    sound3id = soundPool.play(sound3,1,1,0,0,1);

                }
                else if(distance1 == 0 && distance2 == 0 && distance3 == 0 && distance4 == 1 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);
                    sound4id = soundPool.play(sound4,1,1,0,0,1);

                }
                else if(distance1 == 0 && distance2 == 0 && distance3 == 0 && distance4 == 2 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);
                    sound5id = soundPool.play(sound5,1,1,0,0,1);

                }

                else if(distance1 == 1 && distance2 == 1 && distance3 == 0 && distance4 == 0 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);
                    sound6id = soundPool.play(sound6,1,1,0,0,1);
//        left and  front

                }
                else if(distance1 == 1 && distance2 == 0 && distance3 == 1 && distance4 == 0 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);
                    sound7id = soundPool.play(sound7,1,1,0,0,1);
//        left and  front

                }
                else if(distance1 == 1 && distance2 == 0 && distance3 == 0 && distance4 == 1 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);
                    sound8id = soundPool.play(sound8,1,1,0,0,1);
//        left and  front

                }
                else if(distance1 == 1 && distance2 == 0 && distance3 == 0 && distance4 == 2 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);
                    sound9id = soundPool.play(sound9,1,1,0,0,1);
//        left and  front

                }
                else if(distance1 == 0 && distance2 == 1 && distance3 == 1 && distance4 == 0 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);

                    sound10id = soundPool.play(sound10,1,1,0,0,1);
//        left and  front

                }
                else if(distance1 == 0 && distance2 == 1 && distance3 == 0 && distance4 == 1 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);

                    sound11id = soundPool.play(sound11,1,1,0,0,1);
//        left and  front

                }
                else if(distance1 == 0 && distance2 == 1 && distance3 == 0 && distance4 == 2 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);

                    sound12id = soundPool.play(sound12,1,1,0,0,1);
//        left and  front

                }
                else if(distance1 == 1 && distance2 == 1 && distance3 == 1 && distance4 == 0 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);


                    sound13id = soundPool.play(sound13,1,1,0,0,1);
//        left and  front

                }
                else if(distance1 == 1 && distance2 == 1 && distance3 == 0 && distance4 == 1 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);



                    sound14id = soundPool.play(sound14,1,1,0,0,1);
//        left and  front

                }
                else if(distance1 == 1 && distance2 == 1 && distance3 == 0 && distance4 == 2 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);



                    sound15id = soundPool.play(sound15,1,1,0,0,1);
//        left and  front

                }
                else if(distance1 == 0 && distance2 == 1 && distance3 == 1 && distance4 == 1 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);



                    sound16id = soundPool.play(sound16,1,1,0,0,1);
//        left and  front

                }
                else if(distance1 == 0 && distance2 == 1 && distance3 == 1 && distance4 == 2 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);



                    sound17id = soundPool.play(sound17,1,1,0,0,1);
//        left and  front

                }
                else if(distance1 == 1 && distance2 == 1 && distance3 == 1 && distance4 == 1 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);



                    sound18id = soundPool.play(sound18,1,1,0,0,1);
//        left and  front

                }
                else if(distance1 == 1 && distance2 == 1 && distance3 == 1 && distance4 == 2 && WaterState0 == 0 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound20id);
                    soundPool.pause(sound21id);



                    sound19id = soundPool.play(sound19,1,1,0,0,1);
//        left and  front

                }else if(WaterState0 == 1 && ButtonState1 == 0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound21id);
                    sound20id = soundPool.play(sound20,1,1,0,0,1);

                }else if(ButtonState1 == 1){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    soundPool.pause(sound8id);
                    soundPool.pause(sound9id);
                    soundPool.pause(sound10id);
                    soundPool.pause(sound11id);
                    soundPool.pause(sound12id);
                    soundPool.pause(sound13id);
                    soundPool.pause(sound14id);
                    soundPool.pause(sound15id);
                    soundPool.pause(sound16id);
                    soundPool.pause(sound17id);
                    soundPool.pause(sound18id);
                    soundPool.pause(sound19id);
                    soundPool.pause(sound20id);
                    sound21id = soundPool.play(sound21,1,1,0,0,1);

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
