package com.example.clothme.ui.home;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.clothme.Adapter.ImageAdapterAdd;
import com.example.clothme.Adapter.ImageAdapterHome;
import com.example.clothme.App_Input1;
import com.example.clothme.Database.ClothesDB;
import com.example.clothme.MainActivity;
import com.example.clothme.Models.ImageModel;
import com.example.clothme.Models.WeatherData;
import com.example.clothme.R;
import com.example.clothme.WeatherAPI.RetrofitInstance;
import com.example.clothme.WeatherAPI.TimeStampToDate;
import com.example.clothme.WeatherAPI.WeatherAPIInterface;
import com.example.clothme.WeatherAPI.WeatherPojo;
import com.example.clothme.databinding.FragmentHomeBinding;
import com.example.clothme.ui.DisplayWardrobe.SlideshowFragment;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    Button viewMore, refresh;
    RecyclerView viewSome;
    String temp = null;
    String clouds = null;
    String iconUrl = null;
    TextView tempDisplay, envType;
    ImageView weatherImage, image;
    private final String apiKey = "c61c0ec2728a6842089e132b6658ea46";
    static WeatherPojo pojo = null;
    private static final int REQUEST_LOCATION = 1;
    FusedLocationProviderClient fusedLocationProviderClient;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private LocationRequest locationRequest;
    static double Latitude, Longitude;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewMore = root.findViewById(R.id.bt_viewWardrobe);
        viewSome = root.findViewById(R.id.id_recycle_view5);
        refresh = root.findViewById(R.id.id_weather_refresh);
        tempDisplay = root.findViewById(R.id.id_temperature);
        envType = root.findViewById(R.id.id_envType);
        weatherImage = root.findViewById(R.id.id_weatherImage);
        if (temp != null && clouds != null) {
            tempDisplay.setText(temp + "°C");
            envType.setText(clouds);
            Glide.with(requireActivity().getApplicationContext()).load(iconUrl).into(weatherImage);
        }
        if(checkPermission())
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    if (isGPSEnabled()) {
                        getLocation();
                        Log.v("AAA", Latitude + " " + Longitude);
                        if (Latitude != 0.0 && Longitude != 0.0) {
                            WeatherAPIInterface weatherAPIInterface = RetrofitInstance.getRetrofit().create(WeatherAPIInterface.class);
                            weatherAPIInterface.getWeather(Latitude, Longitude, "minutely,hourly,alerts", "metric", "en", apiKey).enqueue(new Callback<WeatherPojo>() {
                                @Override
                                public void onResponse(Call<WeatherPojo> call, Response<WeatherPojo> response) {
                                    if (response.code() != 404) {
//                        Toast.makeText(getContext(),"list present",Toast.LENGTH_SHORT).show();
                                        pojo = response.body();
                                        String txt = "";
                                        Log.d("AAA", response.toString());
                                        temp = Integer.toString(Math.round(pojo.getCurrent().getTemp()));
                                        clouds = pojo.getCurrent().getWeather().get(0).getMain();
                                        Log.d("AAA", temp + " " + clouds);
                                        tempDisplay.setText(temp + "°C");
                                        envType.setText(clouds);
                                        String icon = pojo.getCurrent().getWeather().get(0).getIcon();
                                        iconUrl = "https://openweathermap.org/img/wn/" + icon + "@2x.png";
                                        Log.v("AAA", iconUrl);
//                                        Picasso.get().load(iconUrl).resize(20,20).centerCrop().into(weatherImage);
//                                        image1=Glide.with(requireActivity().getApplicationContext()).load(iconUrl).into(image1);
                                        Glide.with(requireActivity().getApplicationContext()).load(iconUrl).into(weatherImage);
                                    } else {
                                        Toast.makeText(getContext(), "List Empty", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<WeatherPojo> call, Throwable t) {

                                }
                            });
                        }
                    } else {
                        turnOnGPS();
                        Toast.makeText(getContext(), "Please turn on the location", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    reqPerm();
                }
            }
        });
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity().getApplicationContext());
        ClothesDB clothdb = new ClothesDB(getActivity());
        ArrayList<ImageModel> get_Cloth = clothdb.getImage(MainActivity.user.getUsername(), null, getContext());
        ArrayList<ImageModel> list_of_cloth = new ArrayList<>();
        int counter = 0;
        for (int i = get_Cloth.size() - 1; i >= 0; i--) {
            if (counter < 6) {
                list_of_cloth.add(get_Cloth.get(i));
                counter++;
            }
        }
        ImageAdapterHome adapter = new ImageAdapterHome(list_of_cloth, getContext());
//        Toast.makeText(getContext(),list_of_cloth.size()+"",Toast.LENGTH_SHORT).show();
        viewSome.setAdapter(adapter);
        StaggeredGridLayoutManager staggered = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        viewSome.setLayoutManager(staggered);

//        checkPermission();
        if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            WeatherAPIInterface weatherAPIInterface = RetrofitInstance.getRetrofit().create(WeatherAPIInterface.class);
            weatherAPIInterface.getWeather(Latitude, Longitude, "minutely,hourly,alerts", "metric", "en", apiKey).enqueue(new Callback<WeatherPojo>() {
                @Override
                public void onResponse(Call<WeatherPojo> call, Response<WeatherPojo> response) {
                    if (response.code() != 404) {
//                        Toast.makeText(getContext(),"list present",Toast.LENGTH_SHORT).show();
                        pojo = response.body();
                        String txt = "";
                        Log.d("AAA", response.toString());
                        temp = Integer.toString(Math.round(pojo.getCurrent().getTemp()));
                        clouds = pojo.getCurrent().getWeather().get(0).getMain();
                        Log.d("AAA", temp + " " + clouds);
                        tempDisplay.setText(temp + "°C");
                        envType.setText(clouds);
                        String icon = pojo.getCurrent().getWeather().get(0).getIcon();
                        iconUrl = "https://openweathermap.org/img/wn/" + icon + "@2x.png";
                        Log.v("AAA", iconUrl);
//                                        Picasso.get().load(iconUrl).resize(20,20).centerCrop().into(weatherImage);
//                                        image1=Glide.with(requireActivity().getApplicationContext()).load(iconUrl).into(image1);
                        Glide.with(requireActivity().getApplicationContext()).load(iconUrl).into(weatherImage);
                    } else {
                        Toast.makeText(getContext(), "List Empty", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<WeatherPojo> call, Throwable t) {

                }
            });
        }

        viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlideshowFragment fragment = new SlideshowFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_cloth_me_nav, fragment, fragment.getTag());
//                ActionBar a=getSu
//                fragmentTransaction.tit


                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    private void turnOnGPS() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(requireActivity().getApplicationContext())
                .checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(getContext(), "GPS is already tured on", Toast.LENGTH_SHORT).show();
                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(getActivity(), 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });
    }

    public boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
    }

    public Boolean reqPerm() {
        requestPermissions(
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA
                },
                1
        );
        return checkPermission();
    }

    public Boolean checkPermission() {
        Boolean perm1, perm2, perm3,perm4;
        if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            perm1 = true;
        } else {
            perm1 = false;
        }
        if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            perm2 = true;
        } else {
            perm2 = false;
        }
        if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            perm3 = true;
        } else {
            perm3 = false;
        }
        if (ContextCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            perm4 = true;
        } else {
            perm4 = false;
        }
        return perm1 && perm2 && perm3 && perm4;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    try {
                        List<Address> addressList1 = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        Latitude = addressList1.get(0).getLatitude();
                        Longitude = addressList1.get(0).getLongitude();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "Location not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }
}