package com.example.bdchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bdchat.databinding.ActivitySingInBinding;
import com.example.bdchat.databinding.ActivitySingUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
        progressDialog.setMessage("Please wait\nValidating in Process");

        singInBinding.btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!singInBinding.txrEmail.getText().toString().isEmpty() && !singInBinding.txrPassword.getText().toString().isEmpty()){

                    progressDialog.show();
                    auth.signInWithEmailAndPassword(singInBinding.txrEmail.getText().toString(),singInBinding.txrPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    progressDialog.dismiss();
                                    if (task.isSuccessful()){
                                        Intent intent=new Intent(SingInActivity.this,MainActivity.class);
                                        startActivity(intent);



                                    }
                                    else {
                                        Toast.makeText(SingInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }



                                }
                            });
                }
                else {
                    Toast.makeText(SingInActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                }

            }
        });
        if(auth.getCurrentUser()!=null){
            Intent intent =new Intent(SingInActivity.this,MainActivity.class);
            startActivity(intent);
        }
        singInBinding.txtClickSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SingInActivity.this,SingUpActivity.class);
                startActivity(intent);
            }
        });



    }
}