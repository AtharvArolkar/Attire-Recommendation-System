package com.example.clothme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.clothme.Database.AccountDB;
import com.example.clothme.Models.UserModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.IOException;

public class EditProfile extends AppCompatActivity {
    Button upload,update,cancel;
    ImageView profilePic;

    String first_name=MainActivity.user.getFname();
    String last_name=MainActivity.user.getLname();
    String user_age=MainActivity.user.getAge();
    String pass_word=MainActivity.user.getPassword();
    Uri profilePath=MainActivity.user.getProfilePic();

    EditText fname;
    EditText lname;
    EditText age;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        upload=findViewById(R.id.id_editImage);
        update=findViewById(R.id.id_update);
        cancel=findViewById(R.id.id_cancel);
        fname=findViewById(R.id.id_fname);
        lname=findViewById(R.id.id_lname);
        age=findViewById(R.id.id_age);
        password=findViewById(R.id.id_passwordEdit);
        profilePic=findViewById(R.id.id_profileEdit);

        fname.setText(first_name);
        lname.setText(last_name);
        age.setText(user_age);
        password.setText(pass_word);
        profilePic.setImageURI(profilePath);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(EditProfile.this)
                        .crop()
                        .cropSquare()//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(2410);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first_name=fname.getText().toString();
                last_name=lname.getText().toString();
                user_age=age.getText().toString();
                pass_word=password.getText().toString();
                UserModel um=new UserModel(MainActivity.user.getUsername(),first_name,last_name,MainActivity.user.getGender(),user_age,pass_word,profilePath);
                AccountDB db=new AccountDB(EditProfile.this);
                db.updateAccount(um);
                MainActivity.user=um;
                Intent i=new Intent(EditProfile.this,ClothMeNav.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 2410) {
            profilePath = data.getData();
//            profilePath=resultUri;
            try {
                Bitmap image= MediaStore.Images.Media.getBitmap(getContentResolver(),profilePath);
                profilePic.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}