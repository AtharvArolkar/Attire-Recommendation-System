package com.example.clothme.Recommendation;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;

//import java.time.DateTimeUtils;
import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
//import java.time.LocalDate;
//import java.time.Period;
import org.threeten.bp.Period;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.time.format.DateTimeFormatter;


public class LastWornRank extends AppCompatActivity{

    public long getRank(String date, String type) {
//        date = "20 06 2022";
//        type = "Outerwear";
        AndroidThreeTen.init(this);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
        String currentDay = sdf.format(new Date());
        Date date1=null,date2=null;
        try {
            date1 = sdf.parse(currentDay);
            date2 = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        LocalDate date1 = LocalDate.parse(date, dtf);
//        LocalDate date2 = LocalDate.now();
        long difference = Math.abs(date1.getTime() - date2.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);
        difference=differenceDates;
        Log.v("BBBdifference",""+difference);
        if (type == "Outerwear"){
            if (difference < 10)
                difference += 10;
        }
        if (type == "Bottomwear"){
            difference += 5;
        }
        if (difference > 100){
            difference = 100;
        }
        difference *= 0.2;
        Log.v("BBBdifference1",""+difference);
        return difference;

    }

//    public static void main(String[] args) {
//        System.out.println(getRank("20 06 2022","Outerwear"));
//    }
}
