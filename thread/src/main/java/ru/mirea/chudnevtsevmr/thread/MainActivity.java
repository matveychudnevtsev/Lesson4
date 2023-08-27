package ru.mirea.chudnevtsevmr.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import ru.mirea.chudnevtsevmr.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String pairs = String.valueOf(binding.pairs.getText());
                        String days = String.valueOf(binding.days.getText());
                        float aprox = Float.parseFloat(pairs) / Float.parseFloat(days);
                        binding.resultView.setText("Среднее количество пар в день: " + aprox);
                    }
                }).start();
            }
        });
    }
}