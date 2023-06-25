package com.example.foodapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Adapters.AdminOrderAdapter;
import com.example.foodapp.Adapters.OrderAdapter;
import com.example.foodapp.Models.Order;
import com.example.foodapp.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminOrdersFragment extends Fragment {
    private View view;
    private MaterialCardView ConfirmedBTN, NewBTN;
    private TextView ConfirmedTextView, NewTextView;
    private RecyclerView MyAdminNewOrdersRecyclerView, MyAdminConfirmedOrdersRecyclerView;
    private AdminOrderAdapter adminOrderAdapter;
    private OrderAdapter orderAdapter;
    private DatabaseReference RefOrder, RefOrderConfirmed;
    private FirebaseAuth Auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_orders, container, false);

        //init
        InisializationOfFealds();
        ButtonRedirection();

        //recycler view
        LinearLayoutManager Newmanager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        MyAdminNewOrdersRecyclerView.setLayoutManager(Newmanager);
        LinearLayoutManager Confirmedmanager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        MyAdminConfirmedOrdersRecyclerView.setLayoutManager(Confirmedmanager);

        //fetch data
        fetchDataFromDB();

        return view;
    }

    private void InisializationOfFealds() {
        ConfirmedBTN = view.findViewById(R.id.ConfirmedBTN);
        NewBTN = view.findViewById(R.id.NewBTN);
        ConfirmedTextView = view.findViewById(R.id.ConfirmedTextView);
        NewTextView = view.findViewById(R.id.NewTextView);
        MyAdminNewOrdersRecyclerView = view.findViewById(R.id.MyAdminNewOrdersRecyclerView);
        MyAdminConfirmedOrdersRecyclerView = view.findViewById(R.id.MyAdminConfirmedOrdersRecyclerView);
        //init NewBTN
        int SecondColor = ContextCompat.getColor(getActivity(), R.color.SecondColor);
        int PrimaryColor = ContextCompat.getColor(getActivity(), R.color.PrimaryColor);
        NewBTN.setCardBackgroundColor(SecondColor);
        NewTextView.setTextColor(PrimaryColor);
        //ConfirmedBTN
        int WhiteColor = ContextCompat.getColor(getActivity(), R.color.white);
        int PrimaryTextColor = ContextCompat.getColor(getActivity(), R.color.PrimaryTextColor);
        ConfirmedBTN.setCardBackgroundColor(WhiteColor);
        ConfirmedTextView.setTextColor(PrimaryTextColor);
        //visibility
        MyAdminConfirmedOrdersRecyclerView.setVisibility(View.GONE);
        MyAdminNewOrdersRecyclerView.setVisibility(View.VISIBLE);
        Auth = FirebaseAuth.getInstance();
        RefOrder = FirebaseDatabase.getInstance(getString(R.string.DBURL))
                .getReference()
                .child("Orders");
        RefOrderConfirmed = FirebaseDatabase.getInstance(getString(R.string.DBURL))
                .getReference()
                .child("Users")
                .child(Auth.getCurrentUser().getUid())
                .child("Orders");
    }

    private void ButtonRedirection() {
        ConfirmedBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ConfirmedBTN
                int SecondColor = ContextCompat.getColor(getActivity(), R.color.SecondColor);
                int PrimaryColor = ContextCompat.getColor(getActivity(), R.color.PrimaryColor);
                ConfirmedBTN.setCardBackgroundColor(SecondColor);
                ConfirmedTextView.setTextColor(PrimaryColor);
                //NewBTN
                int WhiteColor = ContextCompat.getColor(getActivity(), R.color.white);
                int PrimaryTextColor = ContextCompat.getColor(getActivity(), R.color.PrimaryTextColor);
                NewBTN.setCardBackgroundColor(WhiteColor);
                NewTextView.setTextColor(PrimaryTextColor);
                //visibility
                MyAdminNewOrdersRecyclerView.setVisibility(View.GONE);
                MyAdminConfirmedOrdersRecyclerView.setVisibility(View.VISIBLE);
            }
        });
        NewBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NewBTN
                int SecondColor = ContextCompat.getColor(getActivity(), R.color.SecondColor);
                int PrimaryColor = ContextCompat.getColor(getActivity(), R.color.PrimaryColor);
                NewBTN.setCardBackgroundColor(SecondColor);
                NewTextView.setTextColor(PrimaryColor);
                //ConfirmedBTN
                int WhiteColor = ContextCompat.getColor(getActivity(), R.color.white);
                int PrimaryTextColor = ContextCompat.getColor(getActivity(), R.color.PrimaryTextColor);
                ConfirmedBTN.setCardBackgroundColor(WhiteColor);
                ConfirmedTextView.setTextColor(PrimaryTextColor);
                //visibility
                MyAdminConfirmedOrdersRecyclerView.setVisibility(View.GONE);
                MyAdminNewOrdersRecyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void fetchDataFromDB() {
        ArrayList<Order> NewOrder = new ArrayList<>();
        RefOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NewOrder.clear();
                for (DataSnapshot oneSnapshot : snapshot.getChildren()) {
                    Order order = oneSnapshot.getValue(Order.class);
                    if (order != null && !order.getConfirmation()) {
                        NewOrder.add(order);
                    }
                }
                adminOrderAdapter = new AdminOrderAdapter(getActivity(), NewOrder);
                MyAdminNewOrdersRecyclerView.setAdapter(adminOrderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "Operation canceled", error.toException());
                Toast.makeText(getActivity(), "Database operation canceled: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        ArrayList<Order> ConfirmedOrder = new ArrayList<>();
        RefOrderConfirmed.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ConfirmedOrder.clear();
                for (DataSnapshot oneSnapshot : snapshot.getChildren()) {
                    Order order = oneSnapshot.getValue(Order.class);
                    if (order != null && order.getConfirmation()) {
                        ConfirmedOrder.add(order);
                    }
                }
                orderAdapter = new OrderAdapter(getActivity(), ConfirmedOrder);
                MyAdminConfirmedOrdersRecyclerView.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "Operation canceled", error.toException());
                Toast.makeText(getActivity(), "Database operation canceled: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}