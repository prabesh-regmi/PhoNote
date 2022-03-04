package com.prabesh.phonote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();

//
//        Thread timer = new Thread(){
//            public void run(){
//                try{
//                    sleep(2000);
//                }
//                catch(InterruptedException e){
//                    e.printStackTrace();
//                } finally {
//                    FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//                    Intent openMain = new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(openMain);
//                    finish();
//                }
//            }
//        };
//        timer.start();
        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Menu-Activity. */
            Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
            SplashActivity.this.startActivity(mainIntent);
            SplashActivity.this.finish();
        }, 2000);

    }
}