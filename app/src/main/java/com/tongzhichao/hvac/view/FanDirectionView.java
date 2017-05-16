package com.tongzhichao.hvac.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.tongzhichao.example.R;

import java.util.ArrayList;

/**
 * Created by tongzhichao on 17-5-16.
 */

public class FanDirectionView extends View {
    private static final String TAG = "FanDirectionView";

    //face & foot
    private ArrayList<Bitmap> mBitmaps = new ArrayList<>();
    private Bitmap mUnavailableBitmap;

    private Paint mPaint;
    private int mViewWidth, mViewHeight;

    private int mState = 0;


    private Point lone = new Point(0, 126), ltwo = new Point(0, 130), lthree = new Point(0, 135), lfour = new Point(0, 140),
            rone, rtwo, rthree, rfour;
    private float mOneA, mOneB, mTwoA, mTwoB, mThreeA, mThreeB, mFourA, mFourB;

    public FanDirectionView(Context context) {
        this(context, null);
    }

    public FanDirectionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FanDirectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
//        mPaint.setStrokeWidth(2);
//        mPaint.setColor(Color.RED);
        mUnavailableBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_derection_off);
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_derection_none));
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_derection_feet));
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_derection_face));
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_derection_face_feet));
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_derection_front));
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_derection_head_feet));
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_derection_head_face));
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_derection_full));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        rone = new Point(mViewWidth, 10);
        rtwo = new Point(mViewWidth, 93);
        rthree = new Point(mViewWidth, 176);
        rfour = new Point(mViewWidth, 259);
        initValue();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            mState = -1;
        } else {
            mState = 0;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mState == -1) {
            canvas.drawBitmap(mUnavailableBitmap, 0, 0, mPaint);
            return;
        }
        canvas.drawBitmap(mBitmaps.get(mState), 0, 0, mPaint);

//        canvas.drawLine(lone.x, lone.y, rone.x, rone.y, mPaint);
//        canvas.drawLine(ltwo.x, ltwo.y, rtwo.x, rtwo.y, mPaint);
//        canvas.drawLine(lthree.x, lthree.y, rthree.x, rthree.y, mPaint);
//        canvas.drawLine(lfour.x, lfour.y, rfour.x, rfour.y, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int area = getArea(event);
            if(area!=-1){
                mState^=area;
                invalidate();
            }
        }
        return super.onTouchEvent(event);
    }

    private int getArea(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (((x * mOneA) + mOneB < (mViewHeight - y))) {
            return -1;
        } else if (((x * mTwoA) + mTwoB < (mViewHeight - y))) {
            return 4;
        } else if (((x * mThreeA) + mThreeB < (mViewHeight - y))) {
            return 2;
        } else if (((x * mFourA) + mFourB < (mViewHeight - y))) {
            return 1;
        } else {
            return -1;
        }
    }

    private void initValue() {
        mOneB = mViewHeight - lone.y;
        mOneA = (float) (((double) (lone.y - rone.y)) / ((double) mViewWidth));

        mTwoB = mViewHeight - ltwo.y;
        mTwoA = (float) (((double) (ltwo.y - rtwo.y)) / ((double) mViewWidth));

        mThreeB = mViewHeight - lthree.y;
        mThreeA = (float) (((double) (lthree.y - rthree.y)) / ((double) mViewWidth));

        mFourB = mViewHeight - lfour.y;
        mFourA = (float) (((double) (lfour.y - rfour.y)) / ((double) mViewWidth));

    }
}
