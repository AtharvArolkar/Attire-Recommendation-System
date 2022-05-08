package com.example.clothme.WeatherAPI;

import java.util.ArrayList;

public class WeatherPojo {
        private float lat;
        private float lon;
        private String timezone;
        private float timezone_offset;
        CurrentWeatherPojo current;
        ArrayList<DailyPojo> daily = new ArrayList<DailyPojo>();


        // Getter Methods

        public float getLat() {
            return lat;
        }

        public float getLon() {
            return lon;
        }

        public String getTimezone() {
            return timezone;
        }

        public float getTimezone_offset() {
            return timezone_offset;
        }

        public CurrentWeatherPojo getCurrent() {
            return current;
        }

        // Setter Methods

        public void setLat(float lat) {
            this.lat = lat;
        }

        public void setLon(float lon) {
            this.lon = lon;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public void setTimezone_offset(float timezone_offset) {
            this.timezone_offset = timezone_offset;
        }

    public ArrayList<DailyPojo> getDaily() {
        return daily;
    }

    public void setDaily(ArrayList<DailyPojo> daily) {
        this.daily = daily;
    }

    public void setCurrent(CurrentWeatherPojo current) {
            this.current = current;
        }
}
