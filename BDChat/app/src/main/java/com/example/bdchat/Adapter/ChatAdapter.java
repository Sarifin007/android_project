package com.example.bdchat.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bdchat.Models.MassageModel;
import com.example.bdchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MassageModel> massageModels;
    Context context;
    String recId;
    int SENDER_VIEW_TYPE=1;
    int RECEIVER_VIEW_TYPE=2;


    public ChatAdapter(ArrayList<MassageModel> massageModels, Context context) {
        this.massageModels = massageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MassageModel> massageModels, Context context, String recId) {
        this.massageModels = massageModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SENDER_VIEW_TYPE){
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else {
            View view=LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (massageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECEIVER_VIEW_TYPE;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MassageModel massageModel=massageModels.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete ?")
                        .setMessage("Ary you sure you want to delete this massage")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String senderRoom=FirebaseAuth.getInstance().getUid() + recId;
                                database.getReference().child("chats").child(senderRoom)
                                        .child(massageModel.getMassageId())
                                        .setValue(null);


                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                return false;
            }
        });

        if (holder.getClass() == SenderViewHolder.class)
        {

            Date date=new Date(massageModel.getTimestamp());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(" h:mm:a");
            String strDate=simpleDateFormat.format(date);
            ((SenderViewHolder)holder).senderTime.setText(strDate.toString());


        }
        else {
            ((RecieverViewHolder)holder).receiverMsg.setText(massageModel.getMassage());
            Date date=new Date(massageModel.getTimestamp());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(" h:mm:a");
            String strDate=simpleDateFormat.format(date);
            ((RecieverViewHolder)holder).receiverTime.setText(strDate.toString());

        }

    }

    @Override
    public int getItemCount() {
        return massageModels.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder{
        TextView receiverMsg,receiverTime;


        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg=itemView.findViewById(R.id.reciver_text);
            receiverTime=itemView.findViewById(R.id.recieverTime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView senderMsg,senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg=itemView.findViewById(R.id.senderText);
            senderTime=itemView.findViewById(R.id.senderTime);
        }
    }

}
