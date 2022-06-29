package com.example.clothme.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.clothme.Adapter.ImageAdapter;
import com.example.clothme.Database.ClothesDB;
import com.example.clothme.MainActivity;
import com.example.clothme.Models.ImageModel;
import com.example.clothme.R;

import java.util.ArrayList;


public class BottomWearFragment extends Fragment {

    RecyclerView displayImages;
    public BottomWearFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bottom_wear, container, false);

        displayImages=view.findViewById(R.id.recycler_bottom_wear);
        ClothesDB db=new ClothesDB(getContext());
        ArrayList<ImageModel> bottom_wear_images=db.getImage(MainActivity.user.getUsername(),"bottomwear",getContext());
        ImageAdapter adapter=new ImageAdapter(bottom_wear_images, getContext(),displayImages,"bottomwear");
        displayImages.setAdapter(adapter);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        displayImages.setLayoutManager(staggered);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ClothesDB db=new ClothesDB(getContext());
        ArrayList<ImageModel> bottom_wear_images=db.getImage(MainActivity.user.getUsername(),"bottomwear",getContext());
        ImageAdapter adapter=new ImageAdapter(bottom_wear_images, getContext(),displayImages,"bottomwear");
        displayImages.setAdapter(adapter);
        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        displayImages.setLayoutManager(staggered);
    }
}