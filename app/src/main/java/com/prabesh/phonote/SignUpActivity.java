package com.prabesh.phonote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.prabesh.phonote.Modal.Users;
import com.prabesh.phonote.databinding.ActivitySignUpBinding;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding.logInHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Create Account");
        progressDialog.setMessage("Creating Account");

        binding.signupBtn.setOnClickListener(v -> {
            if (binding.fullName.getText().toString().length() == 0) {
                Toast.makeText(SignUpActivity.this, "Enter Full Name", Toast.LENGTH_SHORT).show();

            } else if (binding.signUpEmail.getText().toString().length() == 0) {
                Toast.makeText(SignUpActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();


            } else if (binding.signUpPassword.getText().toString().length() == 0) {
                Toast.makeText(SignUpActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();


            } else {
                progressDialog.show();
                auth.createUserWithEmailAndPassword
                        (binding.signUpEmail.getText().toString(), binding.signUpPassword.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {

                                    Users user = new Users(binding.fullName.getText().toString(), binding.signUpEmail.getText().toString(), binding.signUpPassword.getText().toString());
                                    String id = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid();
                                    database.getReference().child("Users").child(id).child("User Info").setValue(user);

                                    Toast.makeText(SignUpActivity.this, "Your Account has been successfully created!", Toast.LENGTH_LONG).show();
                                    auth.signOut();
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                } else {

                                    Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }


        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}