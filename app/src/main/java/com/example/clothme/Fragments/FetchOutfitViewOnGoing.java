package com.example.clothme.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clothme.ClothMeNav;
import com.example.clothme.Database.ClothesDB;
import com.example.clothme.Database.HistoryDB;
import com.example.clothme.MainActivity;
import com.example.clothme.Models.HistoryModel;
import com.example.clothme.Models.ReturnClothLogicModelLists;
import com.example.clothme.R;
import com.example.clothme.Recommendation.ShoeRecommend;
import com.example.clothme.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.Locale;

public class FetchOutfitViewOnGoing extends Fragment {
    Button back, next, prev, accept;
    ImageView topwear, bottomwear, outerwear, footwear;
    TextView topTV, bottomTV, outerTv, shoeTv, shoeTvTitle, shoeTvRecommend,pleaseCarry;
    int position = 0, temperature;
    String event,dateText,timeText,locationText,weatherText,dayTime;
    ImageView accessoriesRecommend1,accessoriesRecommend2;
    LinearLayout alsoRecommend;

    public FetchOutfitViewOnGoing() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fetch_outfit_view_on_going, container, false);
        back = view.findViewById(R.id.back_button);
        next = view.findViewById(R.id.next);
        prev = view.findViewById(R.id.prev);
        accept = view.findViewById(R.id.accept);
        topwear = view.findViewById(R.id.id_top);
        bottomwear = view.findViewById(R.id.id_botton);
        outerwear = view.findViewById(R.id.id_outer);
//        footwear=view.findViewById(R.id.id_footwear);
        topTV = view.findViewById(R.id.id_toptv);
        bottomTV = view.findViewById(R.id.id_bottomtv);
        outerTv = view.findViewById(R.id.id_outertv);
        shoeTv = view.findViewById(R.id.id_shoeTv);
        shoeTvTitle = view.findViewById(R.id.id_shoeTvTitle);
        shoeTvRecommend = view.findViewById(R.id.id_shoeTvRecommend);
        pleaseCarry=view.findViewById(R.id.id_pleaseCarry);
        accessoriesRecommend1=view.findViewById(R.id.id_pleaseCarryImage1);
        accessoriesRecommend2=view.findViewById(R.id.id_pleaseCarryImage2);
        alsoRecommend=view.findViewById(R.id.alsoRecomment);

        topwear.setImageResource(R.drawable.not_found);
        bottomwear.setImageResource(R.drawable.not_found);
        outerwear.setImageResource(R.drawable.not_found);

//        footwear.setImageResource(R.drawable.formal);
        topTV.setText("TopWear");
        bottomTV.setText("BottomWear");
        outerTv.setText("OuterWear");
        shoeTvTitle.setText("Footwear");
        shoeTvRecommend.setText("Recommended");
//        shoeTv.setText("Recommended: Sandals, Slippers");

        Bundle b = getArguments();
        temperature = b.getInt("Temperature");
        event = b.getString("Event");
        dateText=b.getString("DateOfEvent");
        timeText=b.getString("TimeOfEvent");
        locationText=b.getString("LocationOfEvent");
        weatherText=b.getString("Weather");
        dayTime=b.getString("DayTime");

