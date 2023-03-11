package com.example.bdchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.bdchat.Adapter.ChatAdapter;
import com.example.bdchat.Models.MassageModel;
import com.example.bdchat.databinding.ActivityChatDetailsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

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



        final ArrayList<MassageModel>massageModels=new ArrayList<>();
        final ChatAdapter chatAdapter=new ChatAdapter(massageModels,this,recieveId);
        binding.chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        final String senderRoom=senderId+recieveId;
        final String recieverRoom=recieveId+senderId;


        database.getReference().child("chats")
                        .child(senderRoom)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        massageModels.clear();
                                        for (DataSnapshot snapshot1:snapshot.getChildren())
                                        {
                                            MassageModel model=snapshot1.getValue(MassageModel.class);
                                            model.setMassageId(snapshot1.getKey());
                                            massageModels.add(model);
                                        }
                                        chatAdapter.notifyDataSetChanged();


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=binding.enterMassges.getText().toString();
                final MassageModel model=new MassageModel(senderId,message);
                model.setTimestamp(new Date().getTime());
                binding.enterMassges.setText("");
                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("chats")
                                        .child(recieverRoom)
                                        .push()
                                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {


                                            }
                                        });

                            }
                        });

            }
        });


    }
}