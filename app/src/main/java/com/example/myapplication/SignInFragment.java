package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SignInFragment extends Fragment {

    String username, password;
    EditText usernameET, passwordET;
    ImageView hideIcon;
    Button signIn;
    DBHelper database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.sign_in_fragment, container, false);

        database = new DBHelper(getContext());

        usernameET = root.findViewById(R.id.signin_username);
        passwordET = root.findViewById(R.id.signin_password);

        signIn = root.findViewById(R.id.signin_btn);

        hideIcon =  root.findViewById(R.id.show_pass_btn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = usernameET.getText().toString().trim();
                password = passwordET.getText().toString().trim();

                if(!database.checkAcoount(username, password)){

                    Intent homePage = new Intent(getActivity(), HomePage.class);
                    startActivity(homePage);
                }
                else
                    Toast.makeText(getActivity(), "Contul nu există sau credentialele sunt greşite!", Toast.LENGTH_LONG).show();
            }
        });

        hideIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHidePass(view);
            }
        });
        return root;
    }

    public void ShowHidePass(View view) {

        if(view.getId()==R.id.show_pass_btn){
            if(passwordET.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.hide_on_icon);
                passwordET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                passwordET.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

}