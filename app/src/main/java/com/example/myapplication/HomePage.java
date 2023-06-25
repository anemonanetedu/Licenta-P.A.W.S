package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class HomePage extends AppCompatActivity{

    LinearLayout add, listOfPets, feedingGuide, logout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        add = findViewById(R.id.addPet);
        listOfPets = findViewById(R.id.seePets);
        feedingGuide = findViewById(R.id.guide);
        logout = findViewById(R.id.logoutOption);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addPetPage = new Intent(HomePage.this, AddPet.class);
                startActivity(addPetPage);
            }
        });

        listOfPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent seeListOfPets = new Intent(HomePage.this, ListOfPets.class);
                startActivity(seeListOfPets);
            }
        });

        feedingGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent showGuide = new Intent(HomePage.this, GuidePage.class);
                startActivity(showGuide);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registrationPage = new Intent(HomePage.this, MainActivity.class);
                startActivity(registrationPage);
            }
        });
    }

}