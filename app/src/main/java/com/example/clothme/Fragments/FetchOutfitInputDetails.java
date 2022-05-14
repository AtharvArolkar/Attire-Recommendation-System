package com.example.clothme.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.clothme.Database.ClothesDB;
import com.example.clothme.MainActivity;
import com.example.clothme.MapActivity;
import com.example.clothme.Models.ClothesModel;
import com.example.clothme.Models.ImageModel;
import com.example.clothme.Models.WeatherData;
import com.example.clothme.R;
import com.example.clothme.Recommendation.ClothLogic;
import com.example.clothme.WeatherAPI.DailyPojo;
import com.example.clothme.WeatherAPI.RetrofitInstance;
import com.example.clothme.WeatherAPI.TimeStampToDate;
import com.example.clothme.WeatherAPI.WeatherAPIInterface;
import com.example.clothme.WeatherAPI.WeatherPojo;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FetchOutfitInputDetails extends Fragment {
    Spinner events;
    String locationSelected="",dateSelected="",eventSelected="",timeSelected="";
    TextView date;
    static public int hour,difference;
    ArrayList<WeatherData> weatherData=new ArrayList<WeatherData>();
    TextView editTextlocation;
    Button generate;
    Button locate,timePick;
    static WeatherPojo pojo=null;
    private final String apiKey="c61c0ec2728a6842089e132b6658ea46";
    private final String url="https://api.openweathermap.org/data/2.5/weather";
    DecimalFormat decimalFormat=new DecimalFormat("#.##");
    DatePickerDialog.OnDateSetListener setListener;
    public FetchOutfitInputDetails() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fetch_outfit_input_details, container, false);
        events= view.findViewById(R.id.spinnerEvent);

//        @SuppressLint("ResourceType") ArrayAdapter<CharSequence> adapter=new  ArrayAdapter<CharSequence>(getContext(), R.layout.spinner_list, R.array.Events);
//        arrayAdapter.setDropDownViewResource(R.layout.spinner_list)
//        spinner.adapter = arrayAdapter
//        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.Events, R.layout.spinner_list);
        locate=view.findViewById(R.id.getLocationButton);
        editTextlocation=view.findViewById(R.id.editTextTextPersonName);
        timePick=view.findViewById(R.id.timePick);
        timePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                FetchOutfitInputDetails.hour=hourOfDay;
                                timePick.setText(hourOfDay + ":" + minute);
                                timeSelected=hourOfDay + ":" + minute;

                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        editTextlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), MapActivity.class);
                startActivityForResult(i,10101);
            }
        });
        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), MapActivity.class);
                startActivityForResult(i,10101);
            }
        });
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.Events, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        events.setAdapter(adapter);
        events.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position)!="Select Event"){
                    eventSelected=parent.getItemAtPosition(position).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(getContext(),"Please Select the event",Toast.LENGTH_SHORT).show();
            }
        });
        date=view.findViewById(R.id.editTextDate);

        Calendar calendar=Calendar.getInstance();
        final int year= calendar.get(Calendar.YEAR);
        final int month= calendar.get(Calendar.MONTH);
        final int day= calendar.get(Calendar.DAY_OF_MONTH);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDay = sdf.format(new Date());
                        String selectedDate=year+"-"+month+"-"+day;
