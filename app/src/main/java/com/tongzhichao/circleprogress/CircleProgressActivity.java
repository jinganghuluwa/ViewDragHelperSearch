package com.tongzhichao.circleprogress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;

import com.tongzhichao.example.R;

/**
 * Created by tzc on 2017/3/21.
 */

public class CircleProgressActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private CircleProgressView circleProgressView;
    private SeekBar seekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress);
        circleProgressView = (CircleProgressView) findViewById(R.id.circle);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        circleProgressView.setProgress(seekBar.getProgress());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        circleProgressView.setProgress(seekBar.getProgress());
    }
}
