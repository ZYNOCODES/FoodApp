package com.example.foodapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Adapters.AnnonceAdapter;
import com.example.foodapp.Adapters.PostType1Adapter;
import com.example.foodapp.Adapters.PostType2Adapter;
import com.example.foodapp.Models.Product;
import com.example.foodapp.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private View view;
    private RecyclerView AnnonceRecyclerView, ProductRecyclerView, AllProductRecyclerView;
    private PostType1Adapter postType1Adapter;
    private PostType2Adapter postType2Adapter;
    private AnnonceAdapter annonceAdapter;
    private DatabaseReference RefProduct;
    private MaterialCardView DrinksBTN,SandwichBTN,PizzaBTN,BurgerBTN;
    private TextView DrinksTitle,SandwichTitle,PizzaTitle,BurgerTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //init
        InisializationOfFealds();

        //Recyclerviews
        LinearLayoutManager Annoncemanager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        AnnonceRecyclerView.setLayoutManager(Annoncemanager);
        LinearLayoutManager PostType1Manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        ProductRecyclerView.setLayoutManager(PostType1Manager);
        LinearLayoutManager PostType2Manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        AllProductRecyclerView.setLayoutManager(PostType2Manager);

        //fetch data
        fetchDataFromDB();

        return view;
    }
    private void InisializationOfFealds(){
        AnnonceRecyclerView = view.findViewById(R.id.AnnonceRecyclerView);
        ProductRecyclerView = view.findViewById(R.id.ProductRecyclerView);
        AllProductRecyclerView = view.findViewById(R.id.AllProductRecyclerView);
        DrinksBTN = view.findViewById(R.id.DrinksBTN);
        SandwichBTN = view.findViewById(R.id.SandwichBTN);
        PizzaBTN = view.findViewById(R.id.PizzaBTN);
        BurgerBTN = view.findViewById(R.id.BurgerBTN);
        DrinksTitle = view.findViewById(R.id.DrinksTitle);
        SandwichTitle = view.findViewById(R.id.SandwichTitle);
        PizzaTitle = view.findViewById(R.id.PizzaTitle);
        BurgerTitle = view.findViewById(R.id.BurgerTitle);
        RefProduct = FirebaseDatabase.getInstance(getContext().getString(R.string.DBURL))
                .getReference().child("Products");
    }
    private void fetchDataFromDB(){
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Product> AnnonceProducts = new ArrayList<>();
        ArrayList<Product> BurgerProducts = new ArrayList<>();
        ArrayList<Product> PizzaProducts = new ArrayList<>();
        ArrayList<Product> SandwichProducts = new ArrayList<>();

        RefProduct.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot oneSnapshot : snapshot.getChildren()){
                    Product product = oneSnapshot.getValue(Product.class);
                    if (product != null && product.getAnnonce()) {
                        // If the condition is true, add the product to the filtered list
                        AnnonceProducts.add(product);
                    }
                    if (product != null && (product.getCategory().equals("Burger") || product.getCategory().equals("burger"))) {
                        // If the condition is true, add the product to the filtered list
                        BurgerProducts.add(product);
                    }
                    if (product != null && (product.getCategory().equals("Pizza") || product.getCategory().equals("pizza"))) {
                        // If the condition is true, add the product to the filtered list
                        PizzaProducts.add(product);
                    }
                    if (product != null && (product.getCategory().equals("Sandwich") || product.getCategory().equals("sandwich"))) {
                        // If the condition is true, add the product to the filtered list
                        SandwichProducts.add(product);
                    }
                    products.add(product);
                }
                annonceAdapter = new AnnonceAdapter(getActivity(), AnnonceProducts);
                AnnonceRecyclerView.setAdapter(annonceAdapter);

                //init category in burger button
                if (isAdded()){
                    int primaryColor = ContextCompat.getColor(getActivity(), R.color.PrimaryColor);
                    int whiteColor = ContextCompat.getColor(getActivity(), R.color.white);
                    int primaryTextColor = ContextCompat.getColor(getActivity(), R.color.PrimaryTextColor);
                    BurgerBTN.setCardBackgroundColor(primaryColor);
                    BurgerTitle.setTextColor(whiteColor);
                    postType1Adapter = new PostType1Adapter(getActivity(),BurgerProducts);
                    ProductRecyclerView.setAdapter(postType1Adapter);
                    BurgerBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // show the content by category
                            BurgerBTN.setCardBackgroundColor(primaryColor);
                            BurgerTitle.setTextColor(whiteColor);
                            PizzaBTN.setCardBackgroundColor(whiteColor);
                            PizzaTitle.setTextColor(primaryTextColor);
                            SandwichBTN.setCardBackgroundColor(whiteColor);
                            SandwichTitle.setTextColor(primaryTextColor);
                            DrinksBTN.setCardBackgroundColor(whiteColor);
                            DrinksTitle.setTextColor(primaryTextColor);

                            postType1Adapter = new PostType1Adapter(getActivity(),BurgerProducts);
                            ProductRecyclerView.setAdapter(postType1Adapter);
                        }
                    });
                    PizzaBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // show the content by category
                            PizzaBTN.setCardBackgroundColor(primaryColor);
                            PizzaTitle.setTextColor(whiteColor);
                            BurgerBTN.setCardBackgroundColor(whiteColor);
                            BurgerTitle.setTextColor(primaryTextColor);
                            SandwichBTN.setCardBackgroundColor(whiteColor);
                            SandwichTitle.setTextColor(primaryTextColor);
                            DrinksBTN.setCardBackgroundColor(whiteColor);
                            DrinksTitle.setTextColor(primaryTextColor);

                            postType1Adapter = new PostType1Adapter(getActivity(),PizzaProducts);
                            ProductRecyclerView.setAdapter(postType1Adapter);
                        }
                    });
                    SandwichBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // show the content by category
                            SandwichBTN.setCardBackgroundColor(primaryColor);
                            SandwichTitle.setTextColor(whiteColor);
                            PizzaBTN.setCardBackgroundColor(whiteColor);
                            PizzaTitle.setTextColor(primaryTextColor);
                            BurgerBTN.setCardBackgroundColor(whiteColor);
                            BurgerTitle.setTextColor(primaryTextColor);
                            DrinksBTN.setCardBackgroundColor(whiteColor);
                            DrinksTitle.setTextColor(primaryTextColor);

                            postType1Adapter = new PostType1Adapter(getActivity(),SandwichProducts);
                            ProductRecyclerView.setAdapter(postType1Adapter);
                        }
                    });
                    DrinksBTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DrinksBTN.setCardBackgroundColor(primaryColor);
                            DrinksTitle.setTextColor(whiteColor);
                            PizzaBTN.setCardBackgroundColor(whiteColor);
                            PizzaTitle.setTextColor(primaryTextColor);
                            SandwichBTN.setCardBackgroundColor(whiteColor);
                            SandwichTitle.setTextColor(primaryTextColor);
                            BurgerBTN.setCardBackgroundColor(whiteColor);
                            BurgerTitle.setTextColor(primaryTextColor);

                            postType1Adapter = new PostType1Adapter(getActivity(),products);
                            ProductRecyclerView.setAdapter(postType1Adapter);
                        }
                    });
                }else {
                    // Handle the case when the fragment is not attached to an activity
                    Log.e("DatabaseError", "fragment is not attached to an activity");
                }
                postType2Adapter = new PostType2Adapter(getActivity(), products);
                AllProductRecyclerView.setAdapter(postType2Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "Operation canceled", error.toException());
                Toast.makeText(getActivity(), "Database operation canceled: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}