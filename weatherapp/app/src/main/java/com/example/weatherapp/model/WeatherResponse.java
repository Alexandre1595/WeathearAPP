package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {

    @SerializedName("results")
    private Results results;

    public Results getResults() {
        return results;
    }

    public static class Results {

        @SerializedName("city")
        private String cityName;

        @SerializedName("temp")
        private double temp;

        @SerializedName("description")
        private String description;

        @SerializedName("img_id")
        private String imgId;

        @SerializedName("date")
        private String date;

        @SerializedName("forecast")
        private List<Forecast> forecast;

        // Getters
        public String getCityName() { return cityName; }
        public double getTemp() { return temp; }
        public String getDescription() { return description; }
        public String getImgId() { return imgId; }
        public String getDate() { return date; }
        public List<Forecast> getForecast() { return forecast; }
    }

    public static class Forecast {

        @SerializedName("date")
        private String date;

        @SerializedName("description")
        private String description;

        @SerializedName("max")
        private double max;

        @SerializedName("min")
        private double min;

        @SerializedName("condition")
        private String condition;

        // Getters
        public String getDate() { return date; }
        public String getDescription() { return description; }
        public double getMax() { return max; }
        public double getMin() { return min; }
        public String getCondition() { return condition; }
    }
}
