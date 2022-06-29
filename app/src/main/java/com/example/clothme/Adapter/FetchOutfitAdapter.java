package com.example.clothme.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothme.Fragments.FetchOutfitViewOnGoing;
import com.example.clothme.Models.ImageModel;
import com.example.clothme.R;
import com.example.clothme.View_Wardrobe;

import java.util.ArrayList;

public class FetchOutfitAdapter extends  RecyclerView.Adapter<FetchOutfitAdapter.ViewHolder>{
    public interface OnClothClick{
        void onClickCloth(int position);
    }
    ArrayList<ImageModel> list;
    FragmentTransaction transaction;
    Context context;
    ImageView imageView;
    TextView textView;
    OnClothClick monClothClick;
    public FetchOutfitAdapter(View v){
    }
    public FetchOutfitAdapter(ArrayList<ImageModel> list,Context context ,FragmentTransaction transaction,OnClothClick onClothClick) {
        this.list = list;
        this.transaction = transaction;
        this.context=context;
        this.monClothClick=onClothClick;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_image,parent,false);
        return new ViewHolder(view,monClothClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageModel im=list.get(position);
        holder.imageView.setImageBitmap(im.getBitmap());
        holder.textView.setText(im.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchOutfitViewOnGoing ff=new FetchOutfitViewOnGoing();
                Bundle b=new Bundle();
                monClothClick.onClickCloth(position);
//                b.putInt("Temperature",Math.round(temperature));
//                b.putString("Event",eventSelected);
//                ff.setArguments(b);
//                transaction.replace(R.id.linearDisplay, ff);
//                transaction.commit();
//                Intent i= new Intent(v.getContext(), View_Wardrobe.class);
//                i.putExtra("clothId",im.getId());
////                Log.v("AAA1",im.getId());
//                i.putExtra("position",holder.getAdapterPosition());
//                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        OnClothClick onClothClick;
        public ViewHolder(@NonNull View itemView ,OnClothClick onClothClick) {
            super(itemView);
            imageView=itemView.findViewById(R.id.id_im);
            textView=itemView.findViewById(R.id.id_tv);
            this.onClothClick=onClothClick;
        }
    }

}
