package com.sevketbuyukdemir.qr_code_scanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread;
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }
}

