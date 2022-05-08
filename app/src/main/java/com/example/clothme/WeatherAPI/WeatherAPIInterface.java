package com.example.clothme.WeatherAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPIInterface {
    @GET("onecall")
    Call<WeatherPojo> getWeather(@Query("lat") double lat,
                                 @Query("lon") double lon,
                                 @Query("exclude") String exclude,
                                 @Query("units") String units,
                                 @Query("lang") String lang,
                                 @Query("appid") String apikey);
}
