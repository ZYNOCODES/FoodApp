package com.example.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Models.OrderModel;
import com.example.foodapp.R;
import com.example.foodapp.ViewHolders.OrderViewHolder;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
        private Context context;
        private ArrayList<OrderModel> orders;

        public OrderAdapter(Context context, ArrayList<OrderModel> orders) {
                this.context = context;
                this.orders = orders;
        }

        @NonNull
        @Override
        public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.card_order,parent,false);
                return new OrderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
                if (orders.get(position).getConfirmation()){
                        holder.OrderCancelBTN.setVisibility(View.GONE);
                        holder.OrderConfirmedBTN.setVisibility(View.VISIBLE);
                        holder.OrderCancelBTN.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        //cancel this order
                                }
                        });
                }else {
                        holder.OrderConfirmedBTN.setVisibility(View.GONE);
                        holder.OrderCancelBTN.setVisibility(View.VISIBLE);
                }

                holder.OrderID.setText(orders.get(position).getID());
                holder.OrderPrice.setText(orders.get(position).getPrice());
                holder.OrderType.setText(orders.get(position).getType());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
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
