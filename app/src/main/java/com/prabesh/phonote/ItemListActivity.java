package com.prabesh.phonote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabesh.phonote.Adapter.DataAdapter;
import com.prabesh.phonote.Modal.DataModel;
import com.prabesh.phonote.databinding.ActivityItemListBinding;

import java.util.ArrayList;
import java.util.Collections;

public class ItemListActivity extends AppCompatActivity {
    ActivityItemListBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Data");
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(ItemListActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setMessage("Please Wait ...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        ArrayList<DataModel> list = new ArrayList<>();
        DataAdapter adapter = new DataAdapter(list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.datalistRv.setLayoutManager(linearLayoutManager);
        binding.datalistRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.datalistRv.setNestedScrollingEnabled(false);
        binding.datalistRv.setAdapter(adapter);


        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("data_added_by_user")
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                            DataModel model = snapshot1.getValue(DataModel.class);
                            assert model != null;
                            if (model.getAddedBy().equals(FirebaseAuth.getInstance().getUid()))
                                list.add(model);

                        }
                        if (list.isEmpty())
                            binding.nothingToShow.setVisibility(View.VISIBLE);
                        else
                            binding.nothingToShow.setVisibility(View.GONE);
                        Collections.reverse(list);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Intent intent = new Intent(ItemListActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("data_added_by_user").keepSynced(true);
        binding.addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemListActivity.this, AddDataActivity.class);
                intent.putExtra("activity", "Main");
                startActivity(intent);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logOut:
                auth.signOut();
                Intent intent = new Intent(ItemListActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private static long back_pressed;

    @Override
    public void onBackPressed() {

        /** If you want to take confirmation then display Alert here...**/
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finishAffinity();
            finish(); /** otherwise directly exit from here...**/
        } else
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();

        back_pressed = System.currentTimeMillis();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(ItemListActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}