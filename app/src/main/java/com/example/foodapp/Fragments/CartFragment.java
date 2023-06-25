package com.example.foodapp.Fragments;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Adapters.AnnonceAdapter;
import com.example.foodapp.Adapters.CartAdapter;
import com.example.foodapp.IconColorChangeListener;
import com.example.foodapp.MainActivity;
import com.example.foodapp.Models.Cart;
import com.example.foodapp.Models.Order;
import com.example.foodapp.Models.Product;
import com.example.foodapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private MaterialCardView DeleveryNotes, PlaceMYOrderBTN;
    private CheckBox DeleveryCheckBox;
    private DatabaseReference RefCart,RefOrder;
    private FirebaseAuth Auth;
    private CartAdapter cartAdapter;
    private RecyclerView CartRecyclerView;
    private LinearLayout AddToCartBTN;
    private TextView DeliveryCartPrice, TotleCartPrice, TotleItemsPrice, LocationOutPut;
    private ArrayList<Cart> products;
    private IconColorChangeListener iconColorChangeListener;
    private Dialog dialog;
    private EditText DeleveryNotesInPut;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);

        //init
        InisializationOfFealds();
        ButtonRedirection();
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_wait1);
        dialog.setCanceledOnTouchOutside(false);

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
        DeleveryNotes = view.findViewById(R.id.DeleveryNotes);
        DeleveryCheckBox = view.findViewById(R.id.DeleveryCheckBox);
        DeleveryNotes.setVisibility(View.GONE);
        CartRecyclerView = view.findViewById(R.id.CartRecyclerView);
        AddToCartBTN = view.findViewById(R.id.AddToCartBTN);
        PlaceMYOrderBTN = view.findViewById(R.id.PlaceMYOrderBTN);
        DeliveryCartPrice = view.findViewById(R.id.DeliveryCartPrice);
        TotleCartPrice = view.findViewById(R.id.TotleCartPrice);
        TotleItemsPrice = view.findViewById(R.id.TotleItemsPrice);
        LocationOutPut = view.findViewById(R.id.LocationOutPut);
        DeleveryNotesInPut = view.findViewById(R.id.DeleveryNotesInPut);
        Auth = FirebaseAuth.getInstance();
        RefCart = FirebaseDatabase.getInstance(getString(R.string.DBURL))
                .getReference()
                .child("Users")
                .child(Auth.getCurrentUser().getUid())
                .child("Cart");
        RefOrder = FirebaseDatabase.getInstance(getString(R.string.DBURL))
                .getReference()
                .child("Orders");
    }
    private void ButtonRedirection(){
        DeleveryCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DeleveryCheckBox.isChecked()){
                    DeleveryNotes.setVisibility(View.VISIBLE);
                }else {
                    DeleveryNotes.setVisibility(View.GONE);
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
        PlaceMYOrderBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (products.size() > 0){
                    RefOrder = FirebaseDatabase.getInstance(getString(R.string.DBURL))
                            .getReference()
                            .child("Orders");
                    RefOrder = RefOrder.push();
                    String idd = RefOrder.getKey();
                    if (!DeleveryCheckBox.isChecked()){
                        Order order = new Order(idd,"a domicile",String.valueOf(TotleCartPrice.getText()),Auth.getCurrentUser().getUid(),false,products);
                        saveOrderIntoDB(order);
                    }else {
                        Order order = new Order(idd,"Livraison",String.valueOf(LocationOutPut.getText()),String.valueOf(DeleveryNotesInPut.getText()),
                                String.valueOf(TotleCartPrice.getText()),Auth.getCurrentUser().getUid(),false,products);
                        saveOrderIntoDB(order);
                    }
                }
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
    protected void saveOrderIntoDB(Order order){
        RefOrder.setValue(order)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            dialog.dismiss();
                            //clear cart
                            RefCart.removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getActivity(), "Product added", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}