package com.example.clothme.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.clothme.Adapter.ImageAdapterAdd;
import com.example.clothme.Database.ClothesDB;
import com.example.clothme.MainActivity;
import com.example.clothme.Models.ClothesModel;
import com.example.clothme.Models.ImageModel;
import com.example.clothme.R;
import com.example.clothme.Recommendation.ClothLogic;

import java.util.ArrayList;

public class FetchOutfitSelectBase extends Fragment {
    RecyclerView recyclerView;
    public FetchOutfitSelectBase() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fetch_outfit_select_base, container, false);
        Bundle b=this.getArguments();
        recyclerView=view.findViewById(R.id.recycler_select_base);
        if(b!=null){
            int temperature=b.getInt("Temperature");
            String event=b.getString("Event");
            Toast.makeText(getContext(),""+temperature+" "+event,Toast.LENGTH_SHORT).show();
            ClothLogic c=new ClothLogic();
            ClothesDB db=new ClothesDB(getContext());
            ArrayList<ClothesModel> images=db.getImageList(MainActivity.user.getUsername(),getContext());
            ArrayList<ImageModel> selectBaseImage=c.convertData(images,temperature,event,getContext());
            ImageAdapterAdd adapter = new ImageAdapterAdd(selectBaseImage, getContext());
            recyclerView.setAdapter(adapter);
            StaggeredGridLayoutManager staggered = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggered);
        }
        return view;
    }
}