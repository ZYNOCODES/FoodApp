package com.example.foodapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapp.Adapters.AnnonceAdapter;
import com.example.foodapp.Adapters.CategoryAdapter;
import com.example.foodapp.Adapters.PostType1Adapter;
import com.example.foodapp.Adapters.PostType2Adapter;
import com.example.foodapp.Models.AnnonceModel;
import com.example.foodapp.Models.CategoryModel;
import com.example.foodapp.Models.PostType1Model;
import com.example.foodapp.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private View view;
    private RecyclerView AnnonceRecyclerView,CategoryRecyclerView, ProductRecyclerView, AllProductRecyclerView;
    private ArrayList<PostType1Model> PostsType1, PostsType2;
    private ArrayList<CategoryModel> category;
    private ArrayList<AnnonceModel> annonce;
    private PostType1Adapter postType1Adapter;
    private PostType2Adapter postType2Adapter;
    private CategoryAdapter categoryAdapter;
    private AnnonceAdapter annonceAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        InisializationOfFealds();

        //annonce recycler view
        annonce = new ArrayList<>();

        annonce.add(new AnnonceModel(R.drawable.burger4,"Special Offer\nfor March"));
        annonce.add(new AnnonceModel(R.drawable.burger4,"Special Offer\nfor April"));
        annonce.add(new AnnonceModel(R.drawable.burger4,"Special Offer\nfor June"));

        annonceAdapter = new AnnonceAdapter(getActivity(), annonce);
        AnnonceRecyclerView.setAdapter(annonceAdapter);

        LinearLayoutManager Annoncemanager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        AnnonceRecyclerView.setLayoutManager(Annoncemanager);

        //category recycler view
        category = new ArrayList<>();

        category.add(new CategoryModel("Burger",R.drawable.burgerimg));
        category.add(new CategoryModel("Pizza",R.drawable.pizzaimg));
        category.add(new CategoryModel("Sandwich",R.drawable.sandwich));

        categoryAdapter = new CategoryAdapter(getActivity(), category);
        CategoryRecyclerView.setAdapter(categoryAdapter);

        LinearLayoutManager Categorymanager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        CategoryRecyclerView.setLayoutManager(Categorymanager);

        //post type 1 recycler view
        PostsType1 = new ArrayList<>();

        PostsType1.add(new PostType1Model(R.drawable.burger,"Chicken burger","100 gr chicken + tomato + cheese  Lettuce","200","description","burger"));
        PostsType1.add(new PostType1Model(R.drawable.burger2,"Chicken burger","100 gr chicken + tomato + cheese  Lettuce","200","description","burger"));
        PostsType1.add(new PostType1Model(R.drawable.burger3,"Chicken burger","100 gr chicken + tomato + cheese  Lettuce","200","description","burger"));
        PostsType1.add(new PostType1Model(R.drawable.pizza,"Chicken pizza","100 gr chicken + tomato + cheese  Lettuce","200","description","burger"));

        postType1Adapter = new PostType1Adapter(getActivity(), PostsType1);
        ProductRecyclerView.setAdapter(postType1Adapter);

        LinearLayoutManager PostType1manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        ProductRecyclerView.setLayoutManager(PostType1manager);

        //post type 2 recycler view
        PostsType2 = new ArrayList<>();

        PostsType2.add(new PostType1Model(R.drawable.pizza,"Chicken pizza","100 gr chicken + tomato + cheese  Lettuce","200","description","burger"));
        PostsType2.add(new PostType1Model(R.drawable.burger,"Chicken burger","100 gr chicken + tomato + cheese  Lettuce","200","description","burger"));
        PostsType2.add(new PostType1Model(R.drawable.burger,"Chicken burger","100 gr chicken + tomato + cheese  Lettuce","200","description","burger"));

        postType2Adapter = new PostType2Adapter(getActivity(), PostsType2);
        AllProductRecyclerView.setAdapter(postType2Adapter);

        LinearLayoutManager PostType2manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        AllProductRecyclerView.setLayoutManager(PostType2manager);
        return view;
    }
    private void InisializationOfFealds(){
        AnnonceRecyclerView = view.findViewById(R.id.AnnonceRecyclerView);
        CategoryRecyclerView = view.findViewById(R.id.CategoryRecyclerView);
        ProductRecyclerView = view.findViewById(R.id.ProductRecyclerView);
        AllProductRecyclerView = view.findViewById(R.id.AllProductRecyclerView);
    }
}