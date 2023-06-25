package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Adapters.MyOrderAdapter;
import com.example.foodapp.Models.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AdminConfirmeOrderActivity extends AppCompatActivity {
    private DatabaseReference RefOrder;
    private MyOrderAdapter myOrderAdapter;
    private RecyclerView CartRecyclerView;
    private TextView DeliveryCartPrice, TotleCartPrice, TotleItemsPrice, LocationOutPut, DeleveryNotesOutPut;
    private String OrderID;
    private ImageView CancelBTN;
    private LinearLayout DeleveryCard;
    private MaterialCardView AnnulationBTN, ConfirmationBTN;
    private Dialog dialog;
    private Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_confirme_order);

        //init
        InisializationOfFealds();
        ButtonsRediraction();
        dialog = new Dialog(AdminConfirmeOrderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_wait1);
        dialog.setCanceledOnTouchOutside(false);

        //recycler view
        LinearLayoutManager manager = new LinearLayoutManager(AdminConfirmeOrderActivity.this,LinearLayoutManager.VERTICAL,false);
        CartRecyclerView.setLayoutManager(manager);

        //fetch data
        fetchDataFromDB();
    }
    private void InisializationOfFealds(){
        CartRecyclerView = findViewById(R.id.CartRecyclerView);
        CancelBTN = findViewById(R.id.CancelBTN);
        DeliveryCartPrice = findViewById(R.id.DeliveryCartPrice);
        TotleCartPrice = findViewById(R.id.TotleCartPrice);
        TotleItemsPrice = findViewById(R.id.TotleItemsPrice);
        LocationOutPut = findViewById(R.id.LocationOutPut);
        DeleveryNotesOutPut = findViewById(R.id.DeleveryNotesOutPut);
        DeleveryCard = findViewById(R.id.DeleveryCard);
        ConfirmationBTN = findViewById(R.id.ConfirmationBTN);
        AnnulationBTN = findViewById(R.id.AnnulationBTN);
        OrderID = getIntent().getStringExtra("OrderID");
        RefOrder = FirebaseDatabase.getInstance(getString(R.string.DBURL))
                .getReference()
                .child("Orders")
                .child(OrderID);
    }
    private void ButtonsRediraction(){
        CancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ConfirmationBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(AdminConfirmeOrderActivity.this);
                mydialog.setTitle("Update "+order.getID());
                mydialog.setMessage("Do you really want to update "
                        +order.getID()+" ?");
                mydialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Update the product
                        dialog.show();
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("confirmation", true);
                        updateOrderIntoDB(updates);
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
        AnnulationBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void fetchDataFromDB(){
        RefOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                order = snapshot.getValue(Order.class);
                final int[] totalItem = {0};
                if (order != null) {
                    TotleCartPrice.setText(String.valueOf(order.getPrice()));
                    for (int i = 0 ; i < order.getProducts().size() ; i++){
                        totalItem[0] = totalItem[0] + Integer.parseInt(order.getProducts().get(i).getProduct().getPrice());
                    }
                    TotleItemsPrice.setText(String.valueOf(totalItem[0]));
                    DeliveryCartPrice.setText(String.valueOf(Integer.parseInt(order.getPrice()) - totalItem[0]));
                    if (order.getType().equals("a domicile")){
                        DeleveryCard.setVisibility(View.GONE);
                    }else {
                        DeleveryCard.setVisibility(View.VISIBLE);
                        LocationOutPut.setText(String.valueOf(order.getLocation()));
                        DeleveryNotesOutPut.setText(String.valueOf(order.getLocationNotes()));
                    }
                    myOrderAdapter = new MyOrderAdapter(AdminConfirmeOrderActivity.this,order.getProducts());
                    CartRecyclerView.setAdapter(myOrderAdapter);
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "Operation canceled", error.toException());
                Toast.makeText(AdminConfirmeOrderActivity.this, "Database operation canceled: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void updateOrderIntoDB(Map<String, Object> product){
        RefOrder.updateChildren(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            dialog.dismiss();
                            onBackPressed();
                            Toast.makeText(AdminConfirmeOrderActivity.this, "Order Confirmed", Toast.LENGTH_SHORT).show();
                        }else {
                            dialog.dismiss();
                            Toast.makeText(AdminConfirmeOrderActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}