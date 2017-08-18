package com.tongzhichao.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tongzhichao on 17-8-10.
 */

public class TestViewGroup extends ViewGroup {

    private static final String TAG = "TestViewGroup";

    public TestViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public TestViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout" + changed);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            Log.d(TAG, "view " + view.getMeasuredWidth() + " " + view.getMeasuredHeight());
            getChildAt(i).layout(l, t, l + view.getMeasuredWidth(), t + view.getMeasuredHeight());
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        Log.d(TAG, "onDraw");
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent");
//        if(ev.getAction()==MotionEvent.ACTION_MOVE){
//            return true;
//        }
        return super.onInterceptTouchEvent(ev);
//        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent");
        return super.onTouchEvent(event);
    }
}
