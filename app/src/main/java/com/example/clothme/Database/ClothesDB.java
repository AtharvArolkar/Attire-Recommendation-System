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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ClothesDB extends SQLiteOpenHelper {
    public static final String dbname = "database.db";
    public static final String TABLE_NAME = "clothesimage";
    public static final String TABLE_NAME_PARENT = "Accounts";
    String[] TopWear = new String[]{"Blazers","Cardigans","Jumpsuits","Tracksuits","T-Shirts","Tops","Shirts","Long Sleeves Shirt",
            "Innerwear Vests","Tank Tops","Hoodies","Suits","Shirt","Dresses","Gowns","Coats","Sherwanis","Jackets","Sweaters","Sweatshirts","Vests"};


    String[] BottomWear = new String[]{"Pajama","Capri & Cropped Pants","Track Pants","Jeans","Tights","Shorts","Skirts","Lounge Pants","Trousers","Pants","Leggings"};

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

    public boolean insertData(String username, Uri path, String Clothtype, String color, String fabric) {
//        String s = ur.toString();
        SQLiteDatabase Mydb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
//        cv.put("address", s);
        cv.put("username", username);
        cv.put("imageURI", path.toString());
        cv.put("ClothType", Clothtype);
        cv.put("color", color);
        cv.put("fabric", fabric);
        if (Arrays.asList(TopWear).contains(Clothtype)) {
            cv.put("category", "topwear");
        } else {
            cv.put("category", "bottomwear");
        }
        long ins = Mydb.insert(TABLE_NAME, null, cv);
        if (ins == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<ImageModel> getImage(String username, String category, Context context) {
        ArrayList<ImageModel> images = new ArrayList<>();
        SQLiteDatabase MyDB = ClothesDB.this.getReadableDatabase();
        final Cursor cursor = MyDB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        try {

            while (cursor.moveToNext()) {
                if (cursor.getString(0).equals(username)) {
                    ImageModel im = new ImageModel();
                    im.setText(cursor.getString(2));
//                    byte[] img = cursor.getBlob(1);
                    Uri imageUri = Uri.parse(cursor.getString(1));
                    Bitmap image = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                    im.setPic(image);
                    if (cursor.getString(5).equals(category)) {
                        images.add(im);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
        cursor.close();
        MyDB.close();
        return images;
    }

    public ClothesModel getCloth(String username, String Clothtype, int position) {
        SQLiteDatabase db = this.getReadableDatabase();//
        String cat = null;
        if (Arrays.asList(TopWear).contains(Clothtype)) {
            cat = "topwear";
        } else {
            cat = "bottomwear";
        }
        final Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE username=? AND category=?", new String[]{username, cat});//
        int pos = 0;
        String uri = null;
        String clothtype = null;
        String color = null;
        String fabric = null;
        String category = null;
        while (cursor.moveToNext()) {
            if (pos <= position) {
                uri = cursor.getString(1);
                clothtype = cursor.getString(2);
                color = cursor.getString(3);
                fabric = cursor.getString(4);
                category = cursor.getString(5);
            }
            pos++;
        }

        ClothesModel cm = new ClothesModel(username, uri, clothtype, color, fabric, category);
        cursor.close();
        db.close();
        return cm;
    }

    public void updateCloth(ClothesModel cm) {
        SQLiteDatabase db = this.getReadableDatabase();//
        String cat = null;
        ContentValues cn=new ContentValues();
        cn.put("ClothType",cm.getClothtype());
        cn.put("color",cm.getColor());
        cn.put("fabric",cm.getFabric());
        String cat1 = null;
        if (Arrays.asList(TopWear).contains(cm.getClothtype())) {
            cat = "topwear";
        } else {
            cat = "bottomwear";
        }
        cn.put("category",cat);
        long r=db.update(TABLE_NAME,cn,"username =? AND imageUri=?",new String[]{cm.getUsername(), cm.getUri()});
    }
}

