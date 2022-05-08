package com.example.clothme.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.clothme.R;

public class FetchOutfitViewOnGoing extends Fragment {
    Button back;
    public FetchOutfitViewOnGoing() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_fetch_outfit_view_on_going, container, false);
        back=view.findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout linearDisplay=view.findViewById(R.id.linearDisplay);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                FetchOutfitInputDetails ff=new FetchOutfitInputDetails();
                transaction.replace(R.id.linearDisplay, ff);
                transaction.commit();
            }
        });
        return view;
    }
}