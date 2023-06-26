package com.example.foodapp.Fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfilUpdateFragment extends Fragment {
    private View view;
    private ImageView ProfileIMGOutPut, BackBTN;
    private TextView ClientFullName, ClientPhoneNumber, ClientEmail;
    private MaterialCardView UpdateProfilBTN;
    private CardView UpdateIMGBTN;
    private DatabaseReference Refuser;
    private FirebaseAuth Auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profil_update, container, false);

        //init
        InisializationOfFealds();
        BackBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        //update profile
        UpdateProfil();
        return view;
    }
    private void InisializationOfFealds(){
        UpdateProfilBTN = view.findViewById(R.id.UpdateProfilBTN);
        ClientEmail = view.findViewById(R.id.ClientEmail);
        ClientPhoneNumber = view.findViewById(R.id.ClientPhoneNumber);
        ClientFullName = view.findViewById(R.id.ClientFullName);
        ProfileIMGOutPut = view.findViewById(R.id.ProfileIMGOutPut);
        UpdateIMGBTN = view.findViewById(R.id.UpdateIMGBTN);
        BackBTN = view.findViewById(R.id.BackBTN);
        Auth = FirebaseAuth.getInstance();
        Refuser = FirebaseDatabase.getInstance(getString(R.string.DBURL))
                .getReference()
                .child("Users")
                .child(Auth.getCurrentUser().getUid());
    }
    private void UpdateProfil() {
        UpdateProfilBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}