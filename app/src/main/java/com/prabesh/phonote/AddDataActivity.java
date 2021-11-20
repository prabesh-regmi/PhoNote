package com.prabesh.phonote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.prabesh.phonote.Modal.DataModel;
import com.prabesh.phonote.databinding.ActivityAddDataBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class AddDataActivity extends AppCompatActivity {
    ActivityAddDataBinding binding;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Data");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(AddDataActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Adding Data");
        progressDialog.setMessage("Please Wait ...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);




        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editDate.getText().toString().length() == 0) {
                    Toast.makeText(AddDataActivity.this, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (binding.editName.getText().toString().length() == 0) {
                    Toast.makeText(AddDataActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();

                }  else if (binding.editWeight.getText().toString().length() == 0) {
                    Toast.makeText(AddDataActivity.this, "Enter Weight", Toast.LENGTH_SHORT).show();

                } else if (binding.editRate.getText().toString().length() == 0) {
                    Toast.makeText(AddDataActivity.this, "Enter Rate", Toast.LENGTH_SHORT).show();

                } else if (notValidDateFormat(binding.editDate.getText().toString())) {
                    Toast.makeText(AddDataActivity.this, "Invalid Date Format! ", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.show();
                    DataModel data = new DataModel();
                    int weight = Integer.parseInt(binding.editWeight.getText().toString());
                    int rate = Integer.parseInt(binding.editRate.getText().toString());
                    data.setDate(binding.editDate.getText().toString());
                    data.setName(binding.editName.getText().toString());
                    data.setWeight(weight);
                    data.setRate(rate);
                    data.setSell(true);
                    data.setAddedTime(new Date().getTime());
                    data.setAddedBy(FirebaseAuth.getInstance().getUid());

                    database.getReference().child("data").push().setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();
                            Toast.makeText(AddDataActivity.this, "Data Added!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddDataActivity.this, ItemListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                }}

        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }

    public static boolean notValidDateFormat(String strDate)
    {
        /* Check if date is 'null' */
        if (strDate.trim().equals(""))
        {
            return true;
        }
        /* Date is not 'null' */
        else
        {
            /*
             * Set preferred date format,
             * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
            SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-mm-dd");
            sdfrmt.setLenient(false);
            /* Create Date object
             * parse the string into date
             */
            try
            {
                Date javaDate = sdfrmt.parse(strDate);
            }
            /* Date format is invalid */
            catch (ParseException e)
            {
                return true;
            }
            /* Return true if date format is valid */
            return false;
        }
    }



}