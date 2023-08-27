package ru.mirea.chudnevtsevmr.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread{
    public Handler mHandler;

    public void run() {
        Log.d("MyLooper", "run");
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()) {
            public void handleMessage(Message msg) {
                String data = msg.getData().getString("job");
                Log.d("MyLooper", "Работа: "+ data);
            }
        };
        Looper.loop();
    }
}