package com.prabesh.phonote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

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
                    FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                    Intent openMain = new Intent(SplashActivity.this, ItemListActivity.class);
                    startActivity(openMain);
                    finish();
                }
            }
        };
        timer.start();
    }
}