package com.example.weatherapp;

public class WeatherItem {
    private String day;
    private String condition;
    private int temperature;

    public WeatherItem(String day, String condition, int temperature){
        this.day = day;
        this.condition = condition;
        this.temperature = temperature;
    }

    public String getDay() { return day; }
    public String getCondition() { return condition; }
    public int getTemperature() { return temperature; }
}
