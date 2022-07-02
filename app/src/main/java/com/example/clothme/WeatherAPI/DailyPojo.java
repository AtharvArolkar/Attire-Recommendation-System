package com.example.clothme.WeatherAPI;

import java.util.ArrayList;

public class DailyPojo {
    private float dt;
    private float sunrise;
    private float sunset;
    private float moonrise;
    private float moonset;
    private float moon_phase;
    Temp temp;
    Feels_like feels_like;
    private float pressure;
    private float humidity;
    private float dew_point;
    private float wind_speed;
    private float wind_deg;
    private float wind_gust;
    ArrayList< weather > weather = new ArrayList < weather > ();
    private float clouds;
    private float pop;
    private float uvi;

    public DailyPojo(float dt, float sunrise, float sunset, float moonrise, float moonset, float moon_phase, Temp tempObject, Feels_like feels_likeObject, float pressure, float humidity, float dew_point, float wind_speed, float wind_deg, float wind_gust, ArrayList<weather> weather, float clouds, float pop, float uvi) {
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.moon_phase = moon_phase;
        temp = tempObject;
        feels_like = feels_likeObject;
        this.pressure = pressure;
        this.humidity = humidity;
        this.dew_point = dew_point;
        this.wind_speed = wind_speed;
        this.wind_deg = wind_deg;
        this.wind_gust = wind_gust;
        this.weather = weather;
        this.clouds = clouds;
        this.pop = pop;
        this.uvi = uvi;
    }
// Getter Methods

    public float getDt() {
        return dt;
    }

    public float getSunrise() {
        return sunrise;
    }

    public float getSunset() {
        return sunset;
    }

    public float getMoonrise() {
        return moonrise;
    }

    public float getMoonset() {
        return moonset;
    }

    public float getMoon_phase() {
        return moon_phase;
    }

    public Temp getTemp() {
        return temp;
    }

    public Feels_like getFeels_like() {
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

    public float getWind_speed() {
        return wind_speed;
    }

    public float getWind_deg() {
        return wind_deg;
    }

    public float getWind_gust() {
        return wind_gust;
    }

    public float getClouds() {
        return clouds;
    }

    public float getPop() {
        return pop;
    }

    public float getUvi() {
        return uvi;
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

    public void setMoonrise(float moonrise) {
        this.moonrise = moonrise;
    }

    public void setMoonset(float moonset) {
        this.moonset = moonset;
    }

    public void setMoon_phase(float moon_phase) {
        this.moon_phase = moon_phase;
    }

    public void setTemp(Temp tempObject) {
        this.temp = tempObject;
    }

    public void setFeels_like(Feels_like feels_likeObject) {
        this.feels_like = feels_likeObject;
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

    public void setWind_speed(float wind_speed) {
        this.wind_speed = wind_speed;
    }

    public void setWind_deg(float wind_deg) {
        this.wind_deg = wind_deg;
    }

    public void setWind_gust(float wind_gust) {
        this.wind_gust = wind_gust;
    }

    public void setClouds(float clouds) {
        this.clouds = clouds;
    }

    public void setPop(float pop) {
        this.pop = pop;
    }

    public void setUvi(float uvi) {
        this.uvi = uvi;
    }

    public ArrayList<DailyPojo.weather> getWeather() { return weather; }

    public static class Feels_like {
        private float day;
        private float night;
        private float eve;
        private float morn;

        public Feels_like(float day, float night, float eve, float morn) {
            this.day = day;
            this.night = night;
            this.eve = eve;
            this.morn = morn;
        }


        // Getter Methods

        public float getDay() {
            return day;
        }

        public float getNight() {
            return night;
        }

        public float getEve() {
            return eve;
        }

        public float getMorn() {
            return morn;
        }

        // Setter Methods

        public void setDay(float day) {
            this.day = day;
        }

        public void setNight(float night) {
            this.night = night;
        }

        public void setEve(float eve) {
            this.eve = eve;
        }

        public void setMorn(float morn) {
            this.morn = morn;
        }
    }
    public static class Temp {
        private float day;
        private float min;
        private float max;
        private float night;
        private float eve;
        private float morn;

        public Temp(float day, float min, float max, float night, float eve, float morn) {
            this.day = day;
            this.min = min;
            this.max = max;
            this.night = night;
            this.eve = eve;
            this.morn = morn;
        }


        // Getter Methods

        public float getDay() {
            return day;
        }

        public float getMin() {
            return min;
        }

        public float getMax() {
            return max;
        }

        public float getNight() {
            return night;
        }

        public float getEve() {
            return eve;
        }

        public float getMorn() {
            return morn;
        }

        // Setter Methods

        public void setDay(float day) {
            this.day = day;
        }

        public void setMin(float min) {
            this.min = min;
        }

        public void setMax(float max) {
            this.max = max;
        }

        public void setNight(float night) {
            this.night = night;
        }

        public void setEve(float eve) {
            this.eve = eve;
        }

        public void setMorn(float morn) {
            this.morn = morn;
        }
    }

    public static class weather{
        private float id;
        private String main;
        private String description;
        private String icon;

        public weather(float id, String main, String description, String icon) {
            this.id = id;
            this.main = main;
            this.description = description;
            this.icon = icon;
        }


        // Getter Methods

        public float getId() {
            return id;
        }

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }

        // Setter Methods

        public void setId(float id) {
            this.id = id;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}


