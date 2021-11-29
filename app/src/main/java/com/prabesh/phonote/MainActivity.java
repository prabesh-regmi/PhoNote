package com.prabesh.phonote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
            Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
            startActivity(intent);
            finish();
        }
    }
