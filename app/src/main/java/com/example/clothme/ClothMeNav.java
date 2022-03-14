package com.example.clothme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clothme.Database.AccountDB;
import com.example.clothme.Models.ImageModel;
import com.example.clothme.Models.UserModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clothme.databinding.ActivityClothMeNavBinding;

import java.io.File;
import java.io.IOException;

public class ClothMeNav extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityClothMeNavBinding binding;
    AccountDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityClothMeNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db=new AccountDB(this);
//        ImageView imageView=new
        reqPerm();
        String user_name = db.checkLoggedInAcc();
        UserModel user=db.getAcc(user_name);
        setSupportActionBar(binding.appBarClothMeNav.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        ImageView ProfileImage = (ImageView) hView.findViewById(R.id.imageView);
//        Uri imageUri=MainActivity.user.getProfilePic();
//        Bitmap profile = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
//        try {
//            profile = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        try {
//        ProfileImage.setImageBitmap(db.getProfile(getApplicationContext(),MainActivity.user.getUsername()));
//        File f=new File(getRe);

//        ProfileImage.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
//        Toast.makeText(this,""+getPathFromURI(db.getProfile(user.getUsername())),Toast.LENGTH_SHORT).show();
        final String path = getPathFromURI(db.getProfile(user.getUsername()));
        File f = new File(path);
        Uri uri = Uri.fromFile(f);
        ProfileImage.setImageURI(uri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        try {
//            ProfileImage.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }Toast.makeText(this,"Hi",Toast.LENGTH_SHORT).show();
//        ProfileImage.setImageBitmap(db.getAcc(MainActivity.user.getUsername()).getProfilePic());
        hView =  navigationView.getHeaderView(0);
        TextView user_greeting=(TextView)hView.findViewById(R.id.user_greeting);
        String greetings="Hello, "+user.getFname()+" "+user.getLname();
        user_greeting.setText(greetings);
//        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_add_wardrobe, R.id.nav_show_wardrobe, R.id.nav_fetch_outfit, R.id.nav_history)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_cloth_me_nav);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        File image=new File(contentUri.getPath());
        res=image.getAbsolutePath();
        return res;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cloth_me_nav, menu);


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                db.logOut(MainActivity.user.getUsername());
                Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show();
                MainActivity.user=null;
                Intent i=new Intent(this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
        }
        return false;

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_cloth_me_nav);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public Boolean reqPerm(){
        ActivityCompat.requestPermissions(
                ClothMeNav.this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                },
                1
        );
        return checkPermission();
    }
    public Boolean checkPermission(){
        Boolean perm1,perm2;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(MainActivity.this, "READ GRANTED", Toast.LENGTH_SHORT).show();
            perm1=true;
        } else{
//            Toast.makeText(MainActivity.this, "READ DENIED", Toast.LENGTH_SHORT).show();
            perm1=false;
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(MainActivity.this, "WRITE GRANTED", Toast.LENGTH_SHORT).show();
            perm2=true;
        }else{
//            Toast.makeText(MainActivity.this, "WRITE DENIED", Toast.LENGTH_SHORT).show();
            perm2=false;
        }
        return perm1&&perm2;
    }
}