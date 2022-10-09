package com.example.chattingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chattingapp.databinding.ActivityAuthenticationBinding;
import com.example.chattingapp.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthenticationActivity extends AppCompatActivity {

    //Fortsettelse steg 1
    ActivityAuthenticationBinding binding;

    String name, email, password;

    DatabaseReference databaseReference; //Steg 4

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference("users"); //Steg 4

        //Steg 2
      binding.login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {


email = binding.email.getText().toString();
password = binding.password.getText().toString();

login();
          }
      });


        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                name = binding.name.getText().toString();
                email = binding.email.getText().toString();
                password = binding.password.getText().toString();

                signUp();
            }
        });




    }



    @Override
    protected void onStart(){
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(AuthenticationActivity.this,MainActivity.class));
            finish();

        }
    }



    //Steg 3 - lage firebase autentisering
    private void login(){
        FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(email.trim(), password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(AuthenticationActivity.this,MainActivity.class));
                        finish();
                    }
                });
    }




    private void signUp(){
        FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(email.trim(), password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>(){


                    @Override
                    public void onSuccess(AuthResult authResult) {

 UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
 FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    firebaseUser.updateProfile(userProfileChangeRequest);
 UserModel userModel = new UserModel(FirebaseAuth.getInstance().getUid(), name, email, password); //Steg 4


databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(userModel); //Steg 4 --> Nå lager ny java class UserModel (POJ)
        startActivity(new Intent(AuthenticationActivity.this,MainActivity.class));
          finish();
                    }
                });
    }



}