package com.tongzhichao.SoundWaves;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tongzhichao.example.R;


/**
 * Created by tongzhichao on 17-8-18.
 */

public class SoundWaveActivity extends AppCompatActivity {


    private SoundWaveView soundWave;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundwave);
        soundWave = findViewById(R.id.soundWave);
    }





}
