package com.example.wagba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.wagba.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(MainActivity.this, SignIn.class));
                            finish();
                        }
                    },
                    1000
            );
        } else {
            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(MainActivity.this, Homepage.class));
                            finish();
                        }
                    },
                    1000
            );
        }
    }
}