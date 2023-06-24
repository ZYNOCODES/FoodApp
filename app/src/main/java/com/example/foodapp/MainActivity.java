package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.foodapp.Fragments.CartFragment;
import com.example.foodapp.Fragments.HomeFragment;
import com.example.foodapp.Fragments.MyOrdersFragment;
import com.example.foodapp.Fragments.ProfilFragment;

public class MainActivity extends AppCompatActivity implements IconColorChangeListener{
    public ImageView Ic_Home,Ic_Profil,Ic_MyOrders,Ic_Cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InisializationOfFealds();
        IconsOnClickListener();
    }
    @Override
    public void changeIconColors() {
        // Change the icon colors here
        Ic_Home.setColorFilter(ContextCompat.getColor(this, R.color.PrimaryColor));
        Ic_Cart.setColorFilter(ContextCompat.getColor(this, R.color.Gray));
    }
    private void InisializationOfFealds(){
        Ic_Home = findViewById(R.id.HomeIcon);
        Ic_Profil = findViewById(R.id.ProfilIcon);
        Ic_MyOrders = findViewById(R.id.MyOrdersIcon);
        Ic_Cart = findViewById(R.id.CartIcon);
    }
    private  void IconsOnClickListener(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.MainFragmentContainer,new HomeFragment()).commit();
        Ic_Home.setColorFilter(getColor(R.color.PrimaryColor), PorterDuff.Mode.SRC_IN);

        Ic_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.MainFragmentContainer,new HomeFragment())
                        .commit();
                Ic_Home.setColorFilter(getColor(R.color.PrimaryColor), PorterDuff.Mode.SRC_IN);
                Ic_Cart.setColorFilter(getColor(R.color.Gray), PorterDuff.Mode.SRC_IN);
                Ic_MyOrders.setColorFilter(getColor(R.color.Gray),PorterDuff.Mode.SRC_IN);
                Ic_Profil.setColorFilter(getColor(R.color.Gray), PorterDuff.Mode.SRC_IN);
            }
        });
        Ic_MyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.MainFragmentContainer,new MyOrdersFragment())
                        .commit();
                Ic_Home.setColorFilter(getColor(R.color.Gray), PorterDuff.Mode.SRC_IN);
                Ic_Cart.setColorFilter(getColor(R.color.Gray), PorterDuff.Mode.SRC_IN);
                Ic_MyOrders.setColorFilter(getColor(R.color.PrimaryColor),PorterDuff.Mode.SRC_IN);
                Ic_Profil.setColorFilter(getColor(R.color.Gray), PorterDuff.Mode.SRC_IN);
            }
        });
        Ic_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.MainFragmentContainer,new CartFragment())
                        .commit();
                Ic_Home.setColorFilter(getColor(R.color.Gray), PorterDuff.Mode.SRC_IN);
                Ic_Cart.setColorFilter(getColor(R.color.PrimaryColor), PorterDuff.Mode.SRC_IN);
                Ic_MyOrders.setColorFilter(getColor(R.color.Gray),PorterDuff.Mode.SRC_IN);
                Ic_Profil.setColorFilter(getColor(R.color.Gray), PorterDuff.Mode.SRC_IN);
            }
        });
        Ic_Profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.MainFragmentContainer,new ProfilFragment())
                        .commit();
                Ic_Home.setColorFilter(getColor(R.color.Gray), PorterDuff.Mode.SRC_IN);
                Ic_Cart.setColorFilter(getColor(R.color.Gray), PorterDuff.Mode.SRC_IN);
                Ic_MyOrders.setColorFilter(getColor(R.color.Gray),PorterDuff.Mode.SRC_IN);
                Ic_Profil.setColorFilter(getColor(R.color.PrimaryColor), PorterDuff.Mode.SRC_IN);
            }
        });
    }
}