package com.example.clothme.Models;

import android.graphics.Bitmap;
import android.net.Uri;

public class ImageModel {
    Bitmap image;
    String text;
    public Bitmap getBitmap() {
        return image;
    }

    public String getName() {return text;}

    public void setPic(Bitmap image) {
        this.image = image;
    }

    public void setText(String text) {this.text = text;}
}
