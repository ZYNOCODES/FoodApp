package com.example.foodapp.Fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodapp.Adapters.AdminOrderAdapter;
import com.example.foodapp.Adapters.OrderAdapter;
import com.example.foodapp.Models.Order;
import com.example.foodapp.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class AdminOrdersFragment extends Fragment {
    private View view;
    private MaterialCardView ConfirmedBTN, NewBTN;
    private TextView ConfirmedTextView, NewTextView;
    private RecyclerView MyAdminNewOrdersRecyclerView,MyAdminConfirmedOrdersRecyclerView;
    private ArrayList<Order> NewOrder,ConfirmedOrder;
    private AdminOrderAdapter adminOrderAdapter;
    private OrderAdapter orderAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_orders, container, false);

        //init
        InisializationOfFealds();
        ButtonRedirection();

        //New orders recycler view
        NewOrder = new ArrayList<>();
        NewOrder.add(new Order("34f51f5f4sddsf","a domicile","350",false));
        NewOrder.add(new Order("45231f5f4s54sf","a domicile","600",false));
        NewOrder.add(new Order("78971f5f4s7d6f","Livraison","850",false));
        NewOrder.add(new Order("24351f5f4sdds5","Livraison","750",false));
        NewOrder.add(new Order("57851f5f4sdds8","Livraison","1200",false));
        NewOrder.add(new Order("gdv51f5f4sdd47","Livraison","250",false));
        NewOrder.add(new Order("27d51f5f4sddcb","a domicile","250",false));
        NewOrder.add(new Order("99f51f5f4sddbb","a domicile","250",false));
        NewOrder.add(new Order("7yf51f5f4sddaw","a domicile","300",false));
        NewOrder.add(new Order("d8f51f5f4spert","Livraison","300",false));

        adminOrderAdapter = new AdminOrderAdapter(getActivity(),NewOrder);
        MyAdminNewOrdersRecyclerView.setAdapter(adminOrderAdapter);

        LinearLayoutManager Newmanager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        MyAdminNewOrdersRecyclerView.setLayoutManager(Newmanager);

        //Confirmed orders recycler view
        ConfirmedOrder = new ArrayList<>();
        ConfirmedOrder.add(new Order("34f51f5f4sddsf","a domicile","350",true));
        ConfirmedOrder.add(new Order("45231f5f4s54sf","a domicile","600",true));
        ConfirmedOrder.add(new Order("78971f5f4s7d6f","Livraison","850",true));
        ConfirmedOrder.add(new Order("24351f5f4sdds5","Livraison","750",true));
        ConfirmedOrder.add(new Order("57851f5f4sdds8","Livraison","1200",true));
        ConfirmedOrder.add(new Order("gdv51f5f4sdd47","Livraison","250",true));
        ConfirmedOrder.add(new Order("27d51f5f4sddcb","a domicile","250",true));
        ConfirmedOrder.add(new Order("99f51f5f4sddbb","a domicile","250",true));
        ConfirmedOrder.add(new Order("7yf51f5f4sddaw","a domicile","300",true));
        ConfirmedOrder.add(new Order("d8f51f5f4spert","Livraison","300",true));

        orderAdapter = new OrderAdapter(getActivity(),ConfirmedOrder);
        MyAdminConfirmedOrdersRecyclerView.setAdapter(orderAdapter);

        LinearLayoutManager Confirmedmanager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        MyAdminConfirmedOrdersRecyclerView.setLayoutManager(Confirmedmanager);
        return view;
    }
    private void InisializationOfFealds(){
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
    }
    private void ButtonRedirection(){
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
}