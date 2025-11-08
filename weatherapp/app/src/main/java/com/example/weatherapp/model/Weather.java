package com.example.weatherapp.model;

import java.util.ArrayList;
import java.util.List;

public class Weather {
    private final String date;
    private final String description;
    private final int temperature;
    private final String cidade;
    private final String iconUrl;

    public Weather(String date, String description, int temperature, String iconUrl, String cidade) {
        this.date = date;
        this.description = description;
        this.temperature = temperature;
        this.cidade = cidade;
        this.iconUrl = iconUrl;
    }

    public String getDate() { return date; }

    public String getDescription() { return description; }
    public int getTemperature() { return temperature; }
    public String getIconUrl() { return iconUrl; }
    public String getCidade() { return cidade; }


    /**
     * Converte WeatherResponse.Results em List<Weather>
     */
    public static List<Weather> fromResults(com.example.weatherapp.model.WeatherResponse.Results results) {
        List<Weather> list = new ArrayList<>();

        // Clima atual
        Weather current = new Weather(
                results.getDate(), // data atual
                results.getDescription(),
                (int) results.getTemp(),
                getIconUrlFromImgId(results.getImgId()),
                results.getCityName()
        );
        list.add(current);

        // Previsões futuras
        if (results.getForecast() != null) {
            for (com.example.weatherapp.model.WeatherResponse.Forecast f : results.getForecast()) {
                Weather w = new Weather(
                        f.getDate(),
                        f.getDescription(),
                        (int) f.getMax(),
                        getIconUrlFromCondition(f.getCondition()), // gera url do ícone
                        results.getCityName()
                );
                list.add(w);
            }
        }

        return list;
    }


    private static String getIconUrlFromImgId(String imgId) {
        if(imgId == null || imgId.isEmpty()) return "";
        return "https://www.hgbrasil.com/weather/icons/" + imgId + ".png";
    }

    private static String getIconUrlFromCondition(String condition) {
        if(condition == null || condition.isEmpty()) return "";
        return "https://www.hgbrasil.com/weather/icons/" + condition + ".png";
    }
}
