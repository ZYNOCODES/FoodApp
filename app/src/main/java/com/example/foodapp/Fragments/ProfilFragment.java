package com.example.foodapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Adapters.MyOrderAdapter;
import com.example.foodapp.AuthentificationActivity;
import com.example.foodapp.DisplayOrderActivity;
import com.example.foodapp.Models.Order;
import com.example.foodapp.Models.User;
import com.example.foodapp.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfilFragment extends Fragment {
    private View view;
    private ImageView ProfileIMGOutPut;
    private TextView ClientFullName, ClientPhoneNumber, ClientEmail;
    private MaterialCardView ResetPasswordBTN, UpdateProfilBTN, LogOutBTN;
    private DatabaseReference Refuser;
    private FirebaseAuth Auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profil, container, false);

        //init
        InisializationOfFealds();
        ButtonRedirection();

        //fetch data
        fetchDataFromDB();

        return view;
    }
    private void InisializationOfFealds(){
        UpdateProfilBTN = view.findViewById(R.id.UpdateProfilBTN);
        ResetPasswordBTN = view.findViewById(R.id.ResetPasswordBTN);
        ClientEmail = view.findViewById(R.id.ClientEmail);
        ClientPhoneNumber = view.findViewById(R.id.ClientPhoneNumber);
        ClientFullName = view.findViewById(R.id.ClientFullName);
        ProfileIMGOutPut = view.findViewById(R.id.ProfileIMGOutPut);
        LogOutBTN = view.findViewById(R.id.LogOutBTN);
        Auth = FirebaseAuth.getInstance();
        Refuser = FirebaseDatabase.getInstance(getString(R.string.DBURL))
                .getReference()
                .child("Users")
                .child(Auth.getCurrentUser().getUid());
    }
    private void ButtonRedirection() {
        UpdateProfilBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                        .replace(R.id.MainFragmentContainer, new ProfilUpdateFragment())
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        LogOutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.signOut();
                Intent i = new Intent(getActivity(), AuthentificationActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
    }
    private void fetchDataFromDB(){
        Refuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    if (user.getPhone() != null && user.getFullName() != null){
                        ClientPhoneNumber.setText(user.getPhone().toString());
                        ClientFullName.setText(user.getFullName().toString());
                        ClientEmail.setText(user.getEmail().toString());
                        if (user.getIMG() > 0){
                            Picasso.get().load(user.getIMG()).into(ProfileIMGOutPut);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "Operation canceled", error.toException());
                Toast.makeText(getActivity(), "Database operation canceled: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}