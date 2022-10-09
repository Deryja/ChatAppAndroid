package com.example.chattingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chattingapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //Steg 1
  ActivityMainBinding binding;

  DatabaseReference databaseReference;  //Steg 5

   UserAdapter userAdapter;   //Steg 7 --> etter UserAdapter og activity_main.xml


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Steg 1
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); //Nå gå til AuthenticationActivity


        userAdapter = new UserAdapter(this); //Steg 7
        binding.recycler.setAdapter(userAdapter); //Steg 7
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));



        //Alt under er fortsettelse på steg 5
databaseReference = FirebaseDatabase.getInstance().getReference("users");
databaseReference.addValueEventListener(new ValueEventListener() {





    @Override

    public void onDataChange(@NonNull DataSnapshot snapshot) {

        userAdapter.clear(); //Steg 7
for (DataSnapshot dataSnapshot: snapshot.getChildren()){
    String uid= dataSnapshot.getKey();

    if (!uid.equals(FirebaseAuth.getInstance().getUid())){




UserModel userModel = dataSnapshot.child(uid).getValue(UserModel.class); //Nå går vi og oppretter user_row.xml i layout
        //Etter det høyre trykker vi på drawable og trykker på Vector asset --> så velger vi person ikon



        userAdapter.add(userModel);

        // Steg 7 --> Ferdig, nå går vi og høyre trykker på under res og oppretter menu
// fra Android resource directory, så oppretter vi menu resource file fra den (main_menu.xml), så oppretter vi loggut ikon
        //Ved å høyreklikke på drawable og så trykke på Vector asset, og deretter søke etter power


    }
}

    }



    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});

    }

    @Override
    //Steg 8: (Samtidig som vi fikser layouten til main_menu.xml)
public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.main_menu, menu);
      return true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){ //Steg 8
        if (item.getItemId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,AuthenticationActivity.class));
            finish();
            return true;
        }
        return false;
    }
//Nå går vi til steg 9 ved å lage ny java class --> ChatActivity

}
