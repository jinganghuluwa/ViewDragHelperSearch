package com.tongzhichao.path;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.tongzhichao.example.R;

/**
 * Created by tongzhichao on 17-3-28.
 */

public class FourPathCircleActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private FourPathCircle fourPathCircle;
    private SeekBar top, side, bottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_path_circle);
        fourPathCircle = (FourPathCircle) findViewById(R.id.fourPathCircle);
        top = (SeekBar) findViewById(R.id.top);
        side = (SeekBar) findViewById(R.id.side);
        bottom = (SeekBar) findViewById(R.id.bottom);
        top.setOnSeekBarChangeListener(this);
        side.setOnSeekBarChangeListener(this);
        bottom.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.top:
                fourPathCircle.setTopPoint(progress);
                break;
            case R.id.side:
                fourPathCircle.setSidePoint(progress);
                break;
            case R.id.bottom:
                fourPathCircle.setBottomPoint(progress);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
