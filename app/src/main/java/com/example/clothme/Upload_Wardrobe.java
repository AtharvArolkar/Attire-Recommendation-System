//package com.example.clothme;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.StaggeredGridLayoutManager;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.clothme.Adapter.ImageAdapter;
//import com.example.clothme.Database.ClothesDB;
//import com.example.clothme.Models.ImageModel;
//import com.example.clothme.Models.UserModel;
//import com.example.clothme.ml.Female71Pool;
//import com.example.clothme.ml.Male96Pool;
//
//import org.tensorflow.lite.DataType;
//import org.tensorflow.lite.support.image.TensorImage;
//import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class Upload_Wardrobe extends AppCompatActivity {
//    RecyclerView displayImages;
//    Button addClothes;
//    Button refresh;
//    Button done;
//    String gender;
//    TextView textName;
//    private Bitmap image1;
//    private ArrayList<String> imageText = new ArrayList<>();
//    int ReqCode=21;
//    ClothesDB db;
//    public ArrayList<ImageModel> images=new ArrayList<>();
//    String[] menlabels=new String[]{"Blazers","Innerwear Vests","Jackets","Jeans","Kurtas","Long Sleeves Shirt","Lounge Pants","Pajama",
//            "Sherwanis","Shirts","Shorts","Sweaters","Track Pants","Tracksuits","Trousers","Tshirts"};
//
//    String [] womenlabels=new String[]{"Blazers","Capris","Churidar","Dresses","Jackets","Jeans","Shirts","Shorts","Skirts","Sweaters",
//            "Tops","Track Pants","Trousers","Tshirts","dhoti_pants","gowns","leggings","lehenga","palazzos","saree"};
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_upload_wardrobe);
//        getSupportActionBar().hide();
//
//        textName=findViewById(R.id.textViewName);
//        Intent i=getIntent();
//        Bundle bundle=getIntent().getExtras();
////        UserModel user =new UserModel(bundle.getString("username"),bundle.getString("fname"),bundle.getString("lname"),
////                bundle.getString("gender"),bundle.getString("age"),bundle.getString("password"));
//        gender=user.getGender();
//        textName.setText("Hello "+user.getFname()+" "+user.getLname());
//
//        db=new ClothesDB(this);
//        displayImages=findViewById(R.id.displayImages);
//        addClothes=findViewById(R.id.id_addPic);
//        refresh=findViewById(R.id.id_refresh);
//        done=findViewById(R.id.id_doneUpload);
//
//        refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ImageAdapter adapter=new ImageAdapter(images,Upload_Wardrobe.this);
//                displayImages.setAdapter(adapter);
//                StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
//                displayImages.setLayoutManager(staggered);
//            }
//        });
//
//        addClothes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent();
//                i.setType("image/*");
//                i.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(i,ReqCode);
//
//            }
//        });
//        done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Upload_Wardrobe.this,Homepage.class);
//                startActivity(intent);
//            }
//        });
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==ReqCode && resultCode==RESULT_OK && data!=null){
//            Uri path=data.getData();
//            try {
//                image1= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
//                ImageModel im=new ImageModel();
//                im.setPic(image1);
//
//                Bitmap img=Bitmap.createScaledBitmap(image1,256,256,true);
//                try {
//                    Context context;
//                    if(gender.equals("Male")){
//
//                        Male96Pool model = Male96Pool.newInstance(getApplicationContext());
//                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 256, 256, 3}, DataType.FLOAT32);
//                        TensorImage tensorImage=new TensorImage(DataType.FLOAT32);
//                        tensorImage.load(img);
//
//                        ByteBuffer byteBuffer=tensorImage.getBuffer();
//                        inputFeature0.loadBuffer(byteBuffer);
//                        Male96Pool.Outputs outputs = model.process(inputFeature0);
//                        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
//                        model.close();
//                        float[] out=outputFeature0.getFloatArray();
//                        float max=0;
//                        int index=0;
//                        for(int i=0;i<out.length;i++){
//                            if(out[i]>max){
//                                index=i;
//                                max=out[i];
//                            }
//                        }
//                        im.setText(menlabels[index]);
//                    }
//                    else if(gender.equals("Female")){
//                        Female71Pool model = Female71Pool.newInstance(getApplicationContext());
//                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 256, 256, 3}, DataType.FLOAT32);
//                        TensorImage tensorImage=new TensorImage(DataType.FLOAT32);
//                        tensorImage.load(img);
//                        ByteBuffer byteBuffer=tensorImage.getBuffer();
//                        inputFeature0.loadBuffer(byteBuffer);
//                        Female71Pool.Outputs outputs = model.process(inputFeature0);
//                        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
//                        model.close();
//                        float[] out=outputFeature0.getFloatArray();
//                        float max=0;
//                        int index=0;
//                        for(int i=0;i<out.length;i++){
//                            if(out[i]>max){
//                                index=i;
//                                max=out[i];
//                            }
//                        }
//                        im.setText(womenlabels[index]);
//                    }
//                    images.add(im);
//                } catch (IOException e) {
//                    // TODO Handle the exception
//                    Toast.makeText(this,"Failed to Load Model",Toast.LENGTH_SHORT).show();
//                }
//                ImageAdapter adapter=new ImageAdapter(images,Upload_Wardrobe.this);
//                displayImages.setAdapter(adapter);
//                StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
//                displayImages.setLayoutManager(staggered);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//}