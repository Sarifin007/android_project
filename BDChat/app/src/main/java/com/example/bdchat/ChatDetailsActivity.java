package com.example.bdchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bdchat.databinding.ActivityChatDetailsBinding;

public class ChatDetailsActivity extends AppCompatActivity {

    ActivityChatDetailsBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityChatDetailsBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());
       getSupportActionBar().hide();
    }
}