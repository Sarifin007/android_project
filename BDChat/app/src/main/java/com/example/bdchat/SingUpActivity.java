package com.example.bdchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bdchat.Models.User;
import com.example.bdchat.databinding.ActivitySingUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SingUpActivity extends AppCompatActivity {

    ActivitySingUpBinding singUpBinding;
    private FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      singUpBinding=ActivitySingUpBinding.inflate(getLayoutInflater());
      setContentView(singUpBinding.getRoot());
      auth=FirebaseAuth.getInstance();
        FirebaseDatabase datbase = FirebaseDatabase.getInstance();


        getSupportActionBar().hide();
        progressDialog=new ProgressDialog(SingUpActivity.this);
        progressDialog.setTitle("Create Account");
        progressDialog.setMessage("We're Creating your Account");
        singUpBinding.btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!singUpBinding.textUsername.getText().toString().isEmpty() && !singUpBinding.textEmail.getText().toString().isEmpty() && !singUpBinding.textPassword.getText().toString().isEmpty()){

                    progressDialog.show();
                    auth.createUserWithEmailAndPassword(singUpBinding.textEmail.getText().toString(), singUpBinding.textPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()){


                                User user=new User(singUpBinding.textUsername.getText().toString(), singUpBinding.textEmail.getText().toString(), singUpBinding.textPassword.getText().toString());
                                String id=task.getResult().getUser().getUid();
                                datbase.getReference().child("User").child(id).setValue(user);
                                Toast.makeText(SingUpActivity.this, "Sing Up Successful", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(SingUpActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

                else {
                    Toast.makeText(SingUpActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }


            }
        });
        singUpBinding.textAlreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SingUpActivity.this,SingInActivity.class);
                startActivity(intent);
            }
        });


    }
}