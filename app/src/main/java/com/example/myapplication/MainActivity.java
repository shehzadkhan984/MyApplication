package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private Button SignupButton;
    EditText email, password;
    CheckBox remember, family;
    Button login;
    ProgressBar progressbar2;
    private SoundPool soundPool;

    private  int sound1,sound2;
    private int sound1id,sound2id;

    FirebaseAuth fAuth;

    FirebaseDatabase firebasedatabase;
    private FirebaseAuth.AuthStateListener mauthlistner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool = new SoundPool.Builder().setMaxStreams(22).setAudioAttributes(audioAttributes).build();

        }else{
            soundPool = new SoundPool(22, AudioManager.STREAM_MUSIC,0);

        }
        sound1 = soundPool.load(this,R.raw.login,1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText) findViewById(R.id.Email);
        password= (EditText) findViewById(R.id.PassWord);
//        remember = (CheckBox) findViewById(R.id.rememberme);
        login = (Button) findViewById(R.id.Login);
        progressbar2 = (ProgressBar) findViewById(R.id.progressBar2);
//        family = (CheckBox) findViewById(R.id.Family);

        SignupButton = (Button) findViewById(R.id.SignUp);
        fAuth = FirebaseAuth.getInstance();
        firebasedatabase = FirebaseDatabase.getInstance();

        mauthlistner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mfirebaseuser = firebaseAuth.getCurrentUser();
                if (mfirebaseuser!=null){
                    movetoactivity(mfirebaseuser);
                }else{
                    Toast.makeText(MainActivity.this,"please login",Toast.LENGTH_SHORT).show();
                }

            }};
//        mauthlistner = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//            }
//        };
//checkinma user alread login or not




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email_id =  email.getText().toString().trim();
                String PASSWORD = password.getText().toString().trim();
                if (TextUtils.isEmpty(Email_id)){
                    email.setError("Email is required");
                    return;
                }if (TextUtils.isEmpty(PASSWORD)){
                    password.setError("Password is required");
                }

                // authenticate the user
                fAuth.signInWithEmailAndPassword(Email_id, PASSWORD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                            movetoactivity(task.getResult().getUser());
                        }else{
                            Toast.makeText(MainActivity.this,"Error Occured"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }



                    }
                });


                progressbar2.setVisibility(View.VISIBLE);
                // authenticate the user




            }
        });

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSignup();
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        fAuth.addAuthStateListener(mauthlistner);
    }
//    private void movetoactivity(FirebaseUser mfirebaseuser){
//        firebasedatabase.getReference().child(mfirebaseuser.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UserData userData = snapshot.getValue(UserData.class);
//                String name = userData.getFname();
//                Intent i = new Intent(getApplicationContext(),Family.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                i.putExtra("name",name);
//                startActivity(i);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

        private void movetoactivity(FirebaseUser mfirebaseuser) {
            firebasedatabase.getReference("User Data").child(mfirebaseuser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserData userData = snapshot.getValue(UserData.class);
                    String name = userData.getFname();
                    String fmem = userData.getFmember();

                    if (fmem.equals("family Member")){
                        Intent i = new Intent(getApplicationContext(),Family.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.putExtra("name",name);

                        startActivity(i);



                    }else {
                        soundPool.play(sound1,1,1,0,0,1);
                        Intent i = new Intent(getApplicationContext(),Dashboard.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.putExtra("name",name);


                        startActivity(i);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    public void OpenSignup(){
        Intent Signupintet = new Intent(this, Signup.class);
        startActivity(Signupintet);
    }
}