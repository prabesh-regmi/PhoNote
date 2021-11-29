package com.prabesh.phonote;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.database.FirebaseDatabase;

public class PhoNote extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Intent openMain = new Intent(PhoNote.this, MainActivity.class);
        startActivity(openMain);
    }
}
