package com.example.clothme.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clothme.Adapter.ImageAdapter;
import com.example.clothme.Database.ClothesDB;
import com.example.clothme.MainActivity;
import com.example.clothme.Models.ImageModel;
import com.example.clothme.R;

import java.util.ArrayList;


public class OuterWearFragment extends Fragment {
    RecyclerView displayImages;
    public OuterWearFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_outer_wear, container, false);
        // Inflate the layout for this fragment
        displayImages=view.findViewById(R.id.recylcer_outer_wear);
        ClothesDB db=new ClothesDB(getContext());
        ArrayList<ImageModel> outer_wear_images=db.getImage(MainActivity.user.getUsername(),"outerwear",getContext());
        ImageAdapter adapter=new ImageAdapter(outer_wear_images, getContext(),displayImages,"outerwear");
        displayImages.setAdapter(adapter);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        ClothesDB db=new ClothesDB(getContext());
        ArrayList<ImageModel> outerwear_wear_images=db.getImage(MainActivity.user.getUsername(),"outerwear",getContext());
        ImageAdapter adapter=new ImageAdapter(outerwear_wear_images, getContext(),displayImages,"outerwear");
        displayImages.setAdapter(adapter);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        displayImages.setLayoutManager(staggered);
    }
}