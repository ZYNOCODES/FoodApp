package com.example.foodapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.foodapp.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

public class CartFragment extends Fragment {
    private View view;
    private MaterialCardView DeleveryNotesInPut;
    private CheckBox DeleveryCheckBox;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);

        //init
        InisializationOfFealds();
        ButtonRedirection();

        return view;
    }
    private void InisializationOfFealds(){
        DeleveryNotesInPut = view.findViewById(R.id.DeleveryNotesInPut);
        DeleveryCheckBox = view.findViewById(R.id.DeleveryCheckBox);
        DeleveryNotesInPut.setVisibility(View.GONE);
    }
    private void ButtonRedirection(){
        DeleveryCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DeleveryCheckBox.isChecked()){
                    DeleveryNotesInPut.setVisibility(View.VISIBLE);
                }else {
                    DeleveryNotesInPut.setVisibility(View.GONE);
                }
            }
        });
    }
}