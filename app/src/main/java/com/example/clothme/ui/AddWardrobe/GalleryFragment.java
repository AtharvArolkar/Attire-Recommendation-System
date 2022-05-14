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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
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

import com.example.clothme.Adapter.ImageAdapter;
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
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.yalantis.ucrop.UCrop;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
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

    String[] menlabels = new String[]{"Blazer", "Innerwear Vests", "Jacket", "Jeans", "Shirt", "Long Sleeves Shirt", "Lounge Pants", "Pajama",
            "Sherwanis", "Shirt", "Shorts", "Sweaters", "Track Pants", "Tracksuits", "Trousers", "T-Shirt"};




    String[] womenlabels=new String[]{"Jacket","Pants","Shorts","Skirts","Tank Tops","Tops", "Blazer","Capri & Cropped Pants","Cardigans","Coats",
            "Dresses","Dresses","Dresses","Gowns","Hoodies","Jackets","Jeans","Jumpsuits", "Skirts","Leggings","Skirts","Skirts","Pants","Shirt","Shorts",
            "Skirts","Suits","Sweaters","Sweatshirts","T-Shirt","Tank Tops","Tights","Tops","Vests"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        clothdb = new ClothesDB(getActivity());
        gender = user.getGender();
        displayImages = root.findViewById(R.id.displayImages);
        addClothes = root.findViewById(R.id.id_addPic);
        done = root.findViewById(R.id.id_doneUpload);
        addClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(GalleryFragment.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(2410);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        path=null;
        try {
            if (resultCode == Activity.RESULT_OK && requestCode == 2410) {
                final Uri resultUri = data.getData();
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    im = new ImageModel();
                    im.setPic(bitmapImage);
                    img = Bitmap.createScaledBitmap(bitmapImage, 256, 256, true);
                    im = modelEvaluate(im, img);
                    images.add(im);
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
                            if(color== null){
                                Toast.makeText(getContext(),"Cant Detect Color, Plz Add Manually",Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(getContext(),""+color,Toast.LENGTH_LONG).show();
                            Boolean success = clothdb.insertData(user.getUsername(), resultUri, im.getName(), color, fabric);
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (NullPointerException e){
            Toast.makeText(getContext(),"Image Not Captured",Toast.LENGTH_SHORT).show();
            Log.d("Error",e.toString());
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
}