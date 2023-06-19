package com.example.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Models.PostType1Model;
import com.example.foodapp.R;
import com.example.foodapp.ViewHolders.AdminPostType1ViewHolder;
import com.example.foodapp.ViewHolders.PostType1ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminPostType1Adapter extends RecyclerView.Adapter<AdminPostType1ViewHolder> {
    private Context context;
    private ArrayList<PostType1Model> Posts;

    public AdminPostType1Adapter(Context context, ArrayList<PostType1Model> posts) {
        this.context = context;
        Posts = posts;
    }

    @NonNull
    @Override
    public AdminPostType1ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_posttype1_admin,parent,false);
        return new AdminPostType1ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminPostType1ViewHolder holder, int position) {
        Picasso.get().load(Posts.get(position).getIMG()).into(holder.PostIMG);
        holder.PostTitle.setText(Posts.get(position).getTitle());
        holder.PostIngredients.setText(Posts.get(position).getIngredients());
        holder.PostPrice.setText(Posts.get(position).getPrice());
        holder.DeleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //redirect to post details
            }
        });
    }

    @Override
    public int getItemCount() {
        return Posts.size();
    }
}
