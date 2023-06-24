package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Models.Product;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DisplayProductActivity extends AppCompatActivity {
    private CardView PlusBTN,LessBTN;
    private MaterialCardView AddToCartBTN;
    private EditText ModificationInPut;
    private TextView AboutOutPut,TitleOutPut,PriceOutPut,QuantityOutPut;
    private ImageView IMGOutPut,BackBTN;
    private String ProductID;
    private DatabaseReference ProductRef,CartRef;
    private FirebaseAuth Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product);

        //init
        InisializationOfFealds();
        ButtonsRediraction();

        //get product details
        ProductRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product product = snapshot.getValue(Product.class);
                AboutOutPut.setText(product.getDescription());
                TitleOutPut.setText(product.getName());
                PriceOutPut.setText(product.getPrice());
                Picasso.get().load(product.getIMG()).into(IMGOutPut);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "Operation canceled", error.toException());
                Toast.makeText(DisplayProductActivity.this, "Database operation canceled: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void InisializationOfFealds(){
        AddToCartBTN = findViewById(R.id.AddToCartBTN);
        PlusBTN = findViewById(R.id.PlusBTN);
        LessBTN = findViewById(R.id.LessBTN);
        QuantityOutPut = findViewById(R.id.QuantityOutPut);
        ModificationInPut = findViewById(R.id.ModificationInPut);
        AboutOutPut = findViewById(R.id.AboutOutPut);
        TitleOutPut = findViewById(R.id.TitleOutPut);
        PriceOutPut = findViewById(R.id.PriceOutPut);
        IMGOutPut = findViewById(R.id.IMGOutPut);
        BackBTN = findViewById(R.id.BackBTN);
        ProductID = getIntent().getStringExtra("ProductID");
        Auth = FirebaseAuth.getInstance();
        ProductRef = FirebaseDatabase.getInstance(getString(R.string.DBURL))
                .getReference()
                .child("Products")
                .child(ProductID);
        CartRef = FirebaseDatabase.getInstance(getString(R.string.DBURL))
                .getReference()
                .child("Users")
                .child(Auth.getCurrentUser().getUid())
                .child("Cart");
    }
    private void ButtonsRediraction(){
        PlusBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(Integer.parseInt(QuantityOutPut.getText().toString()) < 0)) {
                    int result = Integer.parseInt(QuantityOutPut.getText().toString()) + 1;
                    QuantityOutPut.setText(String.valueOf(result));
                }else {
                    QuantityOutPut.setText("0");
                }
            }
        });
        LessBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(Integer.parseInt(QuantityOutPut.getText().toString()) <= 0)) {
                    int result = Integer.parseInt(QuantityOutPut.getText().toString()) - 1;
                    QuantityOutPut.setText(String.valueOf(result));
                }else {
                    QuantityOutPut.setText("0");
                }
            }
        });
        BackBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}