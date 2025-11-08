package com.example.weatherapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.weatherapp.model.Weather;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<Weather> selectedWeather = new MutableLiveData<>();

    public void setWeather(Weather weather) {
        selectedWeather.setValue(weather);
    }

    public LiveData<Weather> getWeather() {
        return selectedWeather;
    }
}
