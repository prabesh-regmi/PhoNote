package com.prabesh.phonote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();


        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                } finally {
                    Intent openMain = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(openMain);
                    finish();
                }
            }
        };
        timer.start();
    }
}