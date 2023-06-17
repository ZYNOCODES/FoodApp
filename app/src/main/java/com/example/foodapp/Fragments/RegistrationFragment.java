package com.example.foodapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodapp.R;
import com.google.android.material.card.MaterialCardView;

public class RegistrationFragment extends Fragment {
    private View view;
    private MaterialCardView SignInWithPassword;
    private TextView ToSignUpInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration, container, false);
        InisializationOfFealds();
        ButtonRedirection();

        return view;
    }
    private void InisializationOfFealds(){
        SignInWithPassword = view.findViewById(R.id.SignInWithPassword);
        ToSignUpInterface = view.findViewById(R.id.ToSignUpInterface);
    }
    private void ButtonRedirection(){
        SignInWithPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                        .replace(R.id.AuthentificationFragmentContainer, new LogInFragment())
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        ToSignUpInterface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                        .replace(R.id.AuthentificationFragmentContainer, new SingupFragment())
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

}