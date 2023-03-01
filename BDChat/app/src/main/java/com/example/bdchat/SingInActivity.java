package com.example.bdchat;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bdchat.Models.User;
import com.example.bdchat.databinding.ActivitySingInBinding;
import com.example.bdchat.databinding.ActivitySingUpBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SingInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;

    ActivitySingInBinding singInBinding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    GoogleSignInClient client;



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

        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();



        client= GoogleSignIn.getClient(this,googleSignInOptions);
        auth=FirebaseAuth.getInstance();



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

        singInBinding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singIn();
            }
        });

    }


    private void singIn(){
        Intent signInIntent=client.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account=task.getResult(ApiException.class);
                Log.d(TAG,"firebaseAuthWithGoogle:"+account.getId());
                firebaseAuthWithGoogleAccount(account.getIdToken());

            }
            catch (Exception e){
                Log.d(TAG,"onActivityResult:"+e.getMessage());
            }
        }
    }






    private void firebaseAuthWithGoogleAccount(String idToken) {
        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()){
                            Toast.makeText(SingInActivity.this, "Account Created..\n", Toast.LENGTH_SHORT).show();
                            FirebaseUser user=auth.getCurrentUser();

                            User users=new User();
                            users.setUserId(user.getUid());
                            users.setUserName(user.getDisplayName());
                            users.setProfilePic(user.getPhotoUrl().toString());
                            firebaseDatabase.getReference().child("User").child(user.getUid()).setValue(users);


                            Intent intent=new Intent(SingInActivity.this,MainActivity.class);

                            startActivity(intent);
                            Toast.makeText(SingInActivity.this, "Sign in with Google", Toast.LENGTH_SHORT).show();

                        }

                        else {
                            Toast.makeText(SingInActivity.this, "Existing User..\n", Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }
}