package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AddPet extends AppCompatActivity
{
    EditText name, age, weight, rfid_tag, race;
    Spinner type;
    Button insert_btn;
    ImageView back;

    FirebaseDatabase add_pet_db;
    DatabaseReference add_pet_ref;

    final List<String> pets = Arrays.asList("Pisică", "Căţel");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pet);

        type = findViewById(R.id.add_pettype);
        name = findViewById(R.id.add_petname);
        age = findViewById(R.id.add_petage);
        weight = findViewById(R.id.add_petweight);
        rfid_tag = findViewById(R.id.add_petrfid);
        race = findViewById(R.id.add_race);
        insert_btn = findViewById(R.id.insert_pet_btn);

        back = findViewById(R.id.backIcon);

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.selected_item, pets);
        adapter.setDropDownViewResource(R.layout.selected_item);
        type.setAdapter(adapter);

        add_pet_db = FirebaseDatabase.getInstance();
        add_pet_ref = add_pet_db.getReference().child("Pets");

        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    String petType = type.getSelectedItem().toString();
                    String petName = name.getText().toString();
                    String petAge = age.getText().toString();
                    String petWeight = weight.getText().toString();
                    String petRFID = rfid_tag.getText().toString();
                    String petRace = race.getText().toString();

                    HashMap<String, String> petInfos = new HashMap<>();

                    petInfos.put("Pet type", petType);
                    petInfos.put("Pet name", petName);
                    petInfos.put("Pet age", petAge);
                    petInfos.put("Pet weight", petWeight);
                    petInfos.put("Pet rfid", petRFID);
                    petInfos.put("Pet race", petRace);

                    name.getText().clear();
                    age.getText().clear();
                    weight.getText().clear();
                    rfid_tag.getText().clear();
                    race.getText().clear();


                    add_pet_ref.child(petName).setValue(petInfos).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(AddPet.this, "Animalul dvs. de companie a fost adăugat cu succes!", Toast.LENGTH_SHORT).show();
                    }
                });

                }catch (Exception e){

                    Toast.makeText(AddPet.this, "Nu a putut fi adăugat animalul dvs. de companie!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toHome = new Intent(AddPet.this, HomePage.class);
                startActivity(toHome);
            }
        });

    }
}