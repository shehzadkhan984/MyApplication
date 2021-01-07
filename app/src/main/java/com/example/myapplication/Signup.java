package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText Name, age, phoneno, password,email;
    RadioGroup gender;
    RadioButton male, female, other;
    Button signup;
    FirebaseAuth fauth;
    DatabaseReference databasereference;

    CheckBox chckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        gender = (RadioGroup) findViewById(R.id.gender);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        other = (RadioButton) findViewById(R.id.other);
        Name = (EditText) findViewById(R.id.fullname);
        age = (EditText) findViewById(R.id.age);
        phoneno = (EditText) findViewById(R.id.phoneno);
        password = (EditText) findViewById(R.id.password);
        signup = (Button) findViewById(R.id.signup);
        email = (EditText) findViewById(R.id.email);
        fauth = FirebaseAuth.getInstance();
        chckbox = (CheckBox) findViewById(R.id.familymember);
        databasereference = FirebaseDatabase.getInstance().getReference("User Data");

//        if (fauth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            finish();
//
//        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String gender_result= (male.isChecked())?"Male":(female.isChecked())?"Female":(other.isChecked())?"Other":"";
//                String family_mem = "";
                final String family_mem = (chckbox.isChecked())?"family Member":(!chckbox.isChecked())?"Visuall imapaired":"";
//                Toast.makeText(getApplicationContext(), family_mem, Toast.LENGTH_SHORT).show();
                final String Email = email.getText().toString().trim();
                final String phoneNo = phoneno.getText().toString().trim();
                final String Age = age.getText().toString().trim();
                String passWord = password.getText().toString().trim();
                final String fname = Name.getText().toString().trim();
                final String PhoneNo = phoneno.getText().toString().trim();
                if (TextUtils.isEmpty(Email)){
                    email.setError("Phone No is Required");
                    return;
                }if (TextUtils.isEmpty(passWord)){
                    password.setError("Password is required");
                    return;
                }if (passWord.length() < 6){
                    password.setError("Password must be >= 6 characters ");
                }


                // Registering the user in firebase
                fauth.createUserWithEmailAndPassword(Email, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            UserData information = new UserData(

                                    fname,
                                    Email,
                                    gender_result,
                                    family_mem,
                                    Age,
                                    phoneNo

                            );
                            FirebaseDatabase.getInstance().getReference("User Data").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Signup.this,"Register Successfully",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                }
                            });


                        }else {
                                        Toast.makeText(Signup.this,"Error Occured"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });





    }
}