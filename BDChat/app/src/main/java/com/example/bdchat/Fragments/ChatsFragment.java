package com.example.bdchat.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bdchat.Adapter.UserAdapter;
import com.example.bdchat.Models.User;
import com.example.bdchat.R;
import com.example.bdchat.databinding.FragmentChatsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {


    public ChatsFragment() {
        // Required empty public constructor
    }

    FragmentChatsBinding binding;
    ArrayList<User>list=new ArrayList<>();
    FirebaseDatabase database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentChatsBinding.inflate(inflater,container,false);
        database=FirebaseDatabase.getInstance();
        UserAdapter adapter=new UserAdapter(list,getContext());
        binding.chatRecyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.chatRecyclerView.setLayoutManager(linearLayoutManager);
        database.getReference().child("User").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    User user=dataSnapshot.getValue(User.class);
                    user.setUserId(dataSnapshot.getKey());
                    list.add(user);

                }
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();

    }
}