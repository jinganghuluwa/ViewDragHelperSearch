package com.tongzhichao.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tongzhichao on 17-8-10.
 */

public class TestView extends View {
    private static final String TAG = "TestView";

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        Log.d(TAG, "TestView ");
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wmode = MeasureSpec.getMode(widthMeasureSpec);
        int hmode = MeasureSpec.getMode(heightMeasureSpec);
        int wsize = MeasureSpec.getSize(widthMeasureSpec);
        int hsize = MeasureSpec.getSize(heightMeasureSpec);
        if (wmode == MeasureSpec.UNSPECIFIED) {
            wsize = 300;
        }
        if (hmode == MeasureSpec.UNSPECIFIED) {
            hsize = 400;
        }
        Log.d(TAG, "onMeasure " + wsize + " " + hsize);
        setMeasuredDimension(wsize, hsize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean dis = super.dispatchTouchEvent(event);
        Log.d(TAG, "dispatchTouchEvent " + dis);
        return dis;
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }
//        return super.onTouchEvent(event);
        return false;
    }

}
