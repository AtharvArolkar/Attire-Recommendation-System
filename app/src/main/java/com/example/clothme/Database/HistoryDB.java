package com.example.clothme.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.clothme.Models.ClothesModel;
import com.example.clothme.Models.HistoryModel;

import java.util.ArrayList;
import java.util.Arrays;

public class HistoryDB extends SQLiteOpenHelper {
    public static final String dbname = "database.db";
    public static final String TABLE_NAME = "eventhistory";
    private static final int startID = 1;
    public HistoryDB(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
    }
    public int getID() {
        int id = startID;
        String sql = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = HistoryDB.this.getReadableDatabase();
        final Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            id++;
        }
        cursor.close();
        return id;
    }
    public boolean insertData(HistoryModel hm) {
//        String s = ur.toString();
        SQLiteDatabase Mydb = HistoryDB.this.getWritableDatabase();
        ContentValues cv = new ContentValues();
//        historyNumber integer, username text,occasion text,topId text,bottomId text,outer Id text
        cv.put("historyNumber", getID());
        cv.put("username", hm.getUsername());
        cv.put("occasion", hm.getOccasion());
        cv.put("date", hm.getDate());
        cv.put("time", hm.getTime());
        cv.put("location", hm.getLocation());
        if(hm.getTopId()!=null){
            cv.put("topId", hm.getTopId());
        }else{
            cv.put("topId", "-");
        }
        if(hm.getBottomId()!=null){
            cv.put("bottomId", hm.getBottomId());
        }else{
            cv.put("bottomId", "-");
        }
        if(hm.getOuterId()!=null){
            cv.put("outerId", hm.getOuterId());
        }else{
            cv.put("outerId", "-");
        }


        long ins = Mydb.insert(TABLE_NAME, null, cv);
        Mydb.close();
        if (ins == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<HistoryModel> fetchHistory(String username) {
        ArrayList<HistoryModel> history = new ArrayList<>();
        SQLiteDatabase MyDB = HistoryDB.this.getReadableDatabase();
        final Cursor cursor = MyDB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        try {
            HistoryModel im = null;
            while (cursor.moveToNext()) {
                if (cursor.getString(1).equals(username)) {
//                    im = new ClothesModel();
                    int historyId = cursor.getInt(0);
                    String usernameData = cursor.getString(1);
                    String occasion = cursor.getString(2);
                    String date = cursor.getString(3);
                    String time = cursor.getString(4);
                    String location = cursor.getString(5);
                    String topId = cursor.getString(6);
                    String bottomId = cursor.getString(7);
                    String outerId = cursor.getString(8);
                    im= new HistoryModel(historyId,usernameData,occasion,date,time,location,topId,bottomId,outerId);
                    history.add(im);
                }
            }
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
        cursor.close();
        MyDB.close();
        return history;
    }
}
