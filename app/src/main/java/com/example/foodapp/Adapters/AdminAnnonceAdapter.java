package com.example.foodapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.AdminDisplayProductActivity;
import com.example.foodapp.Models.AnnonceModel;
import com.example.foodapp.Models.Product;
import com.example.foodapp.R;
import com.example.foodapp.ViewHolders.AdminAnnonceViewHolder;
import com.example.foodapp.ViewHolders.AnnonceViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminAnnonceAdapter extends RecyclerView.Adapter<AdminAnnonceViewHolder> {
    private Context context;
    private ArrayList<Product> annonces;
    private DatabaseReference RefProduct;

    public AdminAnnonceAdapter(Context context, ArrayList<Product> annonces) {
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
        holder.AnnonceTitle.setText(annonces.get(position).getName());
        RefProduct = FirebaseDatabase.getInstance(context.getString(R.string.DBURL))
                .getReference().child("Products");
        holder.DeleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete
                AlertDialog.Builder mydialog = new AlertDialog.Builder(context);
                mydialog.setTitle("Delete "+annonces.get(position).getName());
                mydialog.setMessage("Do you really want to delete "
                        +annonces.get(position).getName()+" ?");
                mydialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // deleting the product
                        RefProduct.child(annonces.get(position).getID()).removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(context, "Product deleted", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(context, "Error , check your internet connexion", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                    }
                });
                mydialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mydialog.show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //annonce details
                Intent i = new Intent(context, AdminDisplayProductActivity.class);
                i.putExtra("ProductID",annonces.get(position).getID());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return annonces.size();
    }
}
