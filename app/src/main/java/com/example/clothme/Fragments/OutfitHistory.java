package com.example.clothme.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.clothme.Adapter.HistoryMainAdapter;
import com.example.clothme.Database.HistoryDB;
import com.example.clothme.MainActivity;
import com.example.clothme.Models.HistoryModel;
import com.example.clothme.R;

import java.util.ArrayList;
import java.util.Collections;


public class OutfitHistory extends Fragment {
    RecyclerView recyclerView;
    public OutfitHistory() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_outfit_history, container, false);
        recyclerView=view.findViewById(R.id.id_history_display);
        HistoryDB db=new HistoryDB(getContext());
        ArrayList<HistoryModel> hm=db.fetchHistory(MainActivity.user.getUsername());
        Collections.reverse(hm);
//        Toast.makeText(getContext(),""+hm.size(),Toast.LENGTH_SHORT).show();
        HistoryMainAdapter adapter=new HistoryMainAdapter(hm,getContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}