package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfPets extends AppCompatActivity {

    RecyclerView rv;
    ImageView back;
    private ArrayList<Pet> pets;
    private PetsListAdapter adapter;
    private FirebaseDatabase see_pets_db = FirebaseDatabase.getInstance();
    private DatabaseReference see_pets_ref = see_pets_db.getReference();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_pets);

        rv = findViewById(R.id.pets_recyclerView);
        back = findViewById(R.id.backIcon);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setHasFixedSize(true);

        pets = new ArrayList<>();

        clearData();
        getData();

        adapter = new PetsListAdapter(ListOfPets.this, pets);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toHome = new Intent(ListOfPets.this, HomePage.class);
                startActivity(toHome);
            }
        });
    }

    private void getData() {

        Query query = see_pets_ref.child("Pets");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearData();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Pet pet = new Pet();

                    pet.setName(dataSnapshot.child("Pet name").getValue().toString());
                    pet.setAge(dataSnapshot.child("Pet age").getValue().toString());
                    pet.setWeight(dataSnapshot.child("Pet weight").getValue().toString());
                    pet.setRfidTag(dataSnapshot.child("Pet rfid").getValue().toString());
                    pet.setRace(dataSnapshot.child("Pet race").getValue().toString());
                    pet.setType(dataSnapshot.child("Pet type").getValue().toString());

                    pets.add(pet);

                    System.out.println("Tip aici 2: " + dataSnapshot.child("Pet type").getValue().toString());
                }

                adapter = new PetsListAdapter(ListOfPets.this, pets);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearData() {
        if(pets!=null){
            pets.clear();

            if(adapter!=null){
                adapter.notifyDataSetChanged();
            }
        }
        pets = new ArrayList<>();
    }

}