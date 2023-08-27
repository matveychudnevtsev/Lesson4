package ru.mirea.chudnevtsevmr.cryptoloader;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {
    private byte[] key;
    private byte[] encrypted;

    public MyLoader(@NonNull Context context, Bundle bundle) {
        super(context);
        if (bundle != null) {
            key = bundle.getByteArray("key");
            encrypted = bundle.getByteArray("message");
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        SecretKey secretKey = new SecretKeySpec(this.key, 0, this.key.length, "AES");
        String message = decryptMsg(encrypted, secretKey);
        return message;
    }

    public static String decryptMsg(byte[] cipherText, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(cipherText));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}