//        Toast.makeText(getContext(), weatherText, Toast.LENGTH_SHORT).show();
//        weatherText="clear sky";
        if(weatherText.toLowerCase(Locale.ROOT).equals("clear sky")){
            if(dayTime.equals("Morning")|| dayTime.equals("Afternoon")){
                pleaseCarry.setText("Please Carry:");
                accessoriesRecommend1.setImageResource(R.drawable.sunglasses);
                accessoriesRecommend2.setImageResource(R.drawable.cap);
            }
        }else if(weatherText.toLowerCase(Locale.ROOT).equals("rain")){
            pleaseCarry.setText("Please Carry:");
            accessoriesRecommend1.setImageResource(R.drawable.raincoat);
            accessoriesRecommend2.setImageResource(R.drawable.umbrella);
        }else{
            alsoRecommend.setVisibility(View.GONE);
        }
        ArrayList<ReturnClothLogicModelLists> listofPairs = (ArrayList<ReturnClothLogicModelLists>) b.getSerializable("ListOfPairs");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout linearDisplay = view.findViewById(R.id.linearDisplay);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                FetchOutfitInputDetails ff = new FetchOutfitInputDetails();
                transaction.replace(R.id.linearDisplay, ff);
                transaction.commit();
            }
        });
        Log.v("Temp", "Hi");
        Log.v("TempTemp", "" + temperature);
        Log.v("TempEvent", "" + event);
        ShoeRecommend shoeRecommend=new ShoeRecommend();
        shoeTv.setText(shoeRecommend.shoesSuggest(event,MainActivity.user.getGender()));
        ReturnClothLogicModelLists rl = listofPairs.get(position);
        for (int i = 0; i < rl.getPairs().size(); i++) {
            if (rl.getPairs().get(i).size() > 1) {
                Uri imageUri = Uri.parse(rl.getPairs().get(i).get(5));
                String name = rl.getPairs().get(i).get(2);
                if (i == 0) {
                    topwear.setImageURI(imageUri);
                    topTV.setText(name);
                } else if (i == 1) {
                    bottomwear.setImageURI(imageUri);
                    bottomTV.setText(name);
                } else if (i == 2) {
                    outerwear.setImageURI(imageUri);
                    outerTv.setText(name);
                }
            }
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++position;
                if (position < listofPairs.size()) {
                    ReturnClothLogicModelLists rl = listofPairs.get(position);
                    for (int i = 0; i < rl.getPairs().size(); i++) {
                        if (rl.getPairs().get(i).size() > 1) {
                            Uri imageUri = Uri.parse(rl.getPairs().get(i).get(5));
                            String name = rl.getPairs().get(i).get(2);
                            if (i == 0) {
                                topwear.setImageURI(imageUri);
                                topTV.setText(name);
                            } else if (i == 1) {
                                bottomwear.setImageURI(imageUri);
                                bottomTV.setText(name);
                            } else if (i == 2 ) {
                                outerwear.setImageURI(imageUri);
                                outerTv.setText(name);
                            }
                        }
                    }
                } else {
                    --position;
                    Toast.makeText(getContext(), "You Have Reached the Last Outfit in the List", Toast.LENGTH_SHORT).show();
                }
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                --position;
                if (position >= 0) {
                    ReturnClothLogicModelLists rl = listofPairs.get(position);
                    for (int i = 0; i < rl.getPairs().size(); i++) {
                        if (rl.getPairs().get(i).size() > 1) {
                            Uri imageUri = Uri.parse(rl.getPairs().get(i).get(5));
                            String name = rl.getPairs().get(i).get(2);
                            if (i == 0) {
                                topwear.setImageURI(imageUri);
                                topTV.setText(name);
                            } else if (i == 1) {
                                bottomwear.setImageURI(imageUri);
                                bottomTV.setText(name);
                            } else if (i == 2) {
                                outerwear.setImageURI(imageUri);
                                outerTv.setText(name);
                            }
                        } else {
                            if (i == 0) {
                                topwear.setImageResource(R.drawable.not_found);
                                topTV.setText("TopWear");
                            } else if (i == 1) {
                                bottomwear.setImageResource(R.drawable.not_found);
                                bottomTV.setText("BottomWear");
                            } else if (i == 2) {
                                outerwear.setImageResource(R.drawable.not_found);
                                outerTv.setText("OuterWear");
                            }
                        }
                    }
                } else {
                    ++position;
                    Toast.makeText(getContext(), "This is the First Outfit in the List", Toast.LENGTH_SHORT).show();
                }
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryDB db=new HistoryDB(getContext());
                String username = MainActivity.user.getUsername();
                String topId = null;
                String bottomId = null;
                String outerId = null;
                ReturnClothLogicModelLists rl = listofPairs.get(position);
                for (int i = 0; i < rl.getPairs().size(); i++) {
                    if (rl.getPairs().get(i).size() > 1) {
                        Uri imageUri = Uri.parse(rl.getPairs().get(i).get(5));
                        String name = rl.getPairs().get(i).get(2);
                        if (i == 0) {
                            topId = rl.getPairs().get(i).get(1);
                        } else if (i == 1) {
                            bottomId = rl.getPairs().get(i).get(1);
                        } else if (i == 2) {
                            outerId = rl.getPairs().get(i).get(1);
                        }
                    }
                }
                HistoryModel hm = new HistoryModel(0, username,event,dateText,timeText,locationText,topId,bottomId,outerId);
                Boolean success=db.insertData(hm);
//                Boolean success = true;
                if (success) {
                    ClothesDB clothesDB=new ClothesDB(getContext());
                    for (int i = 0; i < rl.getPairs().size(); i++) {
                        if (rl.getPairs().get(i).size() > 1) {
                            if (i == 0) {
                                topId = rl.getPairs().get(i).get(1);
                                clothesDB.updateLastWorn(dateText,topId);
                            } else if (i == 1) {
                                bottomId = rl.getPairs().get(i).get(1);
                                clothesDB.updateLastWorn(dateText,bottomId);
                            } else if (i == 2) {
                                outerId = rl.getPairs().get(i).get(1);
                                clothesDB.updateLastWorn(dateText,outerId);
                            }
                        }
                    }
                    AlertDialog.Builder builder
                            = new AlertDialog
                            .Builder(getContext());
                    builder.setTitle("Event Registered Successfully");
                    builder.setMessage("Want To Register More Events?");
                    builder.setCancelable(false);
                    builder
                            .setPositiveButton(
                                    "Register More Events",
                                    new DialogInterface
                                            .OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                                            FetchOutfitInputDetails ff = new FetchOutfitInputDetails();
                                            transaction.replace(R.id.linearDisplay, ff);
                                            transaction.commit();
                                        }
                                    });
                    builder
                            .setNegativeButton(
                                    "Go Back To Home Page",
                                    new DialogInterface
                                            .OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            Intent intent = new Intent(getContext(), ClothMeNav.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                } else {
                    Toast.makeText(getContext(), "Failed to Register Event, Please Try again after some time", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.v("QQQlislength", "" + listofPairs.size());
        Log.v("QQQpairlength", "" + rl.getPairs().size());
        return view;
    }
}