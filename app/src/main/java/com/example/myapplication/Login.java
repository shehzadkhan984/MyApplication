package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText Phoneno, password;
    CheckBox remember;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if (checkbox.equals("true")){
//            Intent intent = new Intent(Login.this,NewAct.this);
//            startActivity(intent);

        }else if (checkbox.equals("false")){
            Toast.makeText(this, "Please Sign in", Toast.LENGTH_SHORT).show();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    SharedPreferences preference = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preference.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(Login.this,"checked", Toast.LENGTH_SHORT).show();

                }else if (!compoundButton.isChecked()){
                    SharedPreferences preference = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preference.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(Login.this,"Unchecked", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}