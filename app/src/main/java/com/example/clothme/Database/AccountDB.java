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

import com.example.clothme.Models.UserModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AccountDB extends SQLiteOpenHelper {
    public static final String dbname="database.db";
    public static final String TABLE_NAME="Accounts";
    public static final String TABLE_NAME1="clothesimage";
    public AccountDB(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table "+TABLE_NAME+"(username text primary key, fname text,lname text, gender text,age text,password text," +
                "profilepic text, loggedIn integer)";
        String sql1="create table " +TABLE_NAME1+ " (username text,imageUri text, ClothType text, color text, fabric text,category text," +
                "FOREIGN KEY(username) REFERENCES Accounts(username))";
        db.execSQL(sql);
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }
    public Boolean checkAvailability(String username){
        Boolean available=true;
        String sql="SELECT * FROM " + TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.rawQuery(sql,null);
        Log.d("Entered","2");
        while(cursor.moveToNext()){//iterate
            String name=cursor.getString(0);
            if(name.equals(username)){
                available=false;
            }
        }
        cursor.close();
        db.close();
        return available;
    }
    public Boolean createAcc(UserModel user){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues cn=new ContentValues();
        cn.put("username",user.getUsername());
        cn.put("fname",user.getFname());
        cn.put("lname",user.getLname());
        cn.put("gender",user.getGender());
        cn.put("age",user.getAge());
        cn.put("password",user.getPassword());

//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        user.getProfilePic().compress(Bitmap.CompressFormat.PNG, 0, stream);
//        byte[] image=stream.toByteArray();

        cn.put("profilepic",user.getProfilePic().toString());
        cn.put("loggedIn ",0);

        long result=db.insert(TABLE_NAME,null,cn);
        db.close();
        if(result==-1){
            return  false;
        }else{
            return  true;
        }
    }
    public UserModel getAcc(String username){
        String sql="SELECT * FROM " + TABLE_NAME ;
        UserModel user=new UserModel();
        SQLiteDatabase db=this.getReadableDatabase();//
        final Cursor cursor = db.rawQuery(sql,null);//
        while(cursor.moveToNext()){//iterate
            String name=cursor.getString(0);
            if(name.equals(username)){
                String fname=cursor.getString(1);
                String lname=cursor.getString(2);
                String gender=cursor.getString(3);
                String age=cursor.getString(4);
                String password=cursor.getString(5);
                Uri imagePath=Uri.parse(cursor.getString(6));
                user=new UserModel(name,fname,lname,gender,age,password,imagePath);
                break;
        }
    }
        cursor.close();
        db.close();
        return user;
    }
    public Uri getProfile(String username) {
        Bitmap profile=null;
        Uri imagePath=null;
        String sql="SELECT * FROM " + TABLE_NAME ;
        UserModel user=new UserModel();
        SQLiteDatabase db=this.getReadableDatabase();//
        final Cursor cursor = db.rawQuery(sql,null);//
        try{
        while(cursor.moveToNext()){//iterate
            String name=cursor.getString(0);
            if(name.equals(username)){
                imagePath=Uri.parse(cursor.getString(6));
//                profile = MediaStore.Images.Media.getBitmap(context.getContentResolver(),imagePath);
//            profile=getBi
            }
        }}catch(Exception e){
            Log.e("Error",e.toString());
        }
        return imagePath;
    }
    public void logIn(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cn=new ContentValues();
        cn.put("loggedIn ",1);
        Cursor c=db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE username=?",new String[]{username});
        if(c.getCount()>0){
            long r=db.update(TABLE_NAME,cn,"username =?",new String[]{username});
        }
    }
    public void logOut(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cn=new ContentValues();
        cn.put("loggedIn ",0);
        Cursor c=db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE username=?",new String[]{username});
        if(c.getCount()>0){
            long r=db.update(TABLE_NAME,cn,"username =?",new String[]{username});
        }
    }
    public String checkLoggedInAcc(){
        String username=null;
        String sql="SELECT * FROM " + TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.rawQuery(sql,null);
        while(cursor.moveToNext()){//iterate
            int log=cursor.getInt(7);
            if(log==1){
                username=cursor.getString(0);
            }
        }
        cursor.close();
        db.close();
        return username;
    }
}
