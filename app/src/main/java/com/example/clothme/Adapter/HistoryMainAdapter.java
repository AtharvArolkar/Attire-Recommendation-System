package com.example.clothme.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothme.Database.ClothesDB;
import com.example.clothme.MainActivity;
import com.example.clothme.Models.ClothesModel;
import com.example.clothme.Models.HistoryModel;
import com.example.clothme.Models.ImageModel;
import com.example.clothme.R;

import java.util.ArrayList;
public class HistoryMainAdapter extends RecyclerView.Adapter<HistoryMainAdapter.ViewHolder> {
    Context context;
    ArrayList<HistoryModel> list;

    public HistoryMainAdapter() {
    }

    public HistoryMainAdapter(ArrayList<HistoryModel> list, Context context) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_history_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryMainAdapter.ViewHolder holder, int position) {
//        Toast.makeText(context,"Hi",Toast.LENGTH_SHORT).show();
        holder.event.setText(list.get(position).getOccasion());
        holder.date.setText(list.get(position).getDate());
        holder.time.setText(list.get(position).getTime());
        holder.location.setText(list.get(position).getLocation());
//        Toast.makeText(context,list.get(position).getLocation(),Toast.LENGTH_SHORT).show();
        ArrayList<ImageModel> im=new ArrayList<>();
        String top=list.get(position).getTopId();
        String bottom=list.get(position).getBottomId();
        String outer=list.get(position).getOuterId();
        if(!top.equals("-")){
            im.add(getImageModel(top));
        }
        if(!bottom.equals("-")){
            im.add(getImageModel(bottom));
        }
        if(!outer.equals("-")){
            im.add(getImageModel(outer));
        }
        HistoryImageAdapter historyImageAdapter=new HistoryImageAdapter(im,context);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
        holder.imagePairs.setLayoutManager(layoutManager);
        holder.imagePairs.setAdapter(historyImageAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public ImageModel getImageModel(String clothId){
        ClothesDB db=new ClothesDB(context);
        ClothesModel cm=db.getCloth(MainActivity.user.getUsername(),clothId);
        Uri imageUri = Uri.parse(cm.getUri());
        Bitmap image=null;
        try {
            image = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ImageModel(image,clothId,cm.getClothtype());
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView imagePairs;
        TextView event,date,time,location;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePairs=itemView.findViewById(R.id.id_recycler_image_pairs);
            event=itemView.findViewById(R.id.id_occasion);
            date=itemView.findViewById(R.id.id_Date);
            time=itemView.findViewById(R.id.id_time);
            location=itemView.findViewById(R.id.id_location);
        }
    }
}
