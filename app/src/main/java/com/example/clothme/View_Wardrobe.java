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
    String[] TypeCat = new String[]{"Tshirts", "Kurtas", "Suits", "Coats", "Cardigans", "Sweatshirts", "Vests", "Tops", "Hoodies", "Sweaters", "Shirts", "Sherwanis",
            "Jackets", "Innerwear Vests", "Tank Tops", "Gowns", "saree", "Churidar", "Dresses", "Jumpsuits", "Blazers", "T-Shirts", "Tunics", "Long Sleeves Shirt",
            "dhoti_pants", "Lounge Pants", "Tracksuits", "Shirts", "Long Skirts", "Tights", "Shorts", "Knee Length Skirts", "Pajama",
            "Mini Skirts", "Capri & Cropped Pants", "Jeans", "Pants", "Skirts", "Leggings", "Track Pants", "Pajamas", "palazzos", "Trousers"};
    String[] FabricCat = new String[]{"Cotton", "Wool", "Nylon/Polyester", "Silk"};
    String[] ColorCat = new String[]{"Null","Red","Blue","Green","Cyan","Magenta","Yellow"};
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

        ArrayAdapter<String> typeAdapter=new ArrayAdapter<>(View_Wardrobe.this, android.R.layout.simple_spinner_dropdown_item,TypeCat);
        type.setAdapter(typeAdapter);
        int pos=Arrays.asList(TypeCat).indexOf(cm.getClothtype());
        Toast.makeText(this,""+pos,Toast.LENGTH_SHORT).show();
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
        typeAdapter = new ArrayAdapter<>(View_Wardrobe.this, android.R.layout.simple_spinner_dropdown_item, ColorCat);
        color.setAdapter(typeAdapter);
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