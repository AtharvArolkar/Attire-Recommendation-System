package com.example.clothme.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.clothme.MainActivity;
import com.example.clothme.Models.ClothesModel;
import com.example.clothme.Models.ImageModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ClothesDB extends SQLiteOpenHelper {
    public static final String dbname = "database.db";
    public static final String TABLE_NAME = "clothesimage";
    public static final String TABLE_NAME_PARENT = "Accounts";
    private static final int startID = 12345;
    String[] TopWear = new String[]{"Cardigans", "Jumpsuits", "T-Shirt", "Top", "Shirt", "Long Sleeves Shirt",
            "Innerwear Vests", "Tank Top", "Hoodies", "Suits", "Shirt", "Dresses", "Gown", "Coats", "Sweatshirts", "Vests"
    ,"Long Dress","Long Sleeved Dress","Short Dress","Short Sleeveless Dress","Sleeveless Top"};
    String[] OuterWear=new String[]{"Coat","Blazer","Jacket","Tracksuits","Sweater","Hoodies","Shawl"};
    String[] BottomWear = new String[]{"Pajama", "Capri & Cropped Pants", "Track Pants", "Jeans", "Tights", "Shorts", "Skirt", "Lounge Pants", "Trousers", "Pants", "Leggings"
    ,"Pencil Skirt","Short skirt"};

    public ClothesDB(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        Log.d("my","qs");
//        String sql="create table " +TABLE_NAME+ " (username text,image blob, ClothType text, color text," +
//                "FOREIGN KEY(username) REFERENCES Accounts(username))";
//        Log.d("mymy",sql);
//        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
    }

    public boolean insertData(String username, Uri path, String Clothtype, String color, String fabric ,String pattern) {
//        String s = ur.toString();
        SQLiteDatabase Mydb = ClothesDB.this.getWritableDatabase();
        ContentValues cv = new ContentValues();
//        cv.put("address", s);
        cv.put("username", username);
        cv.put("imageID", String.valueOf(getID()));
        cv.put("imageURI", path.toString());
        cv.put("ClothType", Clothtype);
        cv.put("color", color);
        cv.put("fabric", fabric);
        cv.put("pattern",pattern);
        if (Arrays.asList(TopWear).contains(Clothtype)) {
            cv.put("category", "topwear");
        } else if(Arrays.asList(BottomWear).contains(Clothtype)){
            cv.put("category", "bottomwear");
        }else{
            cv.put("category", "outerwear");
        }
        long ins = Mydb.insert(TABLE_NAME, null, cv);
        Mydb.close();
        if (ins == -1) {
            return false;
        } else {
            return true;
        }

    }
//    public Bitmap getPic(String uri){
//        Bitmap image=MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
//    }
    public ArrayList<ImageModel> getImage(String username, String category, Context context) {
        ArrayList<ImageModel> images = new ArrayList<>();
        SQLiteDatabase MyDB = ClothesDB.this.getReadableDatabase();
        final Cursor cursor = MyDB.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        try {
            ImageModel im = null;
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equals(username)) {
                    im = new ImageModel();
                    im.setText(cursor.getString(3));
//                    byte[] img = cursor.getBlob(1);
                    Uri imageUri = Uri.parse(cursor.getString(2));
//                    Log.v("AAA",imageUri.toString());
//                    File f=new File(imageUri.getPath());
//                    if(f.exists()){

//                    }
                    Bitmap image = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                    im.setPic(image);
                    im.setId(cursor.getString(1));
                    if (category != null) {
                        if (cursor.getString(6).equals(category)) {
                            images.add(im);
                        }
                    }else{
                        images.add(im);
                    }
                }
            }
//            Log.v("AAA",im.getName());
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
        cursor.close();
        MyDB.close();
        Log.v("AAA", Integer.toString(images.size()));
        return images;
    }
    public ArrayList<ClothesModel> getImageList(String username, Context context) {
        ArrayList<ClothesModel> images = new ArrayList<>();
        SQLiteDatabase MyDB = ClothesDB.this.getReadableDatabase();
        final Cursor cursor = MyDB.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        try {
            ClothesModel im = null;
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equals(username)) {
//                    im = new ClothesModel();
                    String id = cursor.getString(1);
                    String uri = cursor.getString(2);
                    String clothtype = cursor.getString(3);
                    String color = cursor.getString(4);
                    String fabric = cursor.getString(5);
                    String cat = cursor.getString(6);
                    String pattern=cursor.getString(7);
                    String lastworn=cursor.getString(8);
//                    if(cat==category){
                        im= new ClothesModel(username, id, uri, clothtype, color, fabric, cat,pattern,lastworn);
                        images.add(im);
//                    }
                }
            }
//            Log.v("AAA",im.getName());
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
        cursor.close();
        MyDB.close();
        Log.v("AAA", Integer.toString(images.size()));
        return images;
    }
    public int getID() {
        int id = startID;
        String sql = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = ClothesDB.this.getReadableDatabase();
        final Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            id++;
        }
        cursor.close();
        return id;
    }

    public ClothesModel getCloth(String username,String reqID) {
        SQLiteDatabase db = ClothesDB.this.getReadableDatabase();
        final Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE imageID=?", new String[]{reqID});//
        int pos = 0;
        String id = null;
        String uri = null;
        String clothtype = null;
        String color = null;
        String fabric = null;
        String category = null;
        ClothesModel cm = null;
        while (cursor.moveToNext()) {
            Log.v("AAA1", String.valueOf(cursor.getCount()));
            id = cursor.getString(1);
            uri = cursor.getString(2);
            clothtype = cursor.getString(3);
            color = cursor.getString(4);
            fabric = cursor.getString(5);
            category = cursor.getString(6);
            String pattern=cursor.getString(7);
            String lastworn=cursor.getString(8);
            cm = new ClothesModel(username, id, uri, clothtype, color, fabric, category,pattern,lastworn);
        }
//        while (cursor.moveToNext()) {
//            if (pos <= position) {
//
//            }
//            pos++;
//        }


//        Log.
        cursor.close();
        db.close();
        return cm;
    }

    public void updateCloth(ClothesModel cm) {
        SQLiteDatabase db = this.getReadableDatabase();//
        String cat = null;
        ContentValues cn = new ContentValues();
        cn.put("ClothType", cm.getClothtype());
        cn.put("color", cm.getColor());
        cn.put("fabric", cm.getFabric());
        cn.put("pattern",cm.getPattern());
        if (Arrays.asList(TopWear).contains(cm.getClothtype())) {
            cat = "topwear";
        } else if(Arrays.asList(BottomWear).contains(cm.getClothtype())){
            cat = "bottomwear";
        }else{
            cat = "outerwear";
        }
        cn.put("category", cat);
        long r = db.update(TABLE_NAME, cn, "username =? AND imageUri=?", new String[]{cm.getUsername(), cm.getUri()});
    }
    public void updateLastWorn(String date,String clothId){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cn = new ContentValues();
        cn.put("lastworn",date);
        long r = db.update(TABLE_NAME, cn, "imageID =?", new String[]{clothId});
    }
    public void deleteCloth(String imageId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME ,"imageID =?",new String[]{imageId});
    }
}

