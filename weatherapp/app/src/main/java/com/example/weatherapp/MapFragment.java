package com.example.weatherapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.maplibre.android.MapLibre;
import org.maplibre.android.camera.CameraPosition;
import org.maplibre.android.camera.CameraUpdateFactory;
import org.maplibre.android.geometry.LatLng;
import org.maplibre.android.maps.MapView;
import org.maplibre.android.maps.MapLibreMap;
import org.maplibre.android.maps.OnMapReadyCallback;
import org.maplibre.android.maps.Style;
import org.maplibre.android.annotations.MarkerOptions;

import com.example.weatherapp.model.Weather;
import com.example.weatherapp.viewmodel.SharedViewModel;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private MapLibreMap mapLibreMap;
    private SharedViewModel sharedViewModel;

    private static final String MAPTILER_KEY = "nI6TlUP6cq6hC3Cuv8vj";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if(getContext() != null) MapLibre.getInstance(getContext());
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // Inicializa SharedViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Observa mudanças de Weather selecionado
        sharedViewModel.getWeather().observe(getViewLifecycleOwner(), weather -> {
            if (weather != null) {
                moverParaCidade(weather.getCidade());
            }
        });
    }

    @Override
    public void onMapReady(@NonNull MapLibreMap mapLibreMap) {
        this.mapLibreMap = mapLibreMap;

        String styleUrl = "https://api.maptiler.com/maps/streets/style.json?key=" + MAPTILER_KEY;
        mapLibreMap.setStyle(new Style.Builder().fromUri(styleUrl), style -> {
             moverParaCidade("rio de janeiro");
        });
    }


    private void moverParaCidade(String cidade) {
        if (mapLibreMap == null || mapLibreMap.getStyle() == null) return;

        LatLng coordenadas;
        switch (cidade.toLowerCase()) {
            case "rio de janeiro": coordenadas = new LatLng(-22.9068, -43.1729); break;
            case "belo horizonte": coordenadas = new LatLng(-19.9167, -43.9345); break;
            case "curitiba": coordenadas = new LatLng(-25.4284, -49.2733); break;
            default: coordenadas = new LatLng(-23.5505, -46.6333); // São Paulo
        }

        // Atualiza marcador
        mapLibreMap.clear();
        mapLibreMap.addMarker(new MarkerOptions().position(coordenadas).title(cidade));

        // Move câmera suavemente
        CameraPosition position = new CameraPosition.Builder()
                .target(coordenadas)
                .zoom(10)
                .build();
        mapLibreMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000);
    }

    @Override public void onStart() { super.onStart(); mapView.onStart(); }
    @Override public void onResume() { super.onResume(); mapView.onResume(); }
    @Override public void onPause() { super.onPause(); mapView.onPause(); }
    @Override public void onStop() { super.onStop(); mapView.onStop(); }
    @Override public void onLowMemory() { super.onLowMemory(); mapView.onLowMemory(); }
    @Override public void onDestroyView() { super.onDestroyView(); mapView.onDestroy(); }
}
