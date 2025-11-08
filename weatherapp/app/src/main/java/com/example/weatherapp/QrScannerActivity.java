package com.example.weatherapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class QrScannerActivity extends AppCompatActivity {

    private ActivityResultLauncher<ScanOptions> qrLauncher;
    private ActivityResultLauncher<String> permissionLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializa o launcher do scanner
        qrLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result != null && result.getContents() != null) {
                Intent intent = new Intent();
                intent.putExtra("cidade", result.getContents());
                setResult(Activity.RESULT_OK, intent);
            } else {
                setResult(Activity.RESULT_CANCELED);
            }
            finish();
        });

        // Inicializa o launcher de permissão
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        openScanner();
                    } else {
                        setResult(Activity.RESULT_CANCELED);
                        finish();
                    }
                }
        );

        // Verifica permissão antes de abrir
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            openScanner();
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void openScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Aponte para o QR Code da cidade");
        options.setBeepEnabled(true);
        options.setOrientationLocked(false);
        options.setCaptureActivity(ScannerCaptureActivity.class);

        qrLauncher.launch(options);
    }
}