//                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar c = Calendar.getInstance();
                        try {
                            c.setTime(sdf.parse(currentDay));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        c.add(Calendar.DATE, 7);
                        String afterDate=sdf.format(c.getTime());
//                        Toast.makeText(getContext(),sdf.format(c.getTime()),Toast.LENGTH_SHORT).show();
//                        java.time.LocalDate
                        int dateDifference = (int) getDateDiff(new SimpleDateFormat("yyyy-MM-dd"), selectedDate, afterDate);
                        difference=(int) getDateDiff(new SimpleDateFormat("yyyy-MM-dd"), currentDay,selectedDate);
                        if(dateDifference>=0){
                            dateSelected=year+"/"+month+"/"+day;
//                            difference=dateDifference;
                            date.setText(dateSelected);
                        }else{
                            date.setText("");
                            Toast.makeText(getContext(),"Date Limit Exceeded,Please Select a date in next 7 days i.e before "+afterDate,Toast.LENGTH_LONG).show();
                        }

                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

                datePickerDialog.show();
            }
        });
        generate=view.findViewById(R.id.generateOutfit);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!eventSelected.equals("") && !eventSelected.equals("Select Event")){
                    if(!locationSelected.equals("") && pojo!=null){
                        if(!dateSelected.equals("")){
                            if(!timeSelected.equals("")){
                                int j=0;
                                Log.v("AAA",""+difference);
                                float temperature;
                                if(hour<12 && hour >=5){
                                    temperature=weatherData.get(difference).getMor();
                                }else if(hour<17 && hour >=12){
                                    temperature=weatherData.get(difference).getAff();
                                }else if(hour<20 && hour >=17){
                                    temperature=weatherData.get(difference).getEve();
                                }else{
                                    temperature=weatherData.get(difference).getNig();
                                }
                                Log.d("AAA",""+temperature);
//                                ClothLogic c=new ClothLogic();
//                                ClothesDB db=new ClothesDB(getContext());
//                                ArrayList<ClothesModel> images=db.getImageList(MainActivity.user.getUsername(),getContext());
//                                c.convertData(images,12,eventSelected);
                                FrameLayout linearDisplay=view.findViewById(R.id.linearDisplay);
                                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                                FetchOutfitSelectBase ff=new FetchOutfitSelectBase();
                                Bundle b=new Bundle();
                                b.putInt("Temperature",Math.round(temperature));
                                b.putString("Event",eventSelected);
                                ff.setArguments(b);
                                transaction.replace(R.id.linearDisplay, ff);
                                eventSelected="";
                                locationSelected="";
                                dateSelected="";
//                                timePick.setText("Select Time");
                                transaction.commit();
                            }else{
                                Toast.makeText(getContext(),"Please Select Time",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getContext(),"Please Enter Date",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(),"Please Select Location",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(),"Please Enter Event",Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }
    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 10101){
            locationSelected=data.getData().toString();
            String city=data.getStringExtra("city");
            String state=data.getStringExtra("state");
            String country=data.getStringExtra("country");
            double lat=data.getDoubleExtra("lat",0.0);
            double lon=data.getDoubleExtra("long",0.0);
            locationSelected=city+","+state;
            WeatherAPIInterface weatherAPIInterface= RetrofitInstance.getRetrofit().create(WeatherAPIInterface.class);
            weatherAPIInterface.getWeather(lat,lon,"minutely,hourly,alerts","metric","en",apiKey).enqueue(new Callback<WeatherPojo>() {
                @Override
                public void onResponse(Call<WeatherPojo> call, Response<WeatherPojo> response) {
                    if(response.code()!=404){
//                        Toast.makeText(getContext(),"list present",Toast.LENGTH_SHORT).show();
                        pojo=response.body();
                        String txt="";
                        TimeStampToDate t=new TimeStampToDate();
                        Log.d("AAA",response.toString());
                        txt=txt+"Date"+"      : "+"Max Temperature"+"\n";
                        for(int i=0;i<pojo.getDaily().size();i++){
                           String date=new TimeStampToDate().convertTimeStamp(pojo.getDaily().get(i).getDt());
                           float mor=pojo.getDaily().get(i).getTemp().getMorn();
                           float aff=pojo.getDaily().get(i).getTemp().getDay();
                           float eve=pojo.getDaily().get(i).getTemp().getEve();
                           float nig=pojo.getDaily().get(i).getTemp().getNight();
                           WeatherData wm=new WeatherData(date,mor,aff,eve,nig);
                           weatherData.add(wm);
                        }
//                        ArrayList< Object > daily_forecast=pojo.get
//                        Toast.makeText(getContext(),txt,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getContext(),"List Empty",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<WeatherPojo> call, Throwable t) {

                }
            });
            editTextlocation.setText(locationSelected);
        }
    }
}