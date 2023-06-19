package com.example.foodapp.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.google.android.material.card.MaterialCardView;

public class AdminPostType2ViewHolder extends RecyclerView.ViewHolder{
    public ImageView PostIMG;
    public TextView PostTitle,PostIngredients,PostPrice;
    public MaterialCardView DeleteBTN;
    public AdminPostType2ViewHolder(@NonNull View itemView) {
        super(itemView);
        InisializationOfFealds();
    }
    private void InisializationOfFealds(){
        PostIMG = itemView.findViewById(R.id.PostIMG);
        PostTitle = itemView.findViewById(R.id.PostTitle);
        PostIngredients = itemView.findViewById(R.id.PostIngredients);
        PostPrice = itemView.findViewById(R.id.PostPrice);
        DeleteBTN = itemView.findViewById(R.id.DeleteBTN);
    }
}
