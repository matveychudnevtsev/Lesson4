package ru.mirea.chudnevtsevmr.serviceapp;

import static android.Manifest.permission.FOREGROUND_SERVICE;
import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ru.mirea.chudnevtsevmr.serviceapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private int PermissionCode = 200;
    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Разрешения получены");
        } else {
            Log.d(TAG, "Нет разрешений!");
            ActivityCompat.requestPermissions(this, new String[] {POST_NOTIFICATIONS, FOREGROUND_SERVICE}, PermissionCode);
        }

        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(MainActivity.this, PlayerService.class);
                ContextCompat.startForegroundService(MainActivity.this, serviceIntent);
            }
        });
        binding.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, PlayerService.class));
            }
        });
    }
}