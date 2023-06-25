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
import com.example.foodapp.DisplayProductActivity;
import com.example.foodapp.Models.Product;
import com.example.foodapp.R;
import com.example.foodapp.ViewHolders.AdminPostType1ViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminPostType1Adapter extends RecyclerView.Adapter<AdminPostType1ViewHolder> {
    private Context context;
    private ArrayList<Product> Posts;
    private DatabaseReference RefProduct;
    public AdminPostType1Adapter(Context context, ArrayList<Product> posts) {
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
        holder.PostTitle.setText(Posts.get(position).getName());
        holder.PostIngredients.setText(Posts.get(position).getIngredients());
        holder.PostPrice.setText(Posts.get(position).getPrice());
        RefProduct = FirebaseDatabase.getInstance(context.getString(R.string.DBURL))
                .getReference().child("Products");
        holder.DeleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete the product from the database
                AlertDialog.Builder mydialog = new AlertDialog.Builder(context);
                mydialog.setTitle("Supprimer cette publication");
                mydialog.setMessage("Voulez-vous vraiment supprimer cette publication "
                        +Posts.get(position).getName()+" ?");
                mydialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // deleting the product
                        RefProduct.child(Posts.get(position).getID()).removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(context, "La publication a été supprimée avec succès", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(context, "Erreur, vérifiez votre connexion internet.", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                    }
                });
                mydialog.setNegativeButton("Non", new DialogInterface.OnClickListener() {
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
                //redirect to post details
                Intent i = new Intent(context, AdminDisplayProductActivity.class);
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
