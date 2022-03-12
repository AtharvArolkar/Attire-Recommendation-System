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
        Integer [] a=new Integer[]{240,248,255};
        colorDict.put("AliceBlue",a);
        a=new Integer[]{250,235,215};
        colorDict.put("AntiqueWhite",a);
        a=new Integer[]{0,255,255};
        colorDict.put("Aqua",a);
        a=new Integer[]{127,255,212};
        colorDict.put("Aquamarine",a);
        a=new Integer[]{240,255,255};
        colorDict.put("Azure",a);
        a=new Integer[]{245,245,220};
        colorDict.put("Beige",a);
        a=new Integer[]{255,228,196};
        colorDict.put("Bisque",a);
        a=new Integer[]{0,0,0};
        colorDict.put("Black",a);
        a=new Integer[]{255,235,205};
        colorDict.put("BlanchedAlmond",a);
        a=new Integer[]{0,0,255};
        colorDict.put("Blue",a);
        a=new Integer[]{138,43,226};
        colorDict.put("BlueViolet",a);
        a=new Integer[]{165,42,42};
        colorDict.put("Brown",a);
        a=new Integer[]{222,184,135};
        colorDict.put("BurlyWood",a);
        a=new Integer[]{95,158,160};
        colorDict.put("CadetBlue",a);
        a=new Integer[]{127,255,0};
        colorDict.put("Chartreuse",a);
        a=new Integer[]{210,105,30};
        colorDict.put("Chocolate",a);
        a=new Integer[]{255,127,80};
        colorDict.put("Coral",a);
        a=new Integer[]{100,149,237};
        colorDict.put("CornflowerBlue",a);
        a=new Integer[]{255,248,220};
        colorDict.put("Cornsilk",a);
        a=new Integer[]{220,20,60};
        colorDict.put("Crimson",a);
        a=new Integer[]{0,255,255};
        colorDict.put("Cyan",a);
        a=new Integer[]{0,0,139};
        colorDict.put("DarkBlue",a);
        a=new Integer[]{0,139,139};
        colorDict.put("DarkCyan",a);
        a=new Integer[]{184,134,11};
        colorDict.put("DarkGoldenRod",a);
        a=new Integer[]{169,169,169};
        colorDict.put("DarkGray",a);
        a=new Integer[]{0,100,0};
        colorDict.put("DarkGreen",a);
        a=new Integer[]{189,183,107};
        colorDict.put("DarkKhaki",a);
        a=new Integer[]{139,0,139};
        colorDict.put("DarkMagenta",a);
        a=new Integer[]{85,107,47};
        colorDict.put("DarkOliveGreen",a);
        a=new Integer[]{255,140,0};
        colorDict.put("DarkOrange",a);
        a=new Integer[]{153,50,204};
        colorDict.put("DarkOrchid",a);
        a=new Integer[]{139,0,0};
        colorDict.put("DarkRed",a);
        a=new Integer[]{233,150,122};
        colorDict.put("DarkSalmon",a);
        a=new Integer[]{143,188,143};
        colorDict.put("DarkSeaGreen",a);
        a=new Integer[]{72,61,139};
        colorDict.put("DarkSlateBlue",a);
        a=new Integer[]{47,79,79};
        colorDict.put("DarkSlateGray",a);
        a=new Integer[]{0,206,209};
        colorDict.put("DarkTurquoise",a);
        a=new Integer[]{148,0,211};
        colorDict.put("DarkViolet",a);
        a=new Integer[]{255,20,147};
        colorDict.put("DeepPink",a);
        a=new Integer[]{0,191,255};
        colorDict.put("DeepSkyBlue",a);
        a=new Integer[]{105,105,105};
        colorDict.put("DimGray",a);
        a=new Integer[]{30,144,255};
        colorDict.put("DodgerBlue",a);
        a=new Integer[]{178,34,34};
        colorDict.put("FireBrick",a);
        a=new Integer[]{255,250,240};
        colorDict.put("FloralWhite",a);
        a=new Integer[]{34,139,34};
        colorDict.put("ForestGreen",a);
        a=new Integer[]{255,0,255};
        colorDict.put("Fuchsia",a);
        a=new Integer[]{220,220,220};
        colorDict.put("Gainsboro",a);
        a=new Integer[]{248,248,255};
        colorDict.put("GhostWhite",a);
        a=new Integer[]{255,215,0};
        colorDict.put("Gold",a);
        a=new Integer[]{218,165,32};
        colorDict.put("GoldenRod",a);
        a=new Integer[]{128,128,128};
        colorDict.put("Gray",a);
        a=new Integer[]{0,128,0};
        colorDict.put("Green",a);
        a=new Integer[]{173,255,47};
        colorDict.put("GreenYellow",a);
        a=new Integer[]{240,255,240};
        colorDict.put("HoneyDew",a);
        a=new Integer[]{255,105,180};
        colorDict.put("HotPink",a);
        a=new Integer[]{205,92,92};
        colorDict.put("IndianRed",a);
        a=new Integer[]{75,0,130};
        colorDict.put("Indigo",a);
        a=new Integer[]{255,255,240};
        colorDict.put("Ivory",a);
        a=new Integer[]{240,230,140};
        colorDict.put("Khaki",a);
        a=new Integer[]{230,230,250};
        colorDict.put("Lavender",a);
        a=new Integer[]{255,240,245};
        colorDict.put("LavenderBlush",a);
        a=new Integer[]{124,252,0};
        colorDict.put("LawnGreen",a);
        a=new Integer[]{255,250,205};
        colorDict.put("LemonChiffon",a);
        a=new Integer[]{173,216,230};
        colorDict.put("LightBlue",a);
        a=new Integer[]{240,128,128};
        colorDict.put("LightCoral",a);
        a=new Integer[]{224,255,255};
        colorDict.put("LightCyan",a);
        a=new Integer[]{250,250,210};
        colorDict.put("LightGoldenRodYellow",a);
        a=new Integer[]{211,211,211};
        colorDict.put("LightGray",a);
        a=new Integer[]{144,238,144};
        colorDict.put("LightGreen",a);
        a=new Integer[]{255,182,193};
        colorDict.put("LightPink",a);
        a=new Integer[]{255,160,122};
        colorDict.put("LightSalmon",a);
        a=new Integer[]{32,178,170};
        colorDict.put("LightSeaGreen",a);
        a=new Integer[]{135,206,250};
        colorDict.put("LightSkyBlue",a);
        a=new Integer[]{119,136,153};
        colorDict.put("LightSlateGray",a);
        a=new Integer[]{176,196,222};
        colorDict.put("LightSteelBlue",a);
        a=new Integer[]{255,255,224};
        colorDict.put("LightYellow",a);
        a=new Integer[]{0,255,0};
        colorDict.put("Lime",a);
        a=new Integer[]{50,205,50};
        colorDict.put("LimeGreen",a);
        a=new Integer[]{250,240,230};
        colorDict.put("Linen",a);
        a=new Integer[]{255,0,255};
        colorDict.put("Magenta",a);
        a=new Integer[]{128,0,0};
        colorDict.put("Maroon",a);
        a=new Integer[]{102,205,170};
        colorDict.put("MediumAquaMarine",a);
        a=new Integer[]{0,0,205};
        colorDict.put("MediumBlue",a);
        a=new Integer[]{186,85,211};
        colorDict.put("MediumOrchid",a);
        a=new Integer[]{147,112,219};
        colorDict.put("MediumPurple",a);
        a=new Integer[]{60,179,113};
        colorDict.put("MediumSeaGreen",a);
        a=new Integer[]{123,104,238};
        colorDict.put("MediumSlateBlue",a);
        a=new Integer[]{0,250,154};
        colorDict.put("MediumSpringGreen",a);
        a=new Integer[]{72,209,204};
        colorDict.put("MediumTurquoise",a);
        a=new Integer[]{199,21,133};
        colorDict.put("MediumVioletRed",a);
        a=new Integer[]{25,25,112};
        colorDict.put("MidnightBlue",a);
        a=new Integer[]{245,255,250};
        colorDict.put("MintCream",a);
        a=new Integer[]{255,228,225};
        colorDict.put("MistyRose",a);
        a=new Integer[]{255,228,181};
        colorDict.put("Moccasin",a);
        a=new Integer[]{255,222,173};
        colorDict.put("NavajoWhite",a);
        a=new Integer[]{0,0,128};
        colorDict.put("Navy",a);
        a=new Integer[]{253,245,230};
        colorDict.put("OldLace",a);
        a=new Integer[]{128,128,0};
        colorDict.put("Olive",a);
        a=new Integer[]{107,142,35};
        colorDict.put("OliveDrab",a);
        a=new Integer[]{255,165,0};
        colorDict.put("Orange",a);
        a=new Integer[]{255,69,0};
        colorDict.put("OrangeRed",a);
        a=new Integer[]{218,112,214};
        colorDict.put("Orchid",a);
        a=new Integer[]{238,232,170};
        colorDict.put("PaleGoldenRod",a);
        a=new Integer[]{152,251,152};
        colorDict.put("PaleGreen",a);
        a=new Integer[]{175,238,238};
        colorDict.put("PaleTurquoise",a);
        a=new Integer[]{219,112,147};
        colorDict.put("PaleVioletRed",a);
        a=new Integer[]{255,239,213};
        colorDict.put("PapayaWhip",a);
        a=new Integer[]{255,218,185};
        colorDict.put("PeachPuff",a);
        a=new Integer[]{205,133,63};
        colorDict.put("Peru",a);
        a=new Integer[]{255,192,203};
        colorDict.put("Pink",a);
        a=new Integer[]{221,160,221};
        colorDict.put("Plum",a);
        a=new Integer[]{176,224,230};
        colorDict.put("PowderBlue",a);
        a=new Integer[]{128,0,128};
        colorDict.put("Purple",a);
        a=new Integer[]{255,0,0};
        colorDict.put("Red",a);
        a=new Integer[]{188,143,143};
        colorDict.put("RosyBrown",a);
        a=new Integer[]{65,105,225};
        colorDict.put("RoyalBlue",a);
        a=new Integer[]{139,69,19};
        colorDict.put("SaddleBrown",a);
        a=new Integer[]{250,128,114};
        colorDict.put("Salmon",a);
        a=new Integer[]{244,164,96};
        colorDict.put("SandyBrown",a);
        a=new Integer[]{46,139,87};
        colorDict.put("SeaGreen",a);
        a=new Integer[]{255,245,238};
        colorDict.put("SeaShell",a);
        a=new Integer[]{160,82,45};
        colorDict.put("Sienna",a);
        a=new Integer[]{192,192,192};
        colorDict.put("Silver",a);
        a=new Integer[]{135,206,235};
        colorDict.put("SkyBlue",a);
        a=new Integer[]{106,90,205};
        colorDict.put("SlateBlue",a);
        a=new Integer[]{112,128,144};
        colorDict.put("SlateGray",a);
        a=new Integer[]{255,250,250};
        colorDict.put("Snow",a);
        a=new Integer[]{0,255,127};
        colorDict.put("SpringGreen",a);
        a=new Integer[]{70,130,180};
        colorDict.put("SteelBlue",a);
        a=new Integer[]{210,180,140};
        colorDict.put("Tan",a);
        a=new Integer[]{0,128,128};
        colorDict.put("Teal",a);
        a=new Integer[]{216,191,216};
        colorDict.put("Thistle",a);
        a=new Integer[]{255,99,71};
        colorDict.put("Tomato",a);
        a=new Integer[]{64,224,208};
        colorDict.put("Turquoise",a);
        a=new Integer[]{238,130,238};
        colorDict.put("Violet",a);
        a=new Integer[]{245,222,179};
        colorDict.put("Wheat",a);
        a=new Integer[]{255,255,255};
        colorDict.put("White",a);
        a=new Integer[]{245,245,245};
        colorDict.put("WhiteSmoke",a);
        a=new Integer[]{255,255,0};
        colorDict.put("Yellow",a);
        a=new Integer[]{154,205,50};
        colorDict.put("YellowGreen",a);

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
