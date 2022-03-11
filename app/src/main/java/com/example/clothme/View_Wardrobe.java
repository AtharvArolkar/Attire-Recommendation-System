package com.example.clothme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.clothme.Adapter.SpinnerAdapter;
import com.example.clothme.Database.ClothesDB;
import com.example.clothme.Models.ClothesModel;

import java.io.File;
import java.util.Arrays;

public class View_Wardrobe extends AppCompatActivity {
    Spinner type,fabric,color;
    String newType,newColor,newFabric;
    Button cancel,update;
    ImageView im;
    ClothesModel cm;
    String[] TypeCat = new String[]{"Blazers","Cardigans","Jumpsuits","Tracksuits","T-Shirts","Tops","Shirts","Long Sleeves Shirt",
            "Innerwear Vests","Tank Tops","Hoodies","Suits","Shirt","Dresses","Gowns","Coats","Sherwanis","Jackets","Sweaters",
            "Sweatshirts","Vests","Pajama","Capri & Cropped Pants","Track Pants","Jeans","Tights","Shorts","Skirts","Lounge Pants","Trousers","Pants","Leggings"};
    String[] FabricCat = new String[]{"Cotton", "Wool", "Nylon/Polyester", "Silk",};
    String[] ColorCat = new String[]{"Brown","Dark Khaki","White_2","Apricot","White_3","White_4","Orange","White_1","Lime","Beige","Teal","Maroon","Cyan",
            "Violet","Blue_1","Blue","Mint","Grey_6","Magenta","Grey_4","Grey_5","Navy Blue","Grey_2","Grey_3","Grey_1","Red","Olive","Dark Green","Pink",
            "Khaki","Yellow","Purple","Black_4","Black_5","Black_6","Black_7","Black_1","Green","Black_2","Black_3","NULL"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_wardrobe);
        getSupportActionBar().hide();
        type=findViewById(R.id.typeSpinner);
        fabric=findViewById(R.id.fabricSpinner);
        color=findViewById(R.id.colorSpinner);
        update=findViewById(R.id.buttonUpdate);
        cancel=findViewById(R.id.cancel);
        im=findViewById(R.id.imageViewCloth);
        Intent i=getIntent();
        ClothesDB db=new ClothesDB(this);
        cm=db.getCloth(MainActivity.user.getUsername(),i.getExtras().getString("cloth"),i.getExtras().getInt("position"));
        final String path = getPathFromURI(Uri.parse(cm.getUri()));
        File f = new File(path);
        Uri uri = Uri.fromFile(f);
        im.setImageURI(uri);

//        Toast.makeText(this,""+cm.getColor(),Toast.LENGTH_SHORT).show();

        ArrayAdapter<String> typeAdapter=new ArrayAdapter<>(View_Wardrobe.this, android.R.layout.simple_spinner_dropdown_item,TypeCat);
        type.setAdapter(typeAdapter);
        int pos=Arrays.asList(TypeCat).indexOf(cm.getClothtype());
//        Toast.makeText(this,""+pos,Toast.LENGTH_SHORT).show();
        type.setSelection(pos);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newType=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//
        typeAdapter=new ArrayAdapter<>(View_Wardrobe.this, android.R.layout.simple_spinner_dropdown_item,FabricCat);
        fabric.setAdapter(typeAdapter);
        pos=Arrays.asList(FabricCat).indexOf(cm.getFabric());
        fabric.setSelection(pos);
        fabric.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newFabric=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        typeAdapter = new ArrayAdapter<>(View_Wardrobe.this, android.R.layout.simple_spinner_dropdown_item, ColorCat);
        SpinnerAdapter colorAdapter=new SpinnerAdapter(this,ColorCat);
        color.setAdapter(colorAdapter);
        pos=Arrays.asList(ColorCat).indexOf(cm.getColor());
        color.setSelection(pos);
        color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newColor=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClothesModel cm1=new ClothesModel(cm.getUsername(),cm.getUri(),newType,newColor,newFabric,null);
                db.updateCloth(cm1);
                setResult(RESULT_OK, null);
                finish();
            }
        });

    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}