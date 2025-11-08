package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.model.Weather;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private final List<Weather> items;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Weather item);
    }

    public WeatherAdapter(List<Weather> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new WeatherViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        Weather w = items.get(position);
        holder.tvDate.setText(w.getDate());
        holder.tvDesc.setText(w.getDescription());
        holder.tvTemp.setText(w.getTemperature() + "°C");
        holder.itemView.setOnClickListener(v -> listener.onItemClick(w));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView tvDate, tvDesc, tvTemp;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgWeatherIcon);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvTemp = itemView.findViewById(R.id.tvTemp);
        }
    }
}
