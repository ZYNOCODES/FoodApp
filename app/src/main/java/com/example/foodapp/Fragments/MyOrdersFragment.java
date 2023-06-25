package com.example.foodapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodapp.Adapters.CartAdapter;
import com.example.foodapp.Adapters.OrderAdapter;
import com.example.foodapp.Models.Cart;
import com.example.foodapp.Models.Order;
import com.example.foodapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyOrdersFragment extends Fragment {
    private View view;
    private OrderAdapter orderAdapter;
    private RecyclerView MyOrdersRecyclerView;
    private ArrayList<Order> orders;
    private DatabaseReference RefOrder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        //init
        InisializationOfFealds();

        //recycler view
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        MyOrdersRecyclerView.setLayoutManager(manager);

        //fetch data
        fetchDataFromDB();
        return view;
    }
    private void InisializationOfFealds(){
        MyOrdersRecyclerView = view.findViewById(R.id.MyOrdersRecyclerView);
        RefOrder = FirebaseDatabase.getInstance(getString(R.string.DBURL))
                .getReference()
                .child("Orders");
    }
    private void fetchDataFromDB(){
        orders = new ArrayList<>();
        RefOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orders.clear();
                for (DataSnapshot oneSnapshot : snapshot.getChildren()){
                    Order order = oneSnapshot.getValue(Order.class);
                    orders.add(order);
                }
                orderAdapter = new OrderAdapter(getActivity(),orders);
                MyOrdersRecyclerView.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "Operation canceled", error.toException());
                Toast.makeText(getActivity(), "Database operation canceled: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}