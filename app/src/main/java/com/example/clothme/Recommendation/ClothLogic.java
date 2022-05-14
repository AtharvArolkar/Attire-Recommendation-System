package com.example.clothme.Recommendation;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.clothme.Fragments.FetchOutfitSelectBase;
import com.example.clothme.Models.ClothesModel;
import com.example.clothme.Models.ImageModel;

import java.util.ArrayList;
import java.util.Objects;

public class ClothLogic {
    ArrayList<ArrayList<String>> top = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> bottom = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> outer = new ArrayList<ArrayList<String>>();
    public int temperature;
    String occasion;
    Context context;

    public String setOccasion() {
//        this.occasion = occasion;
        return occasion;
    }

    public int setTemperature() {
//        this.temperature = temperature;
        return temperature;
    }
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
    public Context getContext(){
        return context;
    }
    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public ArrayList<ImageModel> convertData(ArrayList<ClothesModel> cm, int temperature, String occasion, Context context) {
        this.context=context;
        setTemperature(temperature);
        setOccasion(occasion);
//        Log.v("AAA",cm.size()+":Cl");
        for (int i = 0; i < cm.size(); i++) {
            ClothesModel cm1 = cm.get(i);
            String username = cm1.getUsername();
            String imageId = cm1.getId();
            String uri = cm1.getUri();
            String clothtype = cm1.getClothtype();
            String color = cm1.getColor();
            String fabric = cm1.getFabric();
            String category = cm1.getCategory();
            all_predict(username, uri, clothtype, color, fabric, category, "0", imageId);
        }
        ArrayList<ImageModel> images=generateOutfit(top, bottom, outer);
        return images;
    }

    public void all_predict(String userid, String uri, String clothtype, String color, String fabric, String category, String rating
            , String imageid) {
        ArrayList<String> list = new ArrayList<>();
        list.add(userid);
        list.add(imageid);
        list.add(clothtype);
        list.add(color);
        list.add(fabric);
        list.add(uri);
        list.add(rating);
        if (Objects.equals(category, "topwear")) {
            top.add(list);
        } else if (Objects.equals(category, "bottomwear")) {
            bottom.add(list);
        } else if (Objects.equals(category, "outerwear")) {
            outer.add(list);
        }
    }

