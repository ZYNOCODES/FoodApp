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

import com.example.foodapp.Adapters.AdminPostType1Adapter;
import com.example.foodapp.Adapters.AdminPostType2Adapter;
import com.example.foodapp.Adapters.AnnonceAdapter;
import com.example.foodapp.Adapters.CategoryAdapter;
import com.example.foodapp.Adapters.PostType1Adapter;
import com.example.foodapp.Adapters.PostType2Adapter;
import com.example.foodapp.Models.AnnonceModel;
import com.example.foodapp.Models.CategoryModel;
import com.example.foodapp.Models.Product;
import com.example.foodapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private View view;
    private RecyclerView AnnonceRecyclerView,CategoryRecyclerView, ProductRecyclerView, AllProductRecyclerView;
    private PostType1Adapter postType1Adapter;
    private PostType2Adapter postType2Adapter;
    private AnnonceAdapter annonceAdapter;
    private DatabaseReference RefProduct;
    private CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        InisializationOfFealds();

        //category recycler view
        ArrayList<CategoryModel> category = new ArrayList<>();

        category.add(new CategoryModel("Burger",R.drawable.burgerimg));
        category.add(new CategoryModel("Pizza",R.drawable.pizzaimg));
        category.add(new CategoryModel("Sandwich",R.drawable.sandwich));

        categoryAdapter = new CategoryAdapter(getActivity(), category);
        CategoryRecyclerView.setAdapter(categoryAdapter);
        LinearLayoutManager Categorymanager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        CategoryRecyclerView.setLayoutManager(Categorymanager);

        //annonce recycler view
        LinearLayoutManager Annoncemanager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        AnnonceRecyclerView.setLayoutManager(Annoncemanager);

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

                if (categoryAdapter.GetCategory().equals("Burger")){
                    postType1Adapter = new PostType1Adapter(getActivity(),BurgerProducts);
                    postType1Adapter.notifyDataSetChanged();
                }else if (categoryAdapter.GetCategory().equals("Pizza")){
                    postType1Adapter = new PostType1Adapter(getActivity(),PizzaProducts);
                    postType1Adapter.notifyDataSetChanged();
                }else if (categoryAdapter.GetCategory().equals("Sandwich")){
                    postType1Adapter = new PostType1Adapter(getActivity(),SandwichProducts);
                    postType1Adapter.notifyDataSetChanged();
                }else{
                    postType1Adapter = new PostType1Adapter(getActivity(),products);
                }

                ProductRecyclerView.setAdapter(postType1Adapter);
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