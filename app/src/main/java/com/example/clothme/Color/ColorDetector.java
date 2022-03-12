package com.example.clothme.Color;

import android.graphics.Bitmap;

import java.sql.Array;
import java.util.*;


public class ColorDetector {
    public Map <String,Integer[]> colorDict =  new HashMap<String,Integer[]>();
//        String colors[]=new String[]{"Brown","Dark Khaki","Apricot","Orange","White_1","Lime","Beige","Teal","Maroon","Cyan","Violet","Blue_1","Blue","Mint",
//        "Grey_6","Magenta","Grey_4","Grey_5","Navy Blue","Grey_2","Grey_3","Grey_1","Red","Olive","Dark Green","Pink","Khaki","Yellow","Purple","Black_4",
//        "Black_5","Black_6","Black_7","Black_1","Green","Black_2","Black_3"};
    public ColorDetector(){
        Integer [] a=new Integer[]{255,0,0};
        colorDict.put("Red",a);
        a=new Integer[]{0,255,0};
        colorDict.put("Green",a);
        a=new Integer[]{0,0,255};
        colorDict.put("Blue",a);
        a=new Integer[]{255,255,0};
        colorDict.put("Yellow",a);
        a=new Integer[]{0,255,255};
        colorDict.put("Cyan",a);
        a=new Integer[]{255,0,255};
        colorDict.put("Magenta",a);

        a=new Integer[]{128,0,128};
        colorDict.put("Purple",a);
        a=new Integer[]{128,128,0};
        colorDict.put("Olive",a);
        a=new Integer[]{0,128,128};
        colorDict.put("Teal",a);

        a=new Integer[]{128,0,0};
        colorDict.put("Maroon",a);
        a=new Integer[]{0,128,0};
        colorDict.put("Dark Green",a);
        a=new Integer[]{0,0,128};
        colorDict.put("Navy Blue",a);

        a=new Integer[]{165,42,42};
        colorDict.put("Brown",a);
        a=new Integer[]{128,0,255};
        colorDict.put("Violet",a);
        a=new Integer[]{255,70,200};
        colorDict.put("Pink",a);
        a=new Integer[]{245,245,220};
        colorDict.put("Beige",a);
        a=new Integer[]{255,128,0};
        colorDict.put("Orange",a);

        a=new Integer[]{240,230,140};
        colorDict.put("Khaki",a);
        a=new Integer[]{189,183,107};
        colorDict.put("Dark Khaki",a);
        a=new Integer[]{255,215,180};
        colorDict.put("Apricot",a);
        a=new Integer[]{250,190,212};
        colorDict.put("Pink",a);
        a=new Integer[]{170,255,195};
        colorDict.put("Mint",a);
        a=new Integer[]{210,245,60};
        colorDict.put("Lime",a);

        //Black shades
        a=new Integer[]{5,5,5};
        colorDict.put("Black_1",a);
        a=new Integer[]{13,13,13};
        colorDict.put("Black_2",a);
        a=new Integer[]{18,18,18};
        colorDict.put("Black_3",a);
        a=new Integer[]{26,26,26};
        colorDict.put("Black_4",a);
        a=new Integer[]{43,43,43};
        colorDict.put("Black_5",a);
        a=new Integer[]{51,51,51};
        colorDict.put("Black_6",a);
        a=new Integer[]{56,56,56};
        colorDict.put("Black_7",a);

        //Shades of Grey
        a=new Integer[]{133,133,133};
        colorDict.put("Grey_1",a);
        a=new Integer[]{158,158,158};
        colorDict.put("Grey_2",a);
        a=new Integer[]{184,184,184};
        colorDict.put("Grey_3",a);
        a=new Integer[]{122,122,122};
        colorDict.put("Grey_4",a);
        a=new Integer[]{102,102,102};
        colorDict.put("Grey_5",a);
        a=new Integer[]{82,82,82};
        colorDict.put("Grey_6",a);

        //Shades of White
        a=new Integer[]{255,255,255};
        colorDict.put("White_1",a);
        a=new Integer[]{245,245,245};
        colorDict.put("White_2",a);
        a=new Integer[]{230,230,230};
        colorDict.put("White_3",a);
        a=new Integer[]{209,209,209};
        colorDict.put("White_4",a);


        a=new Integer[]{77,125,163};
        colorDict.put("Blue_1",a);
        a=new Integer[]{255,255,255};
        colorDict.put("NULL",a);

    }

