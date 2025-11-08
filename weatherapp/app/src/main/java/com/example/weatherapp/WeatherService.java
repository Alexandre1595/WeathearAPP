package com.example.weatherapp;

import com.example.weatherapp.WeatherAPI;
import com.example.weatherapp.model.Weather;
import com.example.weatherapp.model.WeatherResponse;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherService {

    private static final String BASE_URL = "https://api.hgbrasil.com/";
    private static final String API_KEY = "9792537c";

    private static WeatherAPI api;

    private static WeatherAPI getApi() {
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(WeatherAPI.class);
        }
        return api;
    }

    public static void fetchWeather(String cidade, final Callback callback) {
        getApi().getWeather(API_KEY, cidade).enqueue(new retrofit2.Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Weather> list = Weather.fromResults(response.body().getResults());
                    callback.onResult(list);
                } else {
                    callback.onResult(null);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                callback.onResult(null);
            }
        });
    }


    public interface Callback {
        void onResult(List<Weather> weatherList);
    }
}
