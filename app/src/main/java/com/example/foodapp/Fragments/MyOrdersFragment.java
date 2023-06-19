package com.example.foodapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapp.Adapters.OrderAdapter;
import com.example.foodapp.Models.OrderModel;
import com.example.foodapp.R;

import java.util.ArrayList;

public class MyOrdersFragment extends Fragment {
    private View view;
    private ArrayList<OrderModel> order;
    private OrderAdapter orderAdapter;
    private RecyclerView MyOrdersRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        //init
        InisializationOfFealds();

        //order recycler view
        order = new ArrayList<>();

        order.add(new OrderModel("2c1as131cs2s3","Livraison","350",false));
        order.add(new OrderModel("87c64s56d831s","Livraison","700",true));
        order.add(new OrderModel("5317dc13d31zw","Livraison","250",true));
        order.add(new OrderModel("7h35f3sd454fs","a domicile","350",false));

        orderAdapter = new OrderAdapter(getActivity(),order);
        MyOrdersRecyclerView.setAdapter(orderAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        MyOrdersRecyclerView.setLayoutManager(manager);

        return view;
    }
    private void InisializationOfFealds(){
        MyOrdersRecyclerView = view.findViewById(R.id.MyOrdersRecyclerView);

    }
}