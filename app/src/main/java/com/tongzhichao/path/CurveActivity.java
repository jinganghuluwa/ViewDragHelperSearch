package com.tongzhichao.path;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tongzhichao.example.R;

import java.util.ArrayList;

/**
 * Created by tongzhichao on 17-3-22.
 */

public class CurveActivity extends AppCompatActivity {

    CurveView curveView;
    private ArrayList<CurveView.Point> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curve);
        curveView = (CurveView) findViewById(R.id.curve);
        initData();
        curveView.setData(list);
    }

    private void initData() {
        list.add(new CurveView.Point(0f, 5f));
        list.add(new CurveView.Point(1f, 8f));
        list.add(new CurveView.Point(2f, 6f));
        list.add(new CurveView.Point(3f, 6f));
        list.add(new CurveView.Point(4f, 10f));
        list.add(new CurveView.Point(5f, 15f));
        list.add(new CurveView.Point(6f, 28f));
        list.add(new CurveView.Point(7f, 25f));
        list.add(new CurveView.Point(8f, 19f));
        list.add(new CurveView.Point(9f, 24f));
        list.add(new CurveView.Point(10f, 12f));
        list.add(new CurveView.Point(11f, 3f));
        list.add(new CurveView.Point(12f, 9f));
        list.add(new CurveView.Point(13f, 17f));
        list.add(new CurveView.Point(14f, 23f));
        list.add(new CurveView.Point(15f, 10f));
        list.add(new CurveView.Point(16f, 9f));
        list.add(new CurveView.Point(17f, 4f));
        list.add(new CurveView.Point(18f, 12f));
        list.add(new CurveView.Point(19f, 19f));


    }
}
