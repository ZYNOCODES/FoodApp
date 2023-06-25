package com.example.foodapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapp.Adapters.OrderAdapter;
import com.example.foodapp.Models.Order;
import com.example.foodapp.R;

import java.util.ArrayList;

public class AdminAllOrdersFragment extends Fragment {
    private View view;
    private ArrayList<Order> order;
    private OrderAdapter orderAdapter;
    private RecyclerView MyOrdersRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_orders, container, false);

        //init
        InisializationOfFealds();

        //all orders recycler view
        order = new ArrayList<>();
        order.add(new Order("34f51f5f4sddsf","a domicile","350",true));
        order.add(new Order("45231f5f4s54sf","a domicile","600",true));
        order.add(new Order("78971f5f4s7d6f","Livraison","850",true));
        order.add(new Order("24351f5f4sdds5","Livraison","750",true));
        order.add(new Order("57851f5f4sdds8","Livraison","1200",true));
        order.add(new Order("gdv51f5f4sdd47","Livraison","250",true));
        order.add(new Order("27d51f5f4sddcb","a domicile","250",true));
        order.add(new Order("99f51f5f4sddbb","a domicile","250",true));
        order.add(new Order("7yf51f5f4sddaw","a domicile","300",true));
        order.add(new Order("d8f51f5f4spert","Livraison","300",true));

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