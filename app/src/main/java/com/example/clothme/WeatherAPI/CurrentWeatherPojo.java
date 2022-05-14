package com.example.clothme.WeatherAPI;

import java.util.ArrayList;

public class CurrentWeatherPojo {
    private float dt;
    private float sunrise;
    private float sunset;
    private float temp;
    private float feels_like;
    private float pressure;
    private float humidity;
    private float dew_point;
    private float uvi;
    private float clouds;
    private float visibility;
    private float wind_speed;
    private float wind_deg;
    private float wind_gust;
    ArrayList< DailyPojo.weather > weather = new ArrayList < DailyPojo.weather>();


    // Getter Methods



    public CurrentWeatherPojo(float dt, float sunrise, float sunset, float temp, float feels_like, float pressure, float humidity, float dew_point, float uvi, float clouds, float visibility, float wind_speed, float wind_deg, float wind_gust, ArrayList<DailyPojo.weather> weather) {
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temp = temp;
        this.feels_like = feels_like;
        this.pressure = pressure;
        this.humidity = humidity;
        this.dew_point = dew_point;
        this.uvi = uvi;
        this.clouds = clouds;
        this.visibility = visibility;
        this.wind_speed = wind_speed;
        this.wind_deg = wind_deg;
        this.wind_gust = wind_gust;
        this.weather = weather;
    }

    public float getDt() {
        return dt;
    }

    public float getSunrise() {
        return sunrise;
    }

    public float getSunset() {
        return sunset;
    }

    public float getTemp() {
        return temp;
    }

    public float getFeels_like() {
        return feels_like;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getDew_point() {
        return dew_point;
    }

    public float getUvi() {
        return uvi;
    }

    public float getClouds() {
        return clouds;
    }

    public float getVisibility() {
        return visibility;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public float getWind_deg() {
        return wind_deg;
    }

    public float getWind_gust() {
        return wind_gust;
    }

    // Setter Methods

    public void setDt(float dt) {
        this.dt = dt;
    }

    public void setSunrise(float sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(float sunset) {
        this.sunset = sunset;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public void setFeels_like(float feels_like) {
        this.feels_like = feels_like;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public void setDew_point(float dew_point) {
        this.dew_point = dew_point;
    }

    public void setUvi(float uvi) {
        this.uvi = uvi;
    }

    public void setClouds(float clouds) {
        this.clouds = clouds;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public void setWind_speed(float wind_speed) {
        this.wind_speed = wind_speed;
    }

    public void setWind_deg(float wind_deg) {
        this.wind_deg = wind_deg;
    }

    public void setWind_gust(float wind_gust) {
        this.wind_gust = wind_gust;
    }

    public ArrayList<DailyPojo.weather> getWeather() { return weather; }
}
