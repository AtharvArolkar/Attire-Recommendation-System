package com.example.clothme.Models;

public class ClothesModel {
    String username;
    String id;
    String uri;
    String clothtype;
    String color;



    String fabric;
    String category;

    public String getUsername() {
        return username;
    }

    public String getId() { return id;  }

    public String getUri() {
        return uri;
    }

    public String getClothtype() {
        return clothtype;
    }

    public String getColor() {
        return color;
    }

    public String getFabric() {
        return fabric;
    }

    public String getCategory() {
        return category;
    }

    public ClothesModel(String username,String id, String uri, String clothtype, String color, String fabric, String category) {
        this.username = username;
        this.id=id;
        this.uri = uri;
        this.clothtype = clothtype;
        this.color = color;
        this.fabric = fabric;
        this.category = category;
    }
}
