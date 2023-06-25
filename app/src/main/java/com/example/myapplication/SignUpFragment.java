package com.example.myapplication;

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

public class SignUpFragment extends Fragment {

    String username, lastname, firstname, email, password;
    EditText usernameET, lastnameET, firstnameET, emailET, passwordET;
    ImageView hideIcon;
    Button signUp;
    DBHelper database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.sign_up_fragment, container, false);

        database =  new DBHelper(this);

        usernameET = root.findViewById(R.id.signup_username);
        lastnameET = root.findViewById(R.id.signup_lastname);
        firstnameET = root.findViewById(R.id.signup_firstname);
        emailET = root.findViewById(R.id.signup_email);
        passwordET = root.findViewById(R.id.signup_password);

        signUp = root.findViewById(R.id.signup_btn);

        hideIcon =  root.findViewById(R.id.show_pass_btn2);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = usernameET.getText().toString().trim();
                lastname = lastnameET.getText().toString().trim();
                firstname = firstnameET.getText().toString().trim();
                email = emailET.getText().toString().trim();
                password = passwordET.getText().toString().trim();

                if(database.checkUser(username, email)){

                    DBHelper signUpDB = new DBHelper(SignUpFragment.this);

                    Boolean check = signUpDB.createUser(username, lastname, firstname, email, password);
                    if(check) {
                        Toast.makeText(getActivity(), "Cont creat cu succes!", Toast.LENGTH_SHORT).show();

                        usernameET.getText().clear();
                        lastnameET.getText().clear();;
                        firstnameET.getText().clear();
                        emailET.getText().clear();
                        passwordET.getText().clear();
                    }
                }
                else
                    Toast.makeText(getActivity(), "Acest cont deja există!", Toast.LENGTH_SHORT).show();
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

        if(view.getId()==R.id.show_pass_btn2){
            if(passwordET.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.hide_on_icon);
                passwordET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.hide_icon);
                passwordET.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}