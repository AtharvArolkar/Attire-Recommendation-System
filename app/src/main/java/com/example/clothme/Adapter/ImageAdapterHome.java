package com.example.clothme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothme.Models.ImageModel;
import com.example.clothme.R;

import java.util.ArrayList;

public class ImageAdapterHome extends  RecyclerView.Adapter<ImageAdapterHome.ViewHolder>{
    ArrayList<ImageModel> list;
    Context context;
    ImageView imageView;
    TextView textView;
    public ImageAdapterHome(View v){

    }
    public ImageAdapterHome(ArrayList<ImageModel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_image_home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageModel im=list.get(position);
        holder.imageView.setImageBitmap(im.getBitmap());
        holder.textView.setText(im.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.id_im);
            textView=itemView.findViewById(R.id.id_tv);
        }
    }
}
