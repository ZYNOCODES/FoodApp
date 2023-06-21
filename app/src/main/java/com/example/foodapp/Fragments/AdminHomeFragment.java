package com.example.foodapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodapp.Adapters.AdminAnnonceAdapter;
import com.example.foodapp.Adapters.AdminPostType1Adapter;
import com.example.foodapp.Adapters.AdminPostType2Adapter;
import com.example.foodapp.Adapters.CategoryAdapter;
import com.example.foodapp.Models.AnnonceModel;
import com.example.foodapp.Models.CategoryModel;
import com.example.foodapp.Models.Product;
import com.example.foodapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminHomeFragment extends Fragment {
    private View view;
    private RecyclerView AnnonceRecyclerView,CategoryRecyclerView, ProductRecyclerView, AllProductRecyclerView;
    private ArrayList<Product> PostsType1, PostsType2;
    private ArrayList<CategoryModel> category;
    private ArrayList<AnnonceModel> annonce;
    private AdminPostType1Adapter adminpostType1Adapter;
    private AdminPostType2Adapter adminpostType2Adapter;
    private CategoryAdapter admincategoryAdapter;
    private AdminAnnonceAdapter adminAnnonceAdapter;
    private DatabaseReference RefProduct;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        //init
        InisializationOfFealds();

        //category recycler view
        category = new ArrayList<>();

        category.add(new CategoryModel("Burger",R.drawable.burgerimg));
        category.add(new CategoryModel("Pizza",R.drawable.pizzaimg));
        category.add(new CategoryModel("Sandwich",R.drawable.sandwich));

        admincategoryAdapter = new CategoryAdapter(getActivity(), category);
        CategoryRecyclerView.setAdapter(admincategoryAdapter);

        LinearLayoutManager adminCategorymanager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        CategoryRecyclerView.setLayoutManager(adminCategorymanager);

        //annonce recycler view
        LinearLayoutManager adminAnnoncemanager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        AnnonceRecyclerView.setLayoutManager(adminAnnoncemanager);

        //post type 1 / 2
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
        CategoryRecyclerView = view.findViewById(R.id.CategoryRecyclerView);
        ProductRecyclerView = view.findViewById(R.id.ProductRecyclerView);
        AllProductRecyclerView = view.findViewById(R.id.AllProductRecyclerView);
        RefProduct = FirebaseDatabase.getInstance(getContext().getString(R.string.DBURL))
                .getReference().child("Products");
    }
    private void fetchDataFromDB(){
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Product> AnnonceProducts = new ArrayList<>();
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
                    products.add(product);
                }
                adminAnnonceAdapter = new AdminAnnonceAdapter(getActivity(), AnnonceProducts);
                AnnonceRecyclerView.setAdapter(adminAnnonceAdapter);
                adminpostType1Adapter = new AdminPostType1Adapter(getActivity(),products);
                ProductRecyclerView.setAdapter(adminpostType1Adapter);
                adminpostType2Adapter = new AdminPostType2Adapter(getActivity(), products);
                AllProductRecyclerView.setAdapter(adminpostType2Adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "Operation canceled", error.toException());
                Toast.makeText(getActivity(), "Database operation canceled: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}