package com.example.foodapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.DisplayProductActivity;
import com.example.foodapp.Models.Product;
import com.example.foodapp.R;
import com.example.foodapp.ViewHolders.PostType1ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostType1Adapter extends RecyclerView.Adapter<PostType1ViewHolder> {
    private Context context;
    private ArrayList<Product> Posts;

    public PostType1Adapter(Context context, ArrayList<Product> posts) {
        this.context = context;
        Posts = posts;
    }

    @NonNull
    @Override
    public PostType1ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_posttype1,parent,false);
        return new PostType1ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostType1ViewHolder holder, int position) {
        Picasso.get().load(Posts.get(position).getIMG()).into(holder.PostIMG);
        holder.PostTitle.setText(Posts.get(position).getName());
        holder.PostIngredients.setText(Posts.get(position).getIngredients());
        holder.PostPrice.setText(Posts.get(position).getPrice());
        holder.AddToCartBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add to cart
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //redirect to post details
                Intent i = new Intent(context, DisplayProductActivity.class);
                i.putExtra("ProductID",Posts.get(position).getID());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Posts.size();
    }
}