    public String getColor(Bitmap image){
        int height = image.getHeight();
        int width = image.getWidth();
//        Map m = new HashMap();
//        for(int i=0; i < width ; i++)
//        {
//            for(int j=0; j < height ; j++)
//            {
//                int rgb = image.getPixel(i, j);
//                int[] rgbArr = getRGBArr(rgb);
//                // Filter out grays....
//                if (!isGray(rgbArr)) {
//                    Integer counter = (Integer) m.get(rgb);
//                    if (counter == null)
//                        counter = 0;
//                    counter++;
//                    m.put(rgb, counter);
//                }
//            }
//        }
        Map<Integer, Integer> colorMap = new HashMap<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getPixel(i, j);
                if (!isGray(getRGBArr(rgb))) {
                    Integer counter = colorMap.get(rgb);
                    if (counter == null) {
                        counter = 0;
                    }

                    colorMap.put(rgb, ++counter);
                }
            }
        }

//        return getMostCommonColor(colorMap);
        int[] rgb=null;
        String color=null;
        try {
            rgb = getMostCommonColor(colorMap);
            int min=100000;
            for(String key :colorDict.keySet() ){
                Integer[] a=colorDict.get(key);
                int Euclidean=(int)Math.sqrt(Math.pow(((rgb[0]-a[0])*0.3),2)+Math.pow(((rgb[1]-a[1])*0.59),2)+Math.pow(((rgb[2]-a[2])*0.11),2));
                if(Euclidean<min){
                    min=Euclidean;
                    color=key;
                }
            }
        }catch (IndexOutOfBoundsException e){
            color="NULL";
        }


        /*if(rgb[0] >= rgb[1]-10 && rgb[0] <= rgb[1]+10){
            if(rgb[0] >= rgb[2]-10 && rgb[0] <= rgb[2]+10){
                int a=rgb[0];
                if(a>=0 && a<55){color="Black";}
                else if(a>=55 && a<101){color="Dark Grey";}
                else if(a>=101 && a<141){color="Grey";}
                else if(a>=141 && a<181){color="Light Grey";}
                else{color="White";}
            }
        }else{*/



//        return (rgb[0]+","+rgb[1]+","+rgb[2]);
        return color;
    }



    private static int[] getMostCommonColor(Map<Integer, Integer> map) {
        List<Map.Entry<Integer, Integer>> list = new LinkedList<>(map.entrySet());

        Collections.sort(list, (Map.Entry<Integer, Integer> obj1, Map.Entry<Integer, Integer> obj2)
                -> ((Comparable) obj1.getValue()).compareTo(obj2.getValue()));

        Map.Entry<Integer, Integer> entry = list.get(list.size() - 1);
        int[] rgb = getRGBArr(entry.getKey());
        return rgb;
//        return "#" + Integer.toHexString(rgb[0])
//                + Integer.toHexString(rgb[1])
//                + Integer.toHexString(rgb[2]);
    }

    private static int[] getRGBArr(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;

        return new int[]{red, green, blue};
    }

    private static boolean isGray(int[] rgbArr) {
        int rgDiff = rgbArr[0] - rgbArr[1];
        int rbDiff = rgbArr[0] - rgbArr[2];
        // Filter out black, white and grays...... (tolerance within 10 pixels)
        int tolerance = 10;
        if (rgDiff > tolerance || rgDiff < -tolerance) {
            if (rbDiff > tolerance || rbDiff < -tolerance) {
                return false;
            }
        }
        return true;
    }






//    public static int[] getMostCommonColour(Map map) {
//        List list = new LinkedList(map.entrySet());
//        Collections.sort(list, new Comparator() {
//            public int compare(Object o1, Object o2) {
//                return ((Comparable) ((Map.Entry) (o1)).getValue())
//                        .compareTo(((Map.Entry) (o2)).getValue());
//            }
//        });
//        Map.Entry me = (Map.Entry )list.get(list.size()-1);
//        int[] rgb= getRGBArr((Integer)me.getKey());
////        return Integer.toHexString(rgb[0])+" "+Integer.toHexString(rgb[1])+" "+Integer.toHexString(rgb[2]);
//        return rgb;
//    }
//
//    public static int[] getRGBArr(int pixel) {
//        int alpha = (pixel >> 24) & 0xff;
//        int red = (pixel >> 16) & 0xff;
//        int green = (pixel >> 8) & 0xff;
//        int blue = (pixel) & 0xff;
//        return new int[]{red,green,blue};
//
//    }
//    public static boolean isGray(int[] rgbArr) {
//        int rgDiff = rgbArr[0] - rgbArr[1];
//        int rbDiff = rgbArr[0] - rgbArr[2];
//        // Filter out black, white and grays...... (tolerance within 10 pixels)
//        int tolerance = 10;
//        if (rgDiff > tolerance || rgDiff < -tolerance)
//            if (rbDiff > tolerance || rbDiff < -tolerance) {
//                return false;
//            }
//        return true;
//    }
}
