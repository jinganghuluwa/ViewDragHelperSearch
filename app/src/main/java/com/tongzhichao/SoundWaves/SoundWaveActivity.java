package com.tongzhichao.SoundWaves;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tongzhichao.example.R;

import java.io.File;

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
