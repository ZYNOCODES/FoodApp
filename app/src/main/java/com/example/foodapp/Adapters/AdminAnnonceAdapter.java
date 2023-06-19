package com.example.foodapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Models.AnnonceModel;
import com.example.foodapp.R;
import com.example.foodapp.ViewHolders.AdminAnnonceViewHolder;
import com.example.foodapp.ViewHolders.AnnonceViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminAnnonceAdapter extends RecyclerView.Adapter<AdminAnnonceViewHolder> {
    private Context context;
    private ArrayList<AnnonceModel> annonces;

    public AdminAnnonceAdapter(Context context, ArrayList<AnnonceModel> annonces) {
        this.context = context;
        this.annonces = annonces;
    }

    @NonNull
    @Override
    public AdminAnnonceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_annonce_admin,parent,false);
        return new AdminAnnonceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAnnonceViewHolder holder, int position) {
        Picasso.get().load(annonces.get(position).getIMG()).into(holder.AnnonceIMG);
        holder.AnnonceTitle.setText(annonces.get(position).getTitle());
        holder.DeleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //annonce details
            }
        });
    }

    @Override
    public int getItemCount() {
        return annonces.size();
    }
}
