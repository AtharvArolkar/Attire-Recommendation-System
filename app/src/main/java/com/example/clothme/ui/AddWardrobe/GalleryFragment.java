package com.example.clothme.ui.AddWardrobe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.clothme.Adapter.ImageAdapterAdd;
import com.example.clothme.ClothMeNav;
import com.example.clothme.Color.ColorDetector;
import com.example.clothme.Database.ClothesDB;
import com.example.clothme.MainActivity;
import com.example.clothme.Models.ImageModel;
import com.example.clothme.Models.UserModel;
import com.example.clothme.R;
import com.example.clothme.databinding.FragmentGalleryBinding;
import com.example.clothme.ml.Female91;
import com.example.clothme.ml.Male96Pool;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    RecyclerView displayImages;
    Button addClothes;
    Button refresh;
    Button done;
    String gender;
    TextView textName;
    private Bitmap bitmapImage;
    private ArrayList<String> imageText = new ArrayList<>();
    int ReqCode = 21;
    UserModel user = MainActivity.user;
    public static String fabric=null;
    public static String[] options;
    public static ImageModel im;
    public static Uri path;
    public static Bitmap img;
    ClothesDB clothdb;
    public ArrayList<ImageModel> images = new ArrayList<>();

//    String[] menlabels = new String[]{"Blazers","Innerwear Vests","Jackets","Jeans","Long Sleeves Shirt","Lounge Pants","Shirts",
//            "Shorts","Sweaters","Track Pants","Trousers","T-Shirts"};

    String[] menlabels = new String[]{"Blazers", "Innerwear Vests", "Jackets", "Jeans", "Shirt", "Long Sleeves Shirt", "Lounge Pants", "Pajama",
            "Sherwanis", "Shirt", "Shorts", "Sweaters", "Track Pants", "Tracksuits", "Trousers", "T-Shirts"};




    String[] womenlabels=new String[]{"Jackets","Pants","Shorts","Skirts","Tank Tops","Tops", "Blazers","Capri & Cropped Pants","Cardigans","Coats",
            "Dresses","Dresses","Dresses","Gowns","Hoodies","Jackets","Jeans","Jumpsuits", "Skirts","Leggings","Skirts","Skirts","Pants","Shirts","Shorts",
            "Skirts","Suits","Sweaters","Sweatshirts","T-Shirts","Tank Tops","Tights","Tops","Vests"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        clothdb = new ClothesDB(getActivity());
        gender = user.getGender();
//        Intent i=getIntent();
//        Bundle bundle=getIntent().getExtras();

//        textName.setText("Hello "+user.getFname()+" "+user.getLname());


        displayImages = root.findViewById(R.id.displayImages);
        addClothes = root.findViewById(R.id.id_addPic);
        done = root.findViewById(R.id.id_doneUpload);
//        Toast.makeText(getContext(),""+user.getUsername(),Toast.LENGTH_SHORT).show();
//        images=clothdb.getImage(user.getUsername());
//        Toast.makeText(getContext(),"sdsdsd",Toast.LENGTH_SHORT).show();
//        ImageAdapter adapter=new ImageAdapter(images, getContext());
//        displayImages.setAdapter(adapter);
//        StaggeredGridLayoutManager staggered=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
//        displayImages.setLayoutManager(staggered);

        addClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose an Option");
                builder.setIcon(R.drawable.logo);
//                builder.setCancelable(false);
                options = new String[]{"Camera", "Gallery"};
                //Pass the array list in Alert dialog
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                if (checkPermission()) {
                                    dispatchTakePictureIntent();
                                } else {
                                    if (reqPerm()) {
                                        dispatchTakePictureIntent();
                                    } else {
                                        Toast.makeText(getContext(), "You Have to Grant Permission in order to use this app", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                break;
                            case 1:
                                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                startActivityForResult(gallery, ReqCode);
                                break;
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ClothMeNav.class);
                startActivity(intent);
            }
        });
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
//            Toast.makeText(getContext(),"Image Not Captured",Toast.LENGTH_SHORT).show();
        }
    }
    public void ImageCropFunction(Uri uri) {

        // Image Crop Code
        try {
            Intent CropIntent = new Intent("com.android.camera.action.CROP");
            Toast.makeText(getContext(),"plz, Crop the Required part",Toast.LENGTH_LONG).show();
            CropIntent.setDataAndType(uri, "image/*");
//
            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 1024);
            CropIntent.putExtra("outputY", 1024);
            CropIntent.putExtra("return-data", true);
            CropIntent.putExtra("return-uri", uri.toString());
            startActivityForResult(CropIntent, 222);
        }catch (ActivityNotFoundException e) {
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        path=null;
        try {
            if (requestCode == ReqCode && resultCode == Activity.RESULT_OK && data != null) {
                path = data.getData();
                ImageCropFunction(path);
            }
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                bitmapImage = (Bitmap) extras.get("data");
                try {
                    path = saveImage(bitmapImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ImageCropFunction(path);
            }
            if (requestCode == 222) {
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    bitmapImage = bundle.getParcelable("data");
                    im = new ImageModel();
                    im.setPic(bitmapImage);
                    img = Bitmap.createScaledBitmap(bitmapImage, 256, 256, true);
                    im = modelEvaluate(im, img);
                    images.add(im);
                    try{
                        ContentResolver resolver=getContext().getContentResolver();
                        OutputStream op=resolver.openOutputStream(path);
                        bitmapImage.compress(Bitmap.CompressFormat.JPEG,100,op);
                        Objects.requireNonNull(op);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("What is the fabric of the cloth?");
                builder.setIcon(R.drawable.logo);
                builder.setCancelable(false);
                options = new String[]{"Cotton", "Wool", "Nylon/Polyester", "Silk"};
                final int[] checkedItem = {-1};
                builder.setSingleChoiceItems(options, checkedItem[0], new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedItem[0] = which;

                        fabric = options[which];
                        ColorDetector cd = new ColorDetector();
                        String color = cd.getColor(img);
                        if(color=="NULL"){
                            Toast.makeText(getContext(),"Cant Detect Color, Plz Add Manually",Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(getContext(),""+color,Toast.LENGTH_LONG).show();
                        Boolean success = clothdb.insertData(user.getUsername(), path, im.getName(), color, fabric);
                        if (success) {
                            Toast.makeText(getContext(), "Inserted Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Failed To Insert", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog1 = builder.create();
                dialog1.show();
//
                ImageAdapterAdd adapter = new ImageAdapterAdd(images, getContext());
                displayImages.setAdapter(adapter);
                StaggeredGridLayoutManager staggered = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                displayImages.setLayoutManager(staggered);
            }
        }catch (NullPointerException e){
            Toast.makeText(getContext(),"Image Not Captured",Toast.LENGTH_SHORT).show();
        }
    }
    public ImageModel modelEvaluate(ImageModel im, Bitmap img) {
        try {
            Context context;
            if (gender.equals("Male")) {

                Male96Pool model = Male96Pool.newInstance(getContext());
                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 256, 256, 3}, DataType.FLOAT32);
                TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                tensorImage.load(img);

                ByteBuffer byteBuffer = tensorImage.getBuffer();
                inputFeature0.loadBuffer(byteBuffer);
                Male96Pool.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                model.close();
                float[] out = outputFeature0.getFloatArray();
                float max = 0;
                int index = 0;
                for (int i = 0; i < out.length; i++) {
                    if (out[i] > max) {
                        index = i;
                        max = out[i];
                    }
                }
                im.setText(menlabels[index]);
            } else if (gender.equals("Female")) {
                Female91 model = Female91.newInstance(getContext());
                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 256, 256, 3}, DataType.FLOAT32);
                TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                tensorImage.load(img);
                ByteBuffer byteBuffer = tensorImage.getBuffer();
                inputFeature0.loadBuffer(byteBuffer);
                Female91.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                model.close();
                float[] out = outputFeature0.getFloatArray();
                float max = 0;
                int index = 0;
                for (int i = 0; i < out.length; i++) {
                    if (out[i] > max) {
                        index = i;
                        max = out[i];
                    }
                }
                im.setText(womenlabels[index]);
            }
        } catch (IOException e) {
            // TODO Handle the exception
            Toast.makeText(getContext(), "Failed to Load Model", Toast.LENGTH_SHORT).show();
        }
        return im;
    }
    private Uri saveImage(Bitmap bitmap) throws IOException {
        Uri ImageCollection = null;
        ContentResolver resolver=getContext().getContentResolver();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            ImageCollection=MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        }else{
            ImageCollection=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
        ContentValues cv=new ContentValues();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String mImageName="ClothMe"+ timeStamp +".jpg";
        cv.put(MediaStore.Images.Media.DISPLAY_NAME,mImageName);
        cv.put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg");
        Uri imageUri=resolver.insert(ImageCollection,cv);
        try{
            OutputStream op=resolver.openOutputStream(Objects.requireNonNull(imageUri));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,op);
            Objects.requireNonNull(op);
        }catch(Exception e){
            e.printStackTrace();
        }
        return imageUri;
    }

    public Boolean reqPerm() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                },
                1
        );
        return checkPermission();
    }
    public Boolean checkPermission() {
        Boolean perm1, perm2;
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "READ GRANTED", Toast.LENGTH_SHORT).show();
            perm1 = true;
        } else {
            Toast.makeText(getContext(), "READ DENIED", Toast.LENGTH_SHORT).show();
            perm1 = false;
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "WRITE GRANTED", Toast.LENGTH_SHORT).show();
            perm2 = true;
        } else {
            Toast.makeText(getContext(), "WRITE DENIED", Toast.LENGTH_SHORT).show();
            perm2 = false;
        }
        return perm1 && perm2;
    }


}