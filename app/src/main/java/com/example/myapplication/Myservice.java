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
    public  MediaPlayer player;
    public   MediaPlayer player1;
    public    MediaPlayer player2;
    public    MediaPlayer player3;
    public    MediaPlayer player4;
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
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
//            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                    .build();
//            soundPool = new SoundPool.Builder().setMaxStreams(6).setAudioAttributes(audioAttributes).build();
//
//        }else{
//            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC,0);
//
//        }
//        sound1 = soundPool.load(this,R.raw.left,1);
//        sound2 = soundPool.load(this,R.raw.front,1);
//        sound3 = soundPool.load(this,R.raw.emergency,1);


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



                if (sens1 <= 100 && sens2 > 100 && sens3 > 100 && notifi == 0){

                    player = MediaPlayer.create(getApplicationContext(),R.raw.left);
                    player.setLooping(false);

                    if (player != null && player.isPlaying()) {

                        player.reset();
                        player.start();
                    }else{
                        player.start();

                    }




//                        if (player1.isPlaying() && player1 != null){
//                            player1.pause();
//                            player2.start();
//                        }else if (player3.isPlaying() && player3 != null){
//                            player3.pause();
//                            player2.start();
//                        }else if (player4.isPlaying() && player4 != null){
//                            player4.pause();
//                            player2.start();
//                        }else if (player5.isPlaying() && player5 != null){
//                            player5.pause();
//                            player2.start();
//                        }else if (player6.isPlaying() && player6 != null){
//                            player6.pause();
//                            player2.start();
//                        }else if (player7.isPlaying() && player7 != null){
//                            player7.pause();
//                            player2.start();
//                        }else {
//                            player2.start();
//                        }


                }else if (sens1 > 100 && sens2 <= 100 && sens3 > 100  && notifi==0){

                    player1 = MediaPlayer.create(getApplicationContext(),R.raw.front);
                    player1.setLooping(false);
                    if (player.isPlaying() && player != null){
                        player.reset();
                        player1.start();
                    }else {
                        player1.start();
                    }

//                    if (player != null && player.isPlaying()) {
//
//                        player.pause();
//                        player.stop();
//                    }else{
//                        player1 = MediaPlayer.create(getApplicationContext(),R.raw.front);
//                        player1.setLooping(false);
//                        player1.start();
//
//                    }

//                    if (player.isPlaying() && player != null){
//                        player.pause();
//
//                        player3.start();
//                    }else if (player2.isPlaying() && player2 != null){
//                        player2.pause();
//                        player3.start();
//                    }else if (player4.isPlaying() && player4 != null){
//                        player4.pause();
//                        player3.start();
//                    }else if (player5.isPlaying() && player5 != null){
//                        player5.pause();
//                        player3.start();
//                    }else if (player6.isPlaying() && player6 != null){
//                        player6.pause();
//                        player3.start();
//                    }else if (player7.isPlaying() && player7 != null){
//                        player7.pause();
//                        player3.start();
//                    }else{
//                        player3.start();
//                    }
                }
                else if (sens1 > 100 && sens2 > 100 && sens3 <= 100 && notifi==0){

                    player2= MediaPlayer.create(getApplicationContext(),R.raw.right);
                    player2.setLooping(false);
                    if ((player.isPlaying() && player != null) || (player1.isPlaying() && player1 != null)){
                        player.reset();
                        player1.reset();
                        player2.start();
                    }else {
                        player2.start();
                    }
//                    if (player.isPlaying() && player != null){
//                        player.pause();
//
//                        player4.start();
//                    }else if (player2.isPlaying() && player2 != null){
//                        player2.pause();
//                        player4.start();
//                    }else if (player3.isPlaying() && player3 != null){
//                        player3.pause();
//                        player4.start();
//                    }else if (player5.isPlaying() && player5 != null){
//                        player5.pause();
//                        player4.start();
//                    }else if (player6.isPlaying() && player6 != null){
//                        player6.pause();
//                        player4.start();
//                    }else if (player7.isPlaying() && player7 != null){
//                        player7.pause();
//                        player4.start();
//                    }else{
//                        player4.start();
//                    }

                }
                else if (sens1 <= 100 && sens2 <= 100 && sens3 > 100 && notifi==0){
                    if (player2 != null && player2.isPlaying()) {

                        player2.pause();
                        player2.stop();
                    }
                    player3 = MediaPlayer.create(getApplicationContext(),R.raw.middle_and_left);
                    player3.setLooping(false);
                    player3.start();
//                    if (player.isPlaying() && player != null){
//                        player.pause();
//
//                        player5.start();
//                    }else if (player2.isPlaying() && player2 != null){
//                        player2.pause();
//                        player5.start();
//                    }else if (player3.isPlaying() && player3 != null){
//                        player3.pause();
//                        player5.start();
//                    }else if (player4.isPlaying() && player4 != null){
//                        player4.pause();
//                        player5.start();
//                    }else if (player6.isPlaying() && player6 != null){
//                        player6.pause();
//                        player5.start();
//                    }else if (player7.isPlaying() && player7 != null){
//                        player7.pause();
//                        player5.start();
//                    }else{
//                        player5.start();
//                    }

                }
                else if (sens1 <= 100 && sens2 > 100 && sens3 <= 100 && notifi==0){
                    if (player3 != null && player3.isPlaying()) {

                        player3.pause();
                        player3.stop();
                    }
                    player4 = MediaPlayer.create(getApplicationContext(),R.raw.left_and_right);
                    player4.setLooping(false);
                    player4.start();

//                    if (player.isPlaying() && player != null){
//                        player.pause();
//
//                        player6.start();
//                    }else if (player2.isPlaying() && player2 != null){
//                        player2.pause();
//                        player6.start();
//                    }else if (player3.isPlaying() && player3 != null){
//                        player3.pause();
//                        player6.start();
//                    }else if (player4.isPlaying() && player4 != null){
//                        player4.pause();
//                        player6.start();
//                    }else if (player5.isPlaying() && player5 != null){
//                        player5.pause();
//                        player6.start();
//                    }else if (player7.isPlaying() && player7 != null){
//                        player7.pause();
//                        player6.start();
//                    }else{
//                        player6.start();
//                    }

                }
//                else if (sens1 > 100 && sens2 <= 100 && sens3 <= 100 && notifi==0){
//
//
//                    player = MediaPlayer.create(getApplicationContext(),R.raw.middle_and_right);
//                    player.setLooping(false);
//                    player.start();
////                    if (player.isPlaying() && player != null){
////                        player.pause();
////
////                        player7.start();
////                    }else if (player2.isPlaying() && player2 != null){
////                        player2.pause();
////                        player7.start();
////                    }else if (player3.isPlaying() && player3 != null){
////                        player3.pause();
////                        player7.start();
////                    }else if (player4.isPlaying() && player4 != null){
////                        player4.pause();
////                        player7.start();
////                    }else if (player5.isPlaying() && player5 != null){
////                        player5.pause();
////                        player7.start();
////                    }else if (player6.isPlaying() && player6 != null){
////                        player6.pause();
////                        player7.start();
////                    }else{
////                        player7.start();
////                    }
//                }
                if (notifi == 1){
                    player1 = MediaPlayer.create(getApplicationContext(),R.raw.emergency);
                    player1.setLooping(false);
                    if (player2 != null && player2.isPlaying()){
                        player2.pause();
                        player1.start();

                    }else{
                        player1.start();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onDestroy(){
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();

        player.stop();
        player1.stop();




    }
}
