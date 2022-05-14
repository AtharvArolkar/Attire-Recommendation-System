package com.example.clothme;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clothme.Database.AccountDB;
import com.example.clothme.Models.UserModel;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button logIn;
    String user_name;
    String password_Text;
    TextView createAcc;
    public static UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        AccountDB accountDB = new AccountDB(this);
        user_name = accountDB.checkLoggedInAcc();
//        ClothMeNav ch=new ClothMeNav();
            if (user_name != null) {
                user = accountDB.getAcc(user_name);

                Intent i = new Intent(MainActivity.this, ClothMeNav.class);
//            Toast.makeText(this,"Hi",Toast.LENGTH_SHORT).show();
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                MainActivity.this.finish();
            } else {
                username = findViewById(R.id.id_username);
                password = findViewById(R.id.id_password);
                logIn = findViewById(R.id.id_login);
                createAcc = findViewById(R.id.id_createAcc);
                createAcc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, App_Input1.class);
                        startActivity(i);
                    }
                });
                logIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"Button closed temporarily, will open after adding database, for now click on create account",Toast.LENGTH_LONG).show();
                        user_name = username.getText().toString();
                        password_Text = password.getText().toString();
                        if (user_name.equals("")) {
                            Toast.makeText(MainActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();
                        } else {
                            if (password_Text.equals("")) {
                                Toast.makeText(MainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                            } else {
                                if (accountDB.checkAvailability(user_name)) {
                                    Toast.makeText(MainActivity.this, "No accounts found with username " + user_name, Toast.LENGTH_SHORT).show();
                                } else {
                                    user = accountDB.getAcc(user_name);
                                    if (password_Text.equals(user.getPassword())) {
                                        username.setText("");
                                        password.setText("");
                                        accountDB.logIn(user_name);
                                        Intent i = new Intent(MainActivity.this, ClothMeNav.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                        MainActivity.this.finish();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Password Invalid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }
                });



                createAcc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(MainActivity.this,App_Input1.class);
                        startActivity(i);
                    }
                });



            }

    }

}