package com.example.bdchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bdchat.databinding.ActivityChatDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ChatDetailsActivity extends AppCompatActivity {

    ActivityChatDetailsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityChatDetailsBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());
       getSupportActionBar().hide();

       database=FirebaseDatabase.getInstance();
       auth=FirebaseAuth.getInstance();

       final  String senderId=auth.getUid();
       String recieveId=getIntent().getStringExtra("userId");
       String userName=getIntent().getStringExtra("userName");
       String profilePic=getIntent().getStringExtra("profilePic");
       binding.userName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.avatar).into(binding.profileImage);

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChatDetailsActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }
}