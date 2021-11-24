package com.prabesh.phonote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        DataAdapter adapter = new DataAdapter(list,this);
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
                for (DataSnapshot snapshot1 : snapshot.getChildren()){

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
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ItemListActivity.this,MainActivity.class);
        startActivity(intent);
    }
}