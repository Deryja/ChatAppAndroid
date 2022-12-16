package com.example.chattingapp;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Dette er steg 6
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{ //Står at problemet likker her også
    private Context context;
    private List<UserModel> userModelList;

    public UserAdapter(Context context){
        this.context = context;
        userModelList = new ArrayList<>();
    }

    public void add(UserModel userModel){
        userModelList.add(userModel);
        notifyDataSetChanged();
    }
    public void clear(){
        userModelList.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {




        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (userModelList.isEmpty() || position >= userModelList.size()) {
            Log.e("UserAdapter", "User model list is empty or position is out of bounds");
            return;
        }




        UserModel userModel = userModelList.get(position);



        // Use the Java Optional class to prevent a NullPointerException
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Optional.ofNullable(userModel)
                    .map(UserModel::getUserName)
                    .ifPresent(name -> holder.name.setText(name));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Optional.ofNullable(userModel)
                    .map(UserModel::getUserEmail)
                    .ifPresent(email -> holder.email.setText(email));
        }







        // Det som er mellom er steg 13
        holder.itemView.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {

         //Dette er også fra chatGPT
         if (context == null) {
             Log.e("UserAdapter", "Context is null");
             return;
         }



         if (userModel != null) { //Dette er også fra chatGPT
             String userId = userModel.getUserId();
             if (userId != null) { //Dette også

                 Log.d("UserAdapter", "User clicked: userId=" + userId); //Dette også


                 try{
                 Intent intent = new Intent(context, ChatActivity.class);
                 intent.putExtra("id", userId);
                 context.startActivity(intent);}

                 catch (Exception e){
                     // Log the exception
                     Log.e("UserAdapter", "Error starting ChatActivity", e);
                 }
             }
         }
     }
        });}



    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, email;
        public View itemView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.userEmail);
        }
    }


}






