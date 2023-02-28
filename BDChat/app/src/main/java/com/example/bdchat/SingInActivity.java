package com.example.bdchat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.example.bdchat.databinding.ActivitySingInBinding;
import com.example.bdchat.databinding.ActivitySingUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SingInActivity extends AppCompatActivity {

    ActivitySingInBinding singInBinding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        singInBinding=ActivitySingInBinding.inflate(getLayoutInflater());
        setContentView(singInBinding.getRoot());


        getSupportActionBar().hide();


        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        progressDialog =new ProgressDialog(SingInActivity.this);
        progressDialog.setTitle("Log In");
        progressDialog.setMessage("Please wait,Validating in Process");

        singInBinding.btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
            }
        });


    }
}