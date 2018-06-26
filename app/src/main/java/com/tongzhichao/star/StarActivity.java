package com.tongzhichao.star;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.tongzhichao.example.R;

public class StarActivity extends AppCompatActivity {
    private StarView starView;
    private Handler handler = new Handler(Looper.getMainLooper());

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            play();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        starView = findViewById(R.id.star);
        play();
    }

    private double per = 1;
    private boolean up;

    private void play() {
        if (up) {
            per += 0.02;
        } else {
            per -= 0.02;
        }

        if (per < 0.6) {
            up = true;
        } else if (per > 1) {
            up = false;
        }
        starView.setPer(per);
        handler.postDelayed(runnable, 60);
    }
}