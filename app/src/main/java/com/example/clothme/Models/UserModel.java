package com.example.clothme.Models;

import android.graphics.Bitmap;
import android.net.Uri;

public class UserModel {
    String username;
    String fname;
    String lname;
    String gender;
    String age;
    String password;
    Uri profilePic;

    public Uri getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Uri profilePic) {
        this.profilePic = profilePic;
    }

    public UserModel(String username, String fname, String lname, String gender, String age, String password, Uri profilePic) {
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.age = age;
        this.password = password;
        this.profilePic=profilePic;
    }

//    public UserModel(String username, String fname, String lname, String gender, String age, String password) {
//        this.username = username;
//        this.fname = fname;
//        this.lname = lname;
//        this.gender = gender;
//        this.age = age;
//        this.password = password;
//    }

    public UserModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
