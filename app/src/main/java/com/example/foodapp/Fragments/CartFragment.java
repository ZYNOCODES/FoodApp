package com.example.foodapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Adapters.AnnonceAdapter;
import com.example.foodapp.Adapters.CartAdapter;
import com.example.foodapp.IconColorChangeListener;
import com.example.foodapp.MainActivity;
import com.example.foodapp.Models.Cart;
import com.example.foodapp.Models.Product;
import com.example.foodapp.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private View view;
    private MaterialCardView DeleveryNotesInPut, PlaceMYOrderBTN;
    private CheckBox DeleveryCheckBox;
    private DatabaseReference RefCart;
    private FirebaseAuth Auth;
    private CartAdapter cartAdapter;
    private RecyclerView CartRecyclerView;
    private LinearLayout AddToCartBTN;
    private TextView DeliveryCartPrice, TotleCartPrice, TotleItemsPrice;
    private ArrayList<Cart> products;
    private IconColorChangeListener iconColorChangeListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);

        //init
        InisializationOfFealds();
        ButtonRedirection();

        //recycler view
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        CartRecyclerView.setLayoutManager(manager);

        //fetch data
        fetchDataFromDB();

        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Check if the hosting Activity implements the interface
        if (context instanceof IconColorChangeListener) {
            iconColorChangeListener = (IconColorChangeListener) context;
        } else {
            throw new ClassCastException("Hosting Activity must implement IconColorChangeListener");
        }
    }

    private void InisializationOfFealds(){
        DeleveryNotesInPut = view.findViewById(R.id.DeleveryNotesInPut);
        DeleveryCheckBox = view.findViewById(R.id.DeleveryCheckBox);
        DeleveryNotesInPut.setVisibility(View.GONE);
        CartRecyclerView = view.findViewById(R.id.CartRecyclerView);
        AddToCartBTN = view.findViewById(R.id.AddToCartBTN);
        PlaceMYOrderBTN = view.findViewById(R.id.PlaceMYOrderBTN);
        DeliveryCartPrice = view.findViewById(R.id.DeliveryCartPrice);
        TotleCartPrice = view.findViewById(R.id.TotleCartPrice);
        TotleItemsPrice = view.findViewById(R.id.TotleItemsPrice);
        Auth = FirebaseAuth.getInstance();
        RefCart = FirebaseDatabase.getInstance(getString(R.string.DBURL))
                .getReference()
                .child("Users")
                .child(Auth.getCurrentUser().getUid())
                .child("Cart");
    }
    private void ButtonRedirection(){
        DeleveryCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DeleveryCheckBox.isChecked()){
                    DeleveryNotesInPut.setVisibility(View.VISIBLE);
                }else {
                    DeleveryNotesInPut.setVisibility(View.GONE);
                }
            }
        });
        AddToCartBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                        .replace(R.id.MainFragmentContainer, new HomeFragment());
                fragmentTransaction.commit();
                // Inside your method where you want to change the icon colors
                iconColorChangeListener.changeIconColors();
            }
        });
    }
    private void fetchDataFromDB(){
        products = new ArrayList<>();
        RefCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                final int[] totalItem = {0};
                int DeliveryPrice = 200;
                for (DataSnapshot oneSnapshot : snapshot.getChildren()){
                    Cart product = oneSnapshot.getValue(Cart.class);
                    if (product != null) {
                        totalItem[0] = totalItem[0] + Integer.parseInt(product.getProduct().getPrice());
                    }
                    products.add(product);
                }
                TotleItemsPrice.setText(String.valueOf(totalItem[0]));
                DeliveryCartPrice.setText(String.valueOf(DeliveryPrice));
                TotleCartPrice.setText(String.valueOf(totalItem[0]+DeliveryPrice));
                cartAdapter = new CartAdapter(getActivity(), products);
                CartRecyclerView.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "Operation canceled", error.toException());
                Toast.makeText(getActivity(), "Database operation canceled: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}