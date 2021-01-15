package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
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

    private  int sound1,sound2,sound3,sound4,sound5,sound6,sound7;
    private int sound1id,sound2id,sound3id,sound4id,sound5id,sound6id,sound7id;
    //player1,player2,player3,player4,player5,player6,player7;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myref2 = database.getReference("Sensors");

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
            soundPool = new SoundPool.Builder().setMaxStreams(7).setAudioAttributes(audioAttributes).build();

        }else{
            soundPool = new SoundPool(7, AudioManager.STREAM_MUSIC,0);

        }
        sound1 = soundPool.load(this,R.raw.left,1);
        sound2 = soundPool.load(this,R.raw.front,1);
        sound3 = soundPool.load(this,R.raw.right,1);
        sound4 = soundPool.load(this,R.raw.middle_and_left,1);
        sound5 = soundPool.load(this,R.raw.left_and_right,1);
        sound6 = soundPool.load(this,R.raw.middle_and_right,1);
        sound7 = soundPool.load(this,R.raw.emergency,1);


    }
    public void onStart(Intent intent, int startid){
        Toast.makeText(this, "Service Start", Toast.LENGTH_SHORT).show();
        ValueEventListener listener = myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sens1 = snapshot.child("S1").getValue(int.class);
                int notifi = snapshot.child("noti").getValue(int.class);

                int sens2 = snapshot.child("S2").getValue(int.class);

                int sens3 = snapshot.child("S3").getValue(int.class);



                if (sens1 <= 100 && sens2 > 100 && sens3 > 100 && notifi==0){
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    sound1id = soundPool.play(sound1,1,1,0,0,1);



                }else if (sens1 > 100 && sens2 <= 100 && sens3 > 100 && notifi==0){
                    soundPool.pause(sound1id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    sound2id = soundPool.play(sound2,1,1,0,0,1);


                }
                else if (sens1 > 100 && sens2 > 100 && sens3 <= 100 && notifi==0){
                    soundPool.pause(sound2id);
                    soundPool.pause(sound1id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    sound3id = soundPool.play(sound3,1,1,0,0,1);



                }
                else if (sens1 <= 100 && sens2 <= 100 && sens3 > 100 && notifi==0){
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound1id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    sound4id = soundPool.play(sound4,1,1,0,0,1);



                }
                else if (sens1 <= 100 && sens2 > 100 && sens3 <= 100 && notifi==0){
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound1id);
                    soundPool.pause(sound6id);
                    soundPool.pause(sound7id);
                    sound5id = soundPool.play(sound5,1,1,0,0,1);



                }
                else if (sens1 > 100 && sens2 <= 100 && sens3 <= 100 && notifi==0){
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound1id);
                    soundPool.pause(sound7id);
                    sound6id = soundPool.play(sound6,1,1,0,0,1);


                }
                if (notifi == 1){
                    soundPool.pause(sound2id);
                    soundPool.pause(sound3id);
                    soundPool.pause(sound4id);
                    soundPool.pause(sound5id);
                    soundPool.pause(sound1id);
                    soundPool.pause(sound6id);
                    sound7id = soundPool.play(sound7,1,1,0,0,1);

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
