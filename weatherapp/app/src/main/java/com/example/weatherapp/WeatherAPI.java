package com.example.weatherapp;

import com.example.weatherapp.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI{
    @GET("weather")
    Call<WeatherResponse> getWeather(
            @Query("key") String apiKey,
            @Query("city_name") String cidade
    );
}
