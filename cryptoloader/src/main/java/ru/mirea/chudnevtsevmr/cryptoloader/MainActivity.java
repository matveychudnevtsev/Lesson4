package ru.mirea.chudnevtsevmr.cryptoloader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ru.mirea.chudnevtsevmr.cryptoloader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private ActivityMainBinding binding;

    private String TAG = this.getClass().getSimpleName();
    private int loaderId = 87;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainActivity activity = this;

        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = binding.input.getText().toString();

                SecretKey key = MainActivity.generateKey();
                byte[] encrypted = MainActivity.encryptMsg(text, key);

                Bundle	bundle = new Bundle();
                bundle.putByteArray("key", key.getEncoded());
                bundle.putByteArray("message", encrypted);

                LoaderManager.getInstance(activity).initLoader(loaderId, bundle, activity);
            }
        });
    }

    public static SecretKey generateKey() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("r@nd0m seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            return new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static byte[] encryptMsg(String message, SecretKey secret) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher.doFinal(message.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.d(TAG, "onLoaderReset");
    }
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        if (i == loaderId) {
            Log.d(TAG, "onCreateLoader");
            return new MyLoader(this, bundle);
        }
        throw new InvalidParameterException("Invalid loader id");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String msg) {
        if (loader.getId() == loaderId) {
            Log.d(TAG, "onLoadFinished: " + msg);
            Toast.makeText(this, "Decrypted message: " + msg, Toast.LENGTH_SHORT).show();
        }
    }
}