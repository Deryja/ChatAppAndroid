package com.example.chattingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.chattingapp.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;


// Steg 9 --> Vi begynner med å legge til "send" ikon i drawable

//Alt under er steg 9

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    String recieverId;
    DatabaseReference databaseReferenceSender, databaseReferenceReciever;
    String senderRoom, recieverRoom;


    MessageAdapter messageAdapter; //Steg 12


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

recieverId = getIntent().getStringExtra("id");

senderRoom = FirebaseAuth.getInstance().getUid()+recieverId;
recieverRoom = recieverId+FirebaseAuth.getInstance().getUid();



//Steg 12 mellom
messageAdapter = new MessageAdapter(this);
binding.recycler.setAdapter(messageAdapter);
binding.recycler.setLayoutManager(new LinearLayoutManager(this));
//Steg 12 mellom





databaseReferenceSender = FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
databaseReferenceReciever = FirebaseDatabase.getInstance().getReference("chats").child(recieverRoom);


databaseReferenceSender.addValueEventListener(new ValueEventListener() {



    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) { //Nå går jeg til steg 10 --> lager ny java class MessageModel

     messageAdapter.clear(); //Steg 12
        for (DataSnapshot dataSnapshot:snapshot.getChildren()){

        //Steg 11-->
        MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
//Nå går vi til layout og lager linerlayout fil som heter message_row
//Etter det lager vi MessageAdapter class ved å kopiere og paste UserAdapter, for så å endre variabelen innevendig til samme navn

            messageAdapter.add(messageModel); //Steg 12

    }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {


    }
});


//Steg 12 under
        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.messageEd.getText().toString();
                if (message.trim().length()>0){
                    sendMessage(message);

                }
            }
        });



    }

    private void sendMessage(String message) {
        String messageId = UUID.randomUUID().toString();
        MessageModel messageModel = new MessageModel(messageId, FirebaseAuth.getInstance().getUid(),message);


        messageAdapter.add(messageModel);

        databaseReferenceSender
                .child(messageId)
                .setValue(messageModel);
        databaseReferenceReciever
                .child(messageId)
                .setValue(messageModel);
    }
}

//Nå går vi til UserAdapter til onbiendViewHolder delen for steg 13