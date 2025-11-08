package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherapp.model.Weather;
import com.example.weatherapp.viewmodel.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class WeatherFragment extends Fragment {

    private static final int REQUEST_QR = 123;

    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    private FloatingActionButton fab;
    private SharedViewModel sharedViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerWeather);
        fab = view.findViewById(R.id.fabScan);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Inicializa SharedViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Carrega clima inicial da API sem cidade fixa
        carregarClima(null);

        // FAB QRCode
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), QrScannerActivity.class);
            startActivityForResult(intent, REQUEST_QR);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_QR && resultCode == getActivity().RESULT_OK && data != null) {
            String cidade = data.getStringExtra("cidade");
            if (cidade != null && !cidade.isEmpty()) {
                carregarClima(cidade);
            }
        }
    }

    private void carregarClima(@Nullable String cidade) {
        WeatherService.fetchWeather(cidade, new WeatherService.Callback() {
            @Override
            public void onResult(List<Weather> weatherList) {
                if (isAdded() && weatherList != null && !weatherList.isEmpty()) {
                    adapter = new WeatherAdapter(weatherList, weather -> {
                        sharedViewModel.setWeather(weather);
                        Toast.makeText(requireContext(),
                                "Cidade selecionada: " + weather.getCidade(),
                                Toast.LENGTH_SHORT).show();
                    });
                    recyclerView.setAdapter(adapter);

                    // Atualiza a cidade inicial com o primeiro item da API
                    sharedViewModel.setWeather(weatherList.get(0));
                }
            }
        });
    }
}
