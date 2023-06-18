package com.example.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Models.CategoryModel;
import com.example.foodapp.R;
import com.example.foodapp.ViewHolders.CategoryViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    private Context context;
    private ArrayList<CategoryModel> categorys;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categorys) {
        this.context = context;
        this.categorys = categorys;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Picasso.get().load(categorys.get(position).getICON()).into(holder.CategoryICON);
        holder.CategoryTitle.setText(categorys.get(position).getTitle());
        final boolean[] click = {false};
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!click[0]){
                    // show the content by category
                    int primaryColor = ContextCompat.getColor(context, R.color.PrimaryColor);
                    holder.Background.setCardBackgroundColor(primaryColor);
                    int whiteColor = ContextCompat.getColor(context, R.color.white);
                    holder.CategoryTitle.setTextColor(whiteColor);
                    click[0] = true;
                }else {
                    // show the content by category
                    int whiteColor = ContextCompat.getColor(context, R.color.white);
                    holder.Background.setCardBackgroundColor(whiteColor);
                    int primaryTextColor = ContextCompat.getColor(context, R.color.PrimaryTextColor);
                    holder.CategoryTitle.setTextColor(primaryTextColor);
                    click[0] = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categorys.size();
    }
}
