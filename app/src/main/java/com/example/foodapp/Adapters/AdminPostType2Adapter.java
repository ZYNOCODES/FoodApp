package com.example.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Models.PostType1Model;
import com.example.foodapp.R;
import com.example.foodapp.ViewHolders.AdminPostType2ViewHolder;
import com.example.foodapp.ViewHolders.PostType2ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminPostType2Adapter extends RecyclerView.Adapter<AdminPostType2ViewHolder> {
    private Context context;
    private ArrayList<PostType1Model> Posts;

    public AdminPostType2Adapter(Context context, ArrayList<PostType1Model> posts) {
        this.context = context;
        Posts = posts;
    }

    @NonNull
    @Override
    public AdminPostType2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_posttype2_admin,parent,false);
        return new AdminPostType2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminPostType2ViewHolder holder, int position) {
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
