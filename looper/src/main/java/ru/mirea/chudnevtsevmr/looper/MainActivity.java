package ru.mirea.chudnevtsevmr.looper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import ru.mirea.chudnevtsevmr.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MyLooper myLooper = new MyLooper();
        myLooper.start();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String job = String.valueOf(binding.jobInput.getText());
                String age = String.valueOf(binding.ageInput.getText());
                long delayMills = Long.parseLong(age) * 1000;

                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("job", job);
                msg.setData(bundle);

                myLooper.mHandler.sendMessageDelayed(msg, delayMills);
            }
        });
    }
}