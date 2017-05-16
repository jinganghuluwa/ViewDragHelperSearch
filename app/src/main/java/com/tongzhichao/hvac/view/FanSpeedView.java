package com.tongzhichao.hvac.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tongzhichao.example.R;


/**
 * Created by tongzhichao on 17-5-16.
 */

public class FanSpeedView extends View {
    private static final String TAG = "FanSpeedView";

    private static final int TOP_LENGTH = 152, BOTTOM_LENGTH = 82, UNIT_LENGTH = 69;

    private Paint mPaint;
    private int mSpeed = 1;
    private int mViewWidth, mViewHeight;
    private Bitmap mBackgroundBitmap, mFrontBitmap, mUnavailableBitmap,
            mTrayBitmap, mFanOffBitmap, mFanOnBitmap;
    private Path mPath = new Path();
    private OnSpeedChangeListener mOnSpeedChangeListener;


    private Point lt = new Point(0, 75), lb = new Point(0, 85), rt, rb;

    private float mTopA, mTopB, mBottomB;


    public FanSpeedView(Context context) {
        this(context, null);
    }

    public FanSpeedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FanSpeedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
//        mPaint.setStrokeWidth(2);
//        mPaint.setColor(Color.RED);
        mBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_fan_speed_bg);
        mFrontBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_fan_speed_full);
        mUnavailableBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_fan_speed_off);
        mTrayBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_fan_speed_tray);
        mFanOffBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_ic_fan_off);
        mFanOnBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_ic_fan_on);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        rt = new Point(mViewWidth, 5);
        rb = new Point(mViewWidth, lb.y);
        initAB();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mTrayBitmap, 0, 0, mPaint);
        if (isEnabled()) {
            canvas.drawBitmap(mFanOnBitmap, 50, 50, mPaint);
            canvas.drawBitmap(mBackgroundBitmap, 0, 0, mPaint);
            Bitmap frontBitmap = getFrontBitmap();
            mPaint.setXfermode(null);
            canvas.drawBitmap(frontBitmap, 0, 0, mPaint);
        } else {
            canvas.drawBitmap(mFanOffBitmap, 50, 50, mPaint);
            canvas.drawBitmap(mUnavailableBitmap, 0, 0, mPaint);
        }


//        canvas.drawLine(lt.x, lt.y, rt.x, rt.y, mPaint);
//        canvas.drawLine(lb.x, lb.y, rb.x, rb.y, mPaint);
    }


    private Bitmap getFrontBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mViewWidth, mViewHeight,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.lineTo(getTopLength(), 0);
        mPath.lineTo(getBottomLength(), mViewHeight);
        mPath.lineTo(0, mViewHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mFrontBitmap, 0, 0, mPaint);
        return bitmap;
    }

    private int getTopLength() {
        return TOP_LENGTH + mSpeed * UNIT_LENGTH;
    }

    private int getBottomLength() {
        return BOTTOM_LENGTH + mSpeed * UNIT_LENGTH;
    }

    public void setSpeed(int speed) {
        mSpeed = speed;
        refreshSpeed();
    }

    private void refreshSpeed() {
        invalidate();
        if (mOnSpeedChangeListener != null) {
            mOnSpeedChangeListener.onSpeedChange(mSpeed);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isTouchValid(event)) {
            return super.onTouchEvent(event);
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            int position = (int) ((x - BOTTOM_LENGTH - (TOP_LENGTH - BOTTOM_LENGTH) * (y / mViewHeight)) / UNIT_LENGTH);
            if (position >= 0 && position < 8) {
                mSpeed = position + 1;
                refreshSpeed();
            }
        }
        return super.onTouchEvent(event);
    }

    private boolean isTouchValid(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        return ((x * mTopA) + mTopB > (mViewHeight - y)) && ((mViewHeight - y) > mBottomB);
    }

    private void initAB() {
        mBottomB = mViewHeight - lb.y;
        mTopB = mViewHeight - lt.y;
        mTopA = (float) (((double) (lb.y - rt.y)) / ((double) mViewWidth));
    }

    public interface OnSpeedChangeListener {
        void onSpeedChange(int speed);
    }

    public void setOnSpeedChangeListener(OnSpeedChangeListener onSpeedChangeListener) {
        mOnSpeedChangeListener = onSpeedChangeListener;
    }
}
