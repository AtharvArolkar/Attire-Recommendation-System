package com.example.clothme.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.clothme.Database.ClothesDB;
import com.example.clothme.Fragments.BottomWearFragment;
import com.example.clothme.MainActivity;
import com.example.clothme.Models.ImageModel;
import com.example.clothme.R;
import com.example.clothme.View_Wardrobe;

import java.util.ArrayList;

public class ImageAdapter extends  RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    public interface OnLongClick{
        void onLongCloth(int position);
    }
    ArrayList<ImageModel> list;
    Context context;
    ImageAdapter context1=this;
    ImageView imageView;
    TextView textView;
    String category;
    RecyclerView displayImages;

    public ImageAdapter(View v) {

    }

    public ImageAdapter(ArrayList<ImageModel> list, Context context ,RecyclerView displayImages,String category) {
        this.list = list;
        this.context = context;
        this.displayImages=displayImages;
        this.category=category;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ImageModel im = list.get(position);
        holder.imageView.setImageBitmap(im.getBitmap());
        holder.textView.setText(im.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), View_Wardrobe.class);
                i.putExtra("clothId", im.getId());
//                Log.v("AAA1",im.getId());
                i.putExtra("position", holder.getAdapterPosition());
                v.getContext().startActivity(i);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(context);
                builder.setMessage("Are You Sure ?");
                builder.setTitle("Delete Selected Item");
                builder.setCancelable(false);
                builder
                        .setPositiveButton(
                                "Yes",
                                new DialogInterface
                                        .OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        ClothesDB db=new ClothesDB(context);
                                        db.deleteCloth(list.get(position).getId());
                                        db=new ClothesDB(context);
                                        ArrayList<ImageModel> bottom_wear_images=db.getImage(MainActivity.user.getUsername(),category,context);
                                        ImageAdapter adapter=new ImageAdapter(bottom_wear_images, context,displayImages,category);
                                        displayImages.setAdapter(adapter);
                                        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
                                        displayImages.setLayoutManager(staggered);
                                        // When the user click yes button
                                        // then app will close
//                                        finish();
                                    }
                                });
                builder
                        .setNegativeButton(
                                "No",
                                new DialogInterface
                                        .OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.id_im);
            textView = itemView.findViewById(R.id.id_tv);
        }
    }
}
