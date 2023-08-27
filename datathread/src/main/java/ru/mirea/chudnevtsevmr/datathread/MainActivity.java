package ru.mirea.chudnevtsevmr.datathread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import ru.mirea.chudnevtsevmr.datathread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Runnable runnable1 = new Runnable() {
            public void run() {
                binding.test.setText("runnable1");
            }
        };
        final Runnable runnable2 = new Runnable() {
            public void run() {
                binding.test.setText("runnable2");
            }
        };
        final Runnable runnable3 = new Runnable() {
            public void run() {
                binding.test.setText("runnable3");
            }
        };
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runnable1);
                    TimeUnit.SECONDS.sleep(1);
                    binding.test.postDelayed(runnable3, 2000);
                    binding.test.post(runnable2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}