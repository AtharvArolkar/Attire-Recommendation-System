package com.example.clothme;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clothme.Database.AccountDB;
import com.example.clothme.Models.UserModel;
import com.example.clothme.ui.AddWardrobe.GalleryFragment;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.IOException;

public class App_Input1 extends AppCompatActivity {
    String name;
//    TextView text;
    Button upload;
    ImageView profilePic;
    private Bitmap image;
    RadioGroup GenderGroup;
    RadioButton Gender;
    Button next;

    String first_name;
    String last_name;
    String gender;
    String user_age;
    String user_name;
    String pass_word;
    Uri profilePath;

    EditText fname;
    EditText lname;
    EditText age;
    EditText username;
    EditText password;
    TextView goToLogin;
//    Uri profilePic;
    int ReqCode=21;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_input1);
        getSupportActionBar().hide();
        upload=findViewById(R.id.id_upload);
        next=findViewById(R.id.id_next);

        fname=findViewById(R.id.id_firstname);
        lname=findViewById(R.id.id_lastname);
        age=findViewById(R.id.id_password);
        username=findViewById(R.id.id_username_signin);
        password=findViewById(R.id.id_password_sigin);

        profilePic=findViewById(R.id.profilePic);
        GenderGroup=findViewById(R.id.id_gender);
        goToLogin=findViewById(R.id.id_signinToLogin);
        AccountDB accountDB=new AccountDB(this);

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(App_Input1.this,MainActivity.class);
                startActivity(i);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first_name=fname.getText().toString();
                last_name=lname.getText().toString();
                user_age=age.getText().toString();
                user_name=username.getText().toString();
                pass_word=password.getText().toString();
                int radioID=GenderGroup.getCheckedRadioButtonId();
                Gender=findViewById(radioID);

                try {
                    if(first_name.equals("")||last_name.equals("")||user_age.equals("")||user_name.equals("")||pass_word.equals("")||gender.equals("")){
                        Toast.makeText(App_Input1.this,"Please Enter the All Details",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(profilePic.getDrawable()==null){
                            Toast.makeText(App_Input1.this,"Please Input the Image",Toast.LENGTH_SHORT).show();
                        }else{
                            if(accountDB.checkAvailability(user_name)){
                                UserModel user=new UserModel(user_name,first_name,last_name,gender,user_age,pass_word,profilePath);
                                Boolean i=accountDB.createAcc(user);
                                if(i) {Toast.makeText(App_Input1.this,"Account Created, Proceed to login",Toast.LENGTH_SHORT).show();
                                fname.setText("");
                                lname.setText("");
                                age.setText("");
                                username.setText("");
                                password.setText("");
                                Intent intent=new Intent(App_Input1.this,MainActivity.class);
                                startActivity(intent);
                                }
                                else{Toast.makeText(App_Input1.this,"Error Creating Account",Toast.LENGTH_SHORT).show();}
                            }else{
                                Toast.makeText(App_Input1.this,"Username Already Taken",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(App_Input1.this,"Plz Enter Gender",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqPerm();
                if(reqPerm()){
                    ImagePicker.with(App_Input1.this)
                            .crop()
                            .cropSquare()//Crop image(Optional), Check Customization for more option
                            .compress(1024)			//Final image size will be less than 1 MB(Optional)
                            .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                            .start(2410);
                }else{
                    Toast.makeText(App_Input1.this,"Plz grant all permissions in-order to use this app",Toast.LENGTH_LONG).show();

                }

            }
        });
    }
    public void checkButton(View v){
        int radioID=GenderGroup.getCheckedRadioButtonId();
        Gender=findViewById(radioID);
        gender=Gender.getText().toString();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 2410) {
            final Uri resultUri = data.getData();
            profilePath=resultUri;
            try {
                image= MediaStore.Images.Media.getBitmap(getContentResolver(),resultUri);
                profilePic.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean reqPerm(){
        ActivityCompat.requestPermissions(
                App_Input1.this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                },
                1
        );
        return checkPermission();
    }
    public Boolean checkPermission(){
        Boolean perm1,perm2,perm3;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(MainActivity.this, "READ GRANTED", Toast.LENGTH_SHORT).show();
            perm1=true;
        } else{

            perm1=false;
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(MainActivity.this, "WRITE GRANTED", Toast.LENGTH_SHORT).show();
            perm2=true;
        }else{
//            Toast.makeText(MainActivity.this, "WRITE DENIED", Toast.LENGTH_SHORT).show();
            perm2=false;
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(MainActivity.this, "WRITE GRANTED", Toast.LENGTH_SHORT).show();
            perm3=true;
        }else{
//            Toast.makeText(MainActivity.this, "WRITE DENIED", Toast.LENGTH_SHORT).show();
            perm3=false;
        }
        return perm1&&perm2&&perm3;
    }
}