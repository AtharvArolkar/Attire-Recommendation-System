package com.example.clothme.Recommendation;

import android.util.Log;

import java.util.Arrays;

public class PatternMatch {

    public int getRank(String[] patterns,String type) {
//        Log.v("DDDlength" ,""+patterns.length);
//        Log.v("DDDpattern" ,""+patterns[1]);
        //patterns = {"1", "2", "3"};//1 is plain, 2 is stripes etc

        int len = patterns.length;
        int temp = 0;
        int score = 0;
        if (len == 1) {
            return (100);
        }

        else if (len == 2) {
            if (patterns[0] == patterns [1]){
                switch (patterns[0]){
                    case "plain": score = 100;
                        break;
                    case "abstract": score = 80;
                        break;
                    case "geometry": score = 60;
                        break;
                    case "floral": score = 80;
                        break;
                    case "stripes":
                    case "checks":score = 10;
                        break;
                    default: return -1;
//                    break;
                }
            } else if (patterns[0].equals("plain") || patterns[1].equals("plain")) {
//                Log.v("DDD" ,"plain");
                score = 100;
            }
            else{
//                Log.v("DDDelse" ,"0");
                return -1;
            }
        }

        else if (len == 3) {
            if (patterns[0]==patterns[2]&&patterns[0]!=patterns[1]){
                score = 100;
            }
            else if (patterns[0]=="plain"&&patterns[1]=="plain"&&patterns[2]=="plain"){
                score = 100;
            }
            else{
                score = 0;
            }

        }

        if (type.equals("formal")){
            if (Arrays.asList(patterns).contains("floral")||
                    Arrays.asList(patterns).contains("abstract")){
                score -=50;
            }
        }
        return score;
    }
}
