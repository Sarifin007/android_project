package com.example.bdchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bdchat.databinding.ActivitySingUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SingUpActivity extends AppCompatActivity {

    ActivitySingUpBinding singUpBinding;
    private FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      singUpBinding=ActivitySingUpBinding.inflate(getLayoutInflater());
      setContentView(singUpBinding.getRoot());
      auth=FirebaseAuth.getInstance();


        getSupportActionBar().hide();

    }
}