    public ArrayList<ImageModel> generateOutfit(ArrayList<ArrayList<String>> top, ArrayList<ArrayList<String>> bottom,
                                                ArrayList<ArrayList<String>> outer) {
        int temperature1 = setTemperature();
        String event = setOccasion();
        ArrayList<ArrayList<String>> toprightwear = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> bottomrightwear = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> outerrightwear = new ArrayList<ArrayList<String>>();
        switch (event) {
            case "religious event":
                /*TopWear*/
                if (temperature1 <= 15) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "Long Sleeves Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 15 && temperature1 <= 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "Long Sleeves Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 8;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "Long Sleeves Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 2;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                }
                /*Bottom*/
                if (temperature1 < 20) {
                    for (int i = 0; i < bottom.size(); i++) {
                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Trousers")) {
                            if (Objects.equals(bottom.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester") || Objects.equals(bottom.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                } else if (temperature1 >= 20) {
                    for (int i = 0; i < bottom.size(); i++) {
                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Trousers")) {
                            if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester") || Objects.equals(bottom.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                }
                /*Outer*/
                if (temperature1 <= 20) {
                    for (int i = 0; i < outer.size(); i++) {
                        int number = 0;
                        if (Objects.equals(outer.get(i).get(2), "Coat")) {
                            number = number + 10;
                            String s = String.valueOf(number);
                            outer.get(i).set(6, s);
                            outerrightwear.add(outer.get(i));
                        }
                    }
                }
                break;
            case "casual/travel":
            case "picnic":
            case "barbeque":
                /*TopWear*/
                if (temperature1 <= 15) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "T-Shirt") || Objects.equals(top.get(i).get(2), "Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 15 && temperature1 <= 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "T-Shirt") || Objects.equals(top.get(i).get(2), "Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "T-Shirt") || Objects.equals(top.get(i).get(2), "Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                }
                /*Bottom*/
                if (temperature1 <= 25) {
                    for (int i = 0; i < bottom.size(); i++) {
                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Jeans")) {
                            if (Objects.equals(bottom.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester") || Objects.equals(bottom.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 25) {
                    for (int i = 0; i < bottom.size(); i++) {
                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Jeans") || Objects.equals(bottom.get(i).get(2), "Shorts")) {
                            if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester") || Objects.equals(bottom.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                }
                /*Outer*/
                if (temperature1 <= 20) {
                    for (int i = 0; i < outer.size(); i++) {
                        int number = 0;
                        if (Objects.equals(outer.get(i).get(2), "Jacket") || Objects.equals(outer.get(i).get(2), "Sweater")) {
                            if (Objects.equals(outer.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                outer.get(i).set(6, s);
                                outerrightwear.add(outer.get(i));
                            } else if (Objects.equals(outer.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                outer.get(i).set(6, s);
                                outerrightwear.add(outer.get(i));
                            } else if (Objects.equals(outer.get(i).get(4), "Nylon/Polyester") || Objects.equals(outer.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                outer.get(i).set(6, s);
                                outerrightwear.add(outer.get(i));
                            }
                        }
                    }
                }
                break;
            case "Smart Casual":
                /*TopWear*/
                if (temperature1 <= 15) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "Long Sleeves Shirt") || Objects.equals(top.get(i).get(2), "Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 15 && temperature1 <= 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "Long Sleeves Shirt") || Objects.equals(top.get(i).get(2), "Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "Long Sleeves Shirt") || Objects.equals(top.get(i).get(2), "Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                }
                /*Bottom*/
                if (temperature1 <= 25) {
                    for (int i = 0; i < bottom.size(); i++) {
                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Jeans") || Objects.equals(bottom.get(i).get(2), "Trousers")) {
                            if (Objects.equals(bottom.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester") || Objects.equals(bottom.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 25) {
                    for (int i = 0; i < bottom.size(); i++) {
                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Jeans") || Objects.equals(bottom.get(i).get(2), "Trousers")) {
                            if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester") || Objects.equals(bottom.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                }
                /*Outer*/
                if (temperature1 <= 20) {
                    for (int i = 0; i < outer.size(); i++) {
                        int number = 0;
                        if (Objects.equals(outer.get(i).get(2), "Jacket") || Objects.equals(outer.get(i).get(2), "Sweater")) {
                            if (Objects.equals(outer.get(i).get(4), "Wool")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                outer.get(i).set(6, s);
                                outerrightwear.add(outer.get(i));
                            } else if (Objects.equals(outer.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                outer.get(i).set(6, s);
                                outerrightwear.add(outer.get(i));
                            } else if (Objects.equals(outer.get(i).get(4), "Nylon/Polyester") || Objects.equals(outer.get(i).get(4), "Silk")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                outer.get(i).set(6, s);
                                outerrightwear.add(outer.get(i));
                            }
                        }
                    }
                }
                break;
            case "Wedding":
            case "cocktail party":
                /*TopWear*/
                if (temperature1 <= 15) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "Long Sleeves Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 15 && temperature1 <= 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "Long Sleeves Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 2;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "Long Sleeves Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 2;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                }
                /*Bottom*/
                if (temperature1 < 20) {
                    for (int i = 0; i < bottom.size(); i++) {
                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Trousers")) {
                            if (Objects.equals(bottom.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester") || Objects.equals(bottom.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                } else if (temperature1 >= 20) {
                    for (int i = 0; i < bottom.size(); i++) {
                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Trousers")) {
                            if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester") || Objects.equals(bottom.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                }
                /*Outer*/
                for (int i = 0; i < outer.size(); i++) {
                    int number = 0;
                    if (Objects.equals(outer.get(i).get(2), "Blazer")) {
                        number = number + 10;
                        String s = String.valueOf(number);
                        outer.get(i).set(6, s);
                        outerrightwear.add(outer.get(i));
                    }
                }
                break;
            case "job interview":
            case "business dinner":
                /*TopWear*/
                if (temperature1 <= 15) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "Long Sleeves Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 15 && temperature1 <= 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "Long Sleeves Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 2;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "Long Sleeves Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 2;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                }
                /*Bottom*/
                if (temperature1 < 20) {
                    for (int i = 0; i < bottom.size(); i++) {
                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Trousers")) {
                            if (Objects.equals(bottom.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester") || Objects.equals(bottom.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                } else if (temperature1 >= 20) {
                    for (int i = 0; i < bottom.size(); i++) {
                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Trousers")) {
                            if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester") || Objects.equals(bottom.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                }
                break;
            case "dinner party":
                /*TopWear*/
                if (temperature1 <= 15) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "Long Sleeves Shirt") || Objects.equals(top.get(i).get(2), "Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 15 && temperature1 <= 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "Long Sleeves Shirt") || Objects.equals(top.get(i).get(2), "Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 2;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "Long Sleeves Shirt") || Objects.equals(top.get(i).get(2), "Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 2;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                }
                /*Bottom*/
                if (temperature1 < 20) {
                    for (int i = 0; i < bottom.size(); i++) {
                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Trousers") || Objects.equals(top.get(i).get(2), "Jeans")) {
                            if (Objects.equals(bottom.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester") || Objects.equals(bottom.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 20) {
                    for (int i = 0; i < bottom.size(); i++) {
                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Trousers") || Objects.equals(top.get(i).get(2), "Jeans")) {

                            if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester") || Objects.equals(bottom.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                }
                /*Outer*/
                if (temperature1 <= 20) {
                    for (int i = 0; i < outer.size(); i++) {
                        int number = 0;
                        if (Objects.equals(outer.get(i).get(2), "Coat")) {
                            number = number + 10;
                            String s = String.valueOf(number);
                            outer.get(i).set(6, s);
                            outerrightwear.add(outer.get(i));
                        }
                    }
                }
                break;
            case "beach party":
                /*TopWear*/
                if (temperature1 <= 15) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "T-Shirt") || Objects.equals(top.get(i).get(2), "Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 15 && temperature1 <= 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "T-Shirt") || Objects.equals(top.get(i).get(2), "Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Wool")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "T-Shirt") || Objects.equals(top.get(i).get(2), "Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Nylon/Polyester") || Objects.equals(top.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                }
                /*Bottom*/
                if (temperature1 < 20) {
                    for (int i = 0; i < bottom.size(); i++) {
                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Shorts")) {
                            if (Objects.equals(bottom.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                } else if (temperature1 >= 20) {
                    for (int i = 0; i < bottom.size(); i++) {
                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Shorts")) {
                            if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester") || Objects.equals(bottom.get(i).get(4), "Silk")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                }
                /*Outer*/
                if (temperature1 <= 20) {
                    for (int i = 0; i < outer.size(); i++) {
                        int number = 0;
                        if (Objects.equals(outer.get(i).get(2), "Jacket")) {
                            if (Objects.equals(outer.get(i).get(4), "Wool")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                outer.get(i).set(6, s);
                                outerrightwear.add(outer.get(i));
                            } else if (Objects.equals(outer.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                outer.get(i).set(6, s);
                                outerrightwear.add(outer.get(i));
                            } else if (Objects.equals(outer.get(i).get(4), "Nylon/Polyester") || Objects.equals(outer.get(i).get(4), "Silk")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                outer.get(i).set(6, s);
                                outerrightwear.add(outer.get(i));
                            }
                        }
                    }
                }
                break;
            case "sports":
                /*TopWear*/
                if (temperature1 <= 15) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if(Objects.equals(top.get(i).get(2),"T-Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Nylon/Polyester")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 15 && temperature1 <= 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "T-Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Nylon/Polyester")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 25) {
                    for (int i = 0; i < top.size(); i++) {
                        int number = 0;
                        if (Objects.equals(top.get(i).get(2), "T-Shirt")) {
                            if (Objects.equals(top.get(i).get(4), "Nylon/Polyester")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            } else if (Objects.equals(top.get(i).get(4), "Cotton")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                top.get(i).set(6, s);
                                toprightwear.add(top.get(i));
                            }
                        }
                    }
                }
                /*Bottom*/
                if (temperature1 <= 20) {
                    for (int i = 0; i < bottom.size(); i++) {

                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Track Pants")) {
                            if (Objects.equals(bottom.get(i).get(4), "Wool")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                } else if (temperature1 > 20) {
                    for (int i = 0; i < bottom.size(); i++) {
                        int number = 0;
                        if (Objects.equals(bottom.get(i).get(2), "Track Pants") || Objects.equals(bottom.get(i).get(2), "Shorts")) {
                            if (Objects.equals(bottom.get(i).get(4), "Wool")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Cotton")) {
                                number = number + 10;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            } else if (Objects.equals(bottom.get(i).get(4), "Nylon/Polyester")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                bottom.get(i).set(6, s);
                                bottomrightwear.add(bottom.get(i));
                            }
                        }
                    }
                }
                /*Outer*/
                if (temperature1 <= 20) {
                    for (int i = 0; i < outer.size(); i++) {
                        int number = 0;
                        if (Objects.equals(outer.get(i).get(2), "Jacket") || Objects.equals(outer.get(i).get(2), "Track Suit")) {
                            if (Objects.equals(outer.get(i).get(4), "Wool")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                outer.get(i).set(6, s);
                                outerrightwear.add(outer.get(i));
                            } else if (Objects.equals(outer.get(i).get(4), "Cotton")) {
                                number = number + 5;
                                String s = String.valueOf(number);
                                outer.get(i).set(6, s);
                                outerrightwear.add(outer.get(i));
                            } else if (Objects.equals(outer.get(i).get(4), "Nylon/Polyester")) {
                                number = number + 7;
                                String s = String.valueOf(number);
                                outer.get(i).set(6, s);
                                outerrightwear.add(outer.get(i));
                            }
                        }
                    }
                }
                break;
            default:
        }
        Log.v("AAA","TopWear");
        ArrayList<ImageModel> imageModels=new ArrayList<>();
        for(int i=0;i<toprightwear.size();i++){
            ImageModel im=new ImageModel();
            im.setId(toprightwear.get(i).get(1));
            Uri imageUri = Uri.parse(toprightwear.get(i).get(5));
            Bitmap image = null;
            try{
                image = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
            }catch(Exception e){
                e.printStackTrace();
            }
            im.setPic(image);
            im.setText(toprightwear.get(i).get(2));
            imageModels.add(im);
                Log.v("AAA",toprightwear.get(i).get(0)+","+toprightwear.get(i).get(1)+","+toprightwear.get(i).get(2)+","
                +toprightwear.get(i).get(3)+","+toprightwear.get(i).get(4)+","+toprightwear.get(i).get(6)+",");

        }
        Log.v("AAA","BottomWear");
        for(int i=0;i<bottomrightwear.size();i++){
            ImageModel im=new ImageModel();
            im.setId(bottomrightwear.get(i).get(1));
            Uri imageUri = Uri.parse(bottomrightwear.get(i).get(5));
            Bitmap image = null;
            try{
                image = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
            }catch(Exception e){
                e.printStackTrace();
            }
            im.setPic(image);
            im.setText(bottomrightwear.get(i).get(2));
            imageModels.add(im);
            Log.v("AAA",bottomrightwear.get(i).get(0)+","+bottomrightwear.get(i).get(1)+","+bottomrightwear.get(i).get(2)+","
                    +bottomrightwear.get(i).get(3)+","+bottomrightwear.get(i).get(4)+","+bottomrightwear.get(i).get(6)+",");
        }
        Log.v("AAA","OuterWear");
        for(int i=0;i<outerrightwear.size();i++){
            ImageModel im=new ImageModel();
            im.setId(outerrightwear.get(i).get(1));
            Uri imageUri = Uri.parse(outerrightwear.get(i).get(5));
            Bitmap image = null;
            try{
                image = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
            }catch(Exception e){
                e.printStackTrace();
            }
            im.setPic(image);
            im.setText(outerrightwear.get(i).get(2));
            imageModels.add(im);
            Log.v("AAA",outerrightwear.get(i).get(0)+","+outerrightwear.get(i).get(1)+","+outerrightwear.get(i).get(2)+","
                    +outerrightwear.get(i).get(3)+","+outerrightwear.get(i).get(4)+","+outerrightwear.get(i).get(6)+",");
        }
        return imageModels;
//        Log.v("AAA:",toprightwear.get(1));
//        System.out.println(toprightwear);
//        System.out.println(bottomrightwear);
//        System.out.println(outerrightwear);
    }
}
