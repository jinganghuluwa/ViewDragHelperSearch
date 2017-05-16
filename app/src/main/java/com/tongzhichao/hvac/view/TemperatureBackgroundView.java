package com.tongzhichao.hvac.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tongzhichao.example.R;

/**
 * Created by tongzhichao on 17-5-15.
 */

public class TemperatureBackgroundView extends View {
    private static final String TAG = "TemperatureControlView";
    private static final int MIX_MOVE_PIXELS = 5;
    private Paint mPaint;
    private int mViewWidth, mViewHeight;
    private boolean isLeft;
    private int mLineColor = Color.WHITE;
    private Bitmap mLineBitmap;
    private Rect mSideFrame;
    private OnSlideListener mOnSlideListener;

    public TemperatureBackgroundView(Context context) {
        this(context, null);
    }

    public TemperatureBackgroundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TemperatureBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TemperatureBackgroundView, defStyleAttr, defStyleAttr);
        isLeft = typedArray.getBoolean(R.styleable.TemperatureBackgroundView_background_left, true);
        mPaint = new Paint();
        if (isLeft) {
            mLineBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_climate_left_bg);
        } else {
            mLineBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_climate_right_bg);
        }
    }

    public void setLeft(boolean left) {
        isLeft = left;
        if (isLeft) {
            mLineBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_climate_left_bg);
        } else {
            mLineBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hvac_climate_right_bg);
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap lineBitmap = createLineBitmap();
        mPaint.setXfermode(null);
        canvas.drawBitmap(lineBitmap, 0, 0, mPaint);
    }

    private Bitmap createLineBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mViewWidth, mViewHeight,
                Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(mLineColor);
        Canvas canvas = new Canvas(bitmap);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mLineBitmap, null, new Rect(0, 0, mViewWidth, mViewHeight), mPaint);
        return bitmap;
    }

    private float mDownX, mDownY, mLastX, mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = mDownX = event.getX();
                mLastY = mDownY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                int direction = (int) Math.sqrt(Math.pow((event.getX() - mLastX), 2) +
                        Math.pow((event.getY() - mLastY), 2));
                if (direction >= MIX_MOVE_PIXELS) {
                    int slideDirection = getSlideDirection(event);
                    mLastX = event.getX();
                    mLastY = event.getY();
                    if (slideDirection > 0) {
                        if (mOnSlideListener != null) {
                            mOnSlideListener.onSlideDown();
                        }
                        setLineColor(Color.BLUE);
                        return true;
                    } else if (slideDirection < 0) {
                        if (mOnSlideListener != null) {
                            mOnSlideListener.onSlideUp();
                        }
                        setLineColor(Color.RED);
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setLineColor(Color.WHITE);
                break;
        }
        return super.onTouchEvent(event);
    }

    private int getSlideDirection(MotionEvent event) {
        float horizontalDistance = Math.abs(event.getX() - mLastX);
        float verticalDistance = event.getY() - mLastY;
        if (Math.abs(verticalDistance) > 2 * horizontalDistance) {
            return (int) verticalDistance;
        } else {
            return 0;
        }
    }

    public void setLineColor(int color) {
        mLineColor = color;
        invalidate();
    }

    public void setOnSlideListener(OnSlideListener onSlideListener) {
        mOnSlideListener = onSlideListener;
    }

    public interface OnSlideListener {
        void onSlideUp();

        void onSlideDown();
    }
}
