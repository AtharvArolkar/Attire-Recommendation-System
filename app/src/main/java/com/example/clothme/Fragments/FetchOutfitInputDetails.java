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
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.clothme.MainActivity;
import com.example.clothme.MapActivity;
import com.example.clothme.R;
import com.example.clothme.WeatherAPI.RetrofitInstance;
import com.example.clothme.WeatherAPI.TimeStampToDate;
import com.example.clothme.WeatherAPI.WeatherAPIInterface;
import com.example.clothme.WeatherAPI.WeatherPojo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FetchOutfitInputDetails extends Fragment {
    Spinner events;
    String locationSelected="",dateSelected="",eventSelected="",timeSelected="";
    EditText date,editTextlocation;
    Button generate;
    Button locate,timePick;
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

                                timePick.setText(hourOfDay + ":" + minute);
                                timeSelected=hourOfDay + ":" + minute;
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
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
                        dateSelected=day+"/"+month+"/"+year;
                        date.setText(dateSelected);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        generate=view.findViewById(R.id.generateOutfit);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!eventSelected.equals("") && !eventSelected.equals("Select Event")){
                    if(!locationSelected.equals("")){
                        if(!dateSelected.equals("")){
                            if(!timeSelected.equals("")){
                                FrameLayout linearDisplay=view.findViewById(R.id.linearDisplay);
                                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                                FetchOutfitViewOnGoing ff=new FetchOutfitViewOnGoing();
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
            WeatherAPIInterface weatherAPIInterface= RetrofitInstance.getRetrofit().create(WeatherAPIInterface.class);
            weatherAPIInterface.getWeather(lat,lon,"minutely,hourly,alerts","metric","en",apiKey).enqueue(new Callback<WeatherPojo>() {
                @Override
                public void onResponse(Call<WeatherPojo> call, Response<WeatherPojo> response) {
                    if(response.code()!=404){
//                        Toast.makeText(getContext(),"list present",Toast.LENGTH_SHORT).show();
                        WeatherPojo pojo=response.body();
                        String txt="";
                        TimeStampToDate t=new TimeStampToDate();
                        txt=txt+"Date"+"      : "+"Max Temperature"+"\n";
                        for(int i=0;i<pojo.getDaily().size();i++){
                            txt=txt+t.convertTimeStamp(pojo.getDaily().get(i).getDt())+" : "+pojo.getDaily().get(i).getTemp().getMax()+"\n";
                        }
                        Log.v("Aaaaaa",txt);
//                        ArrayList< Object > daily_forecast=pojo.get
                        Toast.makeText(getContext(),txt,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getContext(),"LIst Empty",Toast.LENGTH_SHORT).show();
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