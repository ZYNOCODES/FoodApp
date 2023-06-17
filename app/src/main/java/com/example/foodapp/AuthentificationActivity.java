package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Bundle;

import com.example.foodapp.Fragments.HomeFragment;
import com.example.foodapp.Fragments.RegistrationFragment;

public class AuthentificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.AuthentificationFragmentContainer,new RegistrationFragment()).commit();
    }
}