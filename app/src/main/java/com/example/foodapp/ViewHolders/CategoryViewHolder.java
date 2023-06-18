package com.example.foodapp.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.google.android.material.card.MaterialCardView;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    public ImageView CategoryICON;
    public TextView CategoryTitle;
    public MaterialCardView Background;
    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        InisializationOfFealds();
    }
    private void InisializationOfFealds(){
        CategoryICON = itemView.findViewById(R.id.CategoryICON);
        CategoryTitle = itemView.findViewById(R.id.CategoryTitle);
        Background = itemView.findViewById(R.id.Background);
    }
}
