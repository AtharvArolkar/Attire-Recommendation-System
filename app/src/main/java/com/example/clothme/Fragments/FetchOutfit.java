package com.example.clothme.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.clothme.R;

public class FetchOutfit extends Fragment {
    FrameLayout linearDisplay;
    public FetchOutfit() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fetch_outfit, container, false);

        linearDisplay=view.findViewById(R.id.linearDisplay);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        FetchOutfitInputDetails ff=new FetchOutfitInputDetails();
        transaction.replace(R.id.linearDisplay, ff);
        transaction.commit();
        return view;
    }
}