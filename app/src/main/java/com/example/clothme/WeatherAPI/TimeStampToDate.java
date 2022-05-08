package com.example.clothme.WeatherAPI;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeStampToDate {
    public String convertTimeStamp(float t){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((long)t*1000);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Timestamp ts=new Timestamp(System.currentTimeMillis());
//        Date d=new Date(ts.getTime());
        String date=sdf.format(d).toString();
        return date;
    }
}
