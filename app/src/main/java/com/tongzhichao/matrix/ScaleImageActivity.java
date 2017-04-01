package com.tongzhichao.matrix;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tongzhichao.example.R;

/**
 * Created by tongzhichao on 17-3-29.
 */

public class ScaleImageActivity extends AppCompatActivity {
    private ScaleImageView scaleImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);
        scaleImageView = (ScaleImageView) findViewById(R.id.scaleImage);

        scaleImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.image));

    }
}
