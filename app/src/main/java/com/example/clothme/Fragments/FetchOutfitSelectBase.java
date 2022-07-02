package com.example.clothme.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.clothme.Adapter.FetchOutfitAdapter;
import com.example.clothme.Adapter.ImageAdapterAdd;
import com.example.clothme.Database.ClothesDB;
import com.example.clothme.MainActivity;
import com.example.clothme.Models.ClothesModel;
import com.example.clothme.Models.ImageModel;
import com.example.clothme.Models.ReturnClothLogicModelLists;
import com.example.clothme.R;
import com.example.clothme.Recommendation.ClothLogicFemale;
import com.example.clothme.Recommendation.ClothLogicMale;

import java.util.ArrayList;

public class FetchOutfitSelectBase extends Fragment implements FetchOutfitAdapter.OnClothClick{
    RecyclerView recyclerView;
    Button feeling_lucky;
    ArrayList<ClothesModel> images=null;
    ClothLogicMale cma;
    ClothLogicFemale cf;
    int temperature;
    String event,dateText,timeText,locationText,weatherText=null,dayTime;
    ArrayList<ImageModel> selectBaseImage=new ArrayList<>();
    int position=-1;
    public FetchOutfitSelectBase() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fetch_outfit_select_base, container, false);
        Bundle b=this.getArguments();
        dateText=b.getString("DateOfEvent");
        timeText=b.getString("TimeOfEvent");
        locationText=b.getString("LocationOfEvent");
        weatherText=b.getString("Weather");
        dayTime=b.getString("DayTime");
        recyclerView=view.findViewById(R.id.recycler_select_base);
        feeling_lucky=view.findViewById(R.id.bt_feeling_lucky);
        feeling_lucky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout linearDisplay=view.findViewById(R.id.linearDisplay);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                FetchOutfitViewOnGoing ff=new FetchOutfitViewOnGoing();
//                Bundle b=new Bundle();
                ArrayList<ReturnClothLogicModelLists> listOfPairs =null;
                if(MainActivity.user.getGender().equals("Male")){
                    listOfPairs=cma.generateSets(null,"feelinglucky");
                }else if(MainActivity.user.getGender().equals("Female")){
                    listOfPairs=cf.generateSets(null,"feelinglucky");
                }
//                Log.v("RRRsize",""+listOfPairs.size());
//                for(int i=0;i<listOfPairs.size();i++){
//                    Log.v("RRR",""+listOfPairs.get(i).getRankSum());
//                }
//                b.putInt("Temperature",Math.round(temperature));
//                b.putString("Event",eventSelected);
                Bundle b1=new Bundle();
                b1.putInt("Temperature",temperature);
                b1.putString("Event",event);
                b1.putString("DateOfEvent",dateText);
                b1.putString("TimeOfEvent",timeText);
                b1.putString("LocationOfEvent",locationText);
                b1.putString("Weather",weatherText);
                b1.putString("DayTime",dayTime);
                if(listOfPairs!=null){
                    b1.putSerializable("ListOfPairs",listOfPairs);
                }

                ff.setArguments(b1);
                transaction.replace(R.id.linearDisplay, ff);
                transaction.commit();
            }
        });
        if(b!=null){
            temperature=b.getInt("Temperature");
            event=b.getString("Event");
//            Toast.makeText(getContext(),""+temperature+" "+event,Toast.LENGTH_SHORT).show();

            ClothesDB db=new ClothesDB(getContext());
            images=db.getImageList(MainActivity.user.getUsername(),getContext());


            if(MainActivity.user.getGender().equals("Male")){
                cma=new ClothLogicMale();
//                Toast.makeText(getContext(),"Male",Toast.LENGTH_SHORT).show();
                selectBaseImage=cma.convertData(images,temperature,event,getContext());
            }else if(MainActivity.user.getGender().equals("Female")){
                cf=new ClothLogicFemale();
//                Toast.makeText(getContext(),"Female",Toast.LENGTH_SHORT).show();
                selectBaseImage=cf.convertData(images,temperature,event,getContext());
            }

            FrameLayout linearDisplay=view.findViewById(R.id.linearDisplay);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

            FetchOutfitAdapter adapter = new FetchOutfitAdapter(selectBaseImage, getContext(),transaction,this);
            recyclerView.setAdapter(adapter);
            StaggeredGridLayoutManager staggered = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggered);
        }
        return view;
    }

    @Override
    public void onClickCloth(int position) {
        ClothesDB db=new ClothesDB(getContext());
        ClothesModel cm=db.getCloth(MainActivity.user.getUsername(),selectBaseImage.get(position).getId());
        ArrayList<ReturnClothLogicModelLists> listOfPairs =null;
        if(MainActivity.user.getGender().equals("Male")){
            listOfPairs=cma.generateSets(cm.getId(),null);
        }else if(MainActivity.user.getGender().equals("Female")){
            listOfPairs=cf.generateSets(cm.getId(),null);
        }

        Log.v("Postion",""+position);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        FetchOutfitViewOnGoing ff=new FetchOutfitViewOnGoing();
        Bundle b1=new Bundle();
        b1.putInt("Temperature",temperature);
        b1.putString("Event",event);
        b1.putString("DateOfEvent",dateText);
        b1.putString("TimeOfEvent",timeText);
        b1.putString("LocationOfEvent",locationText);
        b1.putString("DayTime",dayTime);
        if(listOfPairs!=null){
            b1.putSerializable("ListOfPairs",listOfPairs);
        }
        b1.putString("Weather",weatherText);
        ff.setArguments(b1);
        transaction.replace(R.id.linearDisplay, ff);
        transaction.commit();

    }
}