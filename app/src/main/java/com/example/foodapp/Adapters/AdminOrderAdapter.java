package com.example.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Models.OrderModel;
import com.example.foodapp.R;
import com.example.foodapp.ViewHolders.AdminOrderViewHolder;

import java.util.ArrayList;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderViewHolder> {
    private Context context;
    private ArrayList<OrderModel> orders;

    public AdminOrderAdapter(Context context, ArrayList<OrderModel> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public AdminOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_order_admin,parent,false);
        return new AdminOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrderViewHolder holder, int position) {
        holder.OrderID.setText(orders.get(position).getID());
        holder.OrderPrice.setText(orders.get(position).getPrice());
        holder.OrderType.setText(orders.get(position).getType());
        holder.OrderVoirBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //redirect to order details
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
