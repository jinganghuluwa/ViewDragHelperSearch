package com.tongzhichao.path;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.tongzhichao.example.R;

/**
 * Created by tongzhichao on 17-3-28.
 */

public class PointActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private PointView mPointView;
    private SeekBar top,left,right,bottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        mPointView = (PointView) findViewById(R.id.point_view);
        top = (SeekBar) findViewById(R.id.top);
        left = (SeekBar) findViewById(R.id.left);
        right = (SeekBar) findViewById(R.id.right);
        bottom = (SeekBar) findViewById(R.id.bottom);
        top.setOnSeekBarChangeListener(this);
        left.setOnSeekBarChangeListener(this);
        right.setOnSeekBarChangeListener(this);
        bottom.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.top:
                mPointView.setTopPoint(progress);
                break;
            case R.id.left:
                mPointView.setLeftPoint(progress);
                break;
            case R.id.right:
                mPointView.setRightPoint(progress);
                break;
            case R.id.bottom:
                mPointView.setBottomPoint(progress);
